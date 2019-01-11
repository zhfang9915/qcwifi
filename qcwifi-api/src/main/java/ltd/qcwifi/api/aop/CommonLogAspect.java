/**  

 
			2018年2月1日
                               _(\_/) 
                             ,((((^`\
                            ((((  (6 \ 
                          ,((((( ,    \
      ,,,_              ,(((((  /"._  ,`,
     ((((\\ ,...       ,((((   /    `-.-'
     )))  ;'    `"'"'""((((   (      
    (((  /            (((      \
     )) |      zhfang          |
    ((  |        .       '     |
    ))  \     _ '      `t   ,.')
    (   |   y;- -,-""'"-.\   \/  
    )   / ./  ) /         `\  \
       |./   ( (           / /'
       ||     \\          //'|
       ||      \\       _//'||
       ||       ))     |_/  ||
       \_\     |_/          ||
       `'"                  \_\
                            `'" 
 *
 * @Title: CommonLogAspect.java
 * @Prject: VistaMall-comm
 * @Package: org.vista.mall.comm.aop
 * @Description: TODO
 * @author: zhfang  
 * @date: 2018年2月1日 上午9:04:48
 * @version: V1.0  

 */
package ltd.qcwifi.api.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;

import ltd.qcwifi.api.annotation.AopLog;
import ltd.qcwifi.comm.utils.SysDbPkUtil;
import ltd.qcwifi.model.dto.ApiResult;
import ltd.qcwifi.model.entity.log.ApiLog;

/**
 * @ClassName: CommonLogAspect
 * @Description: 公共日志记录
 * @author: zhfang
 * @date: 2018年2月1日 上午9:04:48
 */
@Aspect
@Component("commonLogAspect")
public class CommonLogAspect {

	/**
	 * 注入soaClient用于把日志保存数据库
	 */
	@Autowired
	ltd.qcwifi.system.service.LogService logService;
	
	// 本地异常日志记录对象
	private Logger logger = LoggerFactory.getLogger(CommonLogAspect.class);
	
	/**
	 * 定义日志切入点
	 */
	@Pointcut("@annotation(ltd.qcwifi.api.annotation.AopLog)")
	public void annotationPointcut() {
	}

	/**
	 * 后置通知 用于拦截记录用户的操作
	 * @param joinPoint
	 *            切点
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@Around("annotationPointcut()")
	public Object doAround(ProceedingJoinPoint point) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        //请求的IP  
        String ip = request.getRemoteAddr();  
        Object returnValue = null;
        Object target = point.getTarget();
		String method = point.getSignature().getName();
		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		Method m = null;
		try {
			m = target.getClass().getMethod(method, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.debug("==Around通知异常==");  
            logger.debug("异常信息:{}", e.getMessage());  
		}

		AopLog el = m.getAnnotation(AopLog.class);
		//*========控制台输出=========*//  
		logger.debug("=====后置通知开始=====");  
		logger.debug("请求方法:" + (point.getTarget().getClass().getName() + "." + point.getSignature().getName() + "()"));  
        logger.debug("请求IP:" + ip);  
        
        //*========数据库日志=========*//  
        ApiLog log = new ApiLog();
        log.setId(SysDbPkUtil.createPk("API"));
        log.setRequestDate(new Date()); 
        log.setIp(ip);  
        log.setInvokeApi(el.value());
        log.setParams(JSONObject.toJSONString(point.getArgs()));
        
		try {
			//执行目标方法
			Stopwatch sw = Stopwatch.createStarted();
			returnValue = point.proceed();
			log.setInvokeTime(sw.stop().toString());//执行时间
			log.setResult(JSONObject.toJSONString(returnValue));
		} catch (Throwable e) {
			//记录本地异常日志  
            logger.debug("==Around通知异常==");  
            logger.debug("异常信息:{}", e.getMessage());  
            log.setException(e.getMessage() + ":" + e.getCause());
            returnValue = new ApiResult<>(10001);
		} finally {
			//保存数据库  
	        logService.saveApiLog(log);
	        logger.debug("=====后置通知结束=====");
		}
		return returnValue;
	}

}

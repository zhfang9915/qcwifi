/**  

 
			2018年1月31日
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
 * @Title: AsyncUncaughtExceptionHandler.java
 * @Prject: VistaMall-comm
 * @Package: org.vista.mall.comm.exception
 * @Description: TODO
 * @author: zhfang  
 * @date: 2018年1月31日 下午3:04:56
 * @version: V1.0  

 */
package ltd.qcwifi.comm.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: AsyncUncaughtExceptionHandler
 * @Description: 异步子线程 异常捕获类
 * @author: zhfang
 * @date: 2018年1月31日 下午3:04:56
 */
@Component("customAsyncUncaughtExceptionHandler")
public class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(CustomAsyncUncaughtExceptionHandler.class);
	
	/* (non Javadoc)
	 * @Title: handleUncaughtException
	 * @Description: 子线程异常信息
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler#handleUncaughtException(java.lang.Throwable, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... obj) {
		logger.debug("-------------异步方法异常信息开始-----------------");
		logger.debug("方法名-->{}", method.getName());
		logger.debug("参数-->{}", JSONObject.toJSONString(obj));
		logger.debug("异常信息-->{}", ex.getMessage());
		logger.debug("-------------异步方法异常信息结束-----------------");
	}

	

}

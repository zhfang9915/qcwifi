package ltd.qcwifi.backend.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import ltd.qcwifi.model.dto.ExecuteResult;

/**
 * 重写 SimpleMappingExceptionResolver 类的 resolveException() 方法 拦截普通请求和ajax请求
 * 
 * @author 张芳
 *
 */
@Component
public class CustomHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ex.printStackTrace();
		response.setCharacterEncoding("UTF-8");
		response.setStatus(500);
		
		if (!(request.getHeader("accept").contains("application/json") || (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
			// 如果不是异步请求
			if (ex instanceof BaseControllerException || ex instanceof BaseServiceException) {
				request.setAttribute("exceptionMsg", ex.getMessage());
				return new ModelAndView("error/500");
			}else if (ex instanceof UnauthorizedException) {
				request.setAttribute("exceptionMsg", "您没有权限执行此操作，请联系管理员");
				return new ModelAndView("error/unauthorized");
			}else {
				request.setAttribute("exceptionMsg", "系统内部出错，若刷新后仍无效，请联系管理员");
				return new ModelAndView("error/500");
			}
		} else {// JSON格式返回
			try (PrintWriter out = response.getWriter()) {
				ExecuteResult<String> result = null;
				if (ex instanceof UnauthorizedException) {
					result = new ExecuteResult<>(false, "您没有权限执行此操作，请联系管理员");
				}else if (ex instanceof MaxUploadSizeExceededException) {
					result = new ExecuteResult<>(false, "上传文件超出限制");
				}else{
					result = new ExecuteResult<>(false, ex.getMessage());
				}
				String resultJson = new ObjectMapper().writeValueAsString(result);
				out.write(resultJson);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}

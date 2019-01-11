package ltd.qcwifi.backend.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import ltd.qcwifi.backend.Qc;

/**
 * ServletContxt初始化加载环境变量
 * @author 张芳
 *
 */
@Component("servletContxtInitListener")
public class ServletContxtInitListener implements ServletContextAware {

	@Resource Qc qc;
	
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		onApplicationInIt();
	}

	public void onApplicationInIt() {
		if (servletContext != null) {
			System.out.println(qc);
			servletContext.setAttribute("qc", qc);
		}

	}

}

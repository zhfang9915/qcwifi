package ltd.qcwifi.web.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常错误控制器
 * @author 张芳
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

	/**
	 * 打开未授权提示页
	 * @return
	 */
	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public ModelAndView unauthorizedPage() {
		
		return new ModelAndView("error/unauthorized");
	}
	
	/**
	 * 不是从指定入口打开的页面
	 * @return
	 */
	@RequestMapping(value = "/notstart", method = RequestMethod.GET)
	public ModelAndView notStartPage() {
		
		return new ModelAndView("error/notstart");
	}
}

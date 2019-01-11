package ltd.qcwifi.web.controller.cloud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 推广管家-报表统计
 * @author Administrator
 *
 */
@RequestMapping("/push/report")
@Controller
public class PushReportController extends BaseController {

	/**
	 * 报表统计视图
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("currentHeader", "report");
		return Web.PUSH + "report";
	}
}

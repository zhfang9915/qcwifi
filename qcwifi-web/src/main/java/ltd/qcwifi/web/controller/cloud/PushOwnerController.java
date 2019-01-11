package ltd.qcwifi.web.controller.cloud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 推广管家-广告主
 * @author Administrator
 *
 */
@RequestMapping("/push/owner")
@Controller
public class PushOwnerController extends BaseController {

	/**
	 * 广告主视图
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("currentHeader", "owner");
		return Web.PUSH + "owner";
	}
}

package ltd.qcwifi.backend.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.system.service.SystemService;

/**
 * 权限管理
 * @author 张芳
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SystemService systemService;
	
	/**
	 * 权限列表视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/system/index")
	public ModelAndView toSystemConfiPage(Model model) {
		logger.debug(">>>进入系统配置页....");
		model.addAttribute("systemActive", "active");
		return new ModelAndView(Web.FOLDER_SYSTEM + "system");
	}
	
	@RequestMapping(value = "/flushDB", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/system/flushDB")
	public ExecuteResult<String> flushDBCache() {
		if (systemService.flushDBCache()) {
			return new ExecuteResult<String>("DB Redis缓存已清空");
		}
		return new ExecuteResult<String>("DB Redis缓存清空失败");
	}
	
}

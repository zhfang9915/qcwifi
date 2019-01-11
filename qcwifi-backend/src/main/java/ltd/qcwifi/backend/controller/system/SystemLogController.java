package ltd.qcwifi.backend.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ApiLogParam;
import ltd.qcwifi.model.entity.log.ApiLog;
import ltd.qcwifi.system.service.LogService;
import net.sf.json.JSONObject;

@RequestMapping("/system/log")
@Controller
public class SystemLogController {

private Logger logger = LoggerFactory.getLogger(SystemLogController.class);
	
	@Autowired
	LogService logService;
	
	/**
	 * API日志查询页面
	 * @return
	 */
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public ModelAndView toPage(Model model) {
		return new ModelAndView(Web.FOLDER_CLOUD_LOGS + "api");
	}
	
	/**
	 * 表格展示API日志
	 * @return
	 */
	@RequestMapping(value = "/api", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public TableResult<ApiLog> table(@RequestBody String requestJson) {
		ApiLogParam param = (ApiLogParam) JSONObject.toBean(JSONObject.fromObject(requestJson), ApiLogParam.class);
		return logService.getApiLogs(param);
	}
	
	
}

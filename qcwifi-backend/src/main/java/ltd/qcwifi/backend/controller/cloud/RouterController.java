package ltd.qcwifi.backend.controller.cloud;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.cloud.platform.service.impl.JsServiceImpl;
import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.comm.utils.FileMD5Util;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.dto.param.RouterParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import net.sf.json.JSONObject;

@RequestMapping("/router")
@Controller
public class RouterController {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ShopRouterService routerService;
	
	/**
	 * JS 配置视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/router/query")
	public ModelAndView toPage(Model model) {
		logger.debug(">>>进入路由器设备列表页....");
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "router");
	}
	
	/**
	 * 表格展示JS列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/router/query")
	public TableResult<ShopRouter> table(@RequestBody String requestJson) {
		logger.debug("获取JS列表-->" + requestJson);
		RouterParam param = (RouterParam) JSONObject.toBean(JSONObject.fromObject(requestJson), RouterParam.class);
		return routerService.tables(param);
	}
	
	
}

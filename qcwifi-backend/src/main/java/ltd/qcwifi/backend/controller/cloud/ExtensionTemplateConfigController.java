package ltd.qcwifi.backend.controller.cloud;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.cloud.platform.service.impl.ExtensionTemplateServiceImpl;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ExtensionTemplateParam;
import ltd.qcwifi.model.entity.cloud.platform.ExtensionTemplate;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import net.sf.json.JSONObject;

/**
 * 云平台 固件 配置
 * @author 张芳
 *
 */
@RequestMapping("/extension/template")
@Controller
public class ExtensionTemplateConfigController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="extensionTemplateService")
	ExtensionTemplateServiceImpl templateService;
	
	/**
	 * 推送模版 配置视图页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/extension/template/query")
	public ModelAndView toFirmwareConfigPage(Model model) {
		logger.debug(">>>进入广告模版 配置页....");
		model.addAttribute("templateActive", "active");
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "extension.template.config");
	}
	
	/**
	 * 表格展示推送模版列表
	 * @param requestJson
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/query")
	public TableResult<ExtensionTemplate> table(@RequestBody String requestJson) {
		logger.debug("table ExtensionTemplate-->" + requestJson);
		ExtensionTemplateParam param = (ExtensionTemplateParam) JSONObject.toBean(JSONObject.fromObject(requestJson), ExtensionTemplateParam.class);
		return templateService.tables(param);
	}
	
	/**
	 * 根据ID获取推送模版信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/query")
	public ExecuteResult<ExtensionTemplate> searchExtensionTemplateById(@PathVariable("id")Integer id) {
		logger.debug("search ExtensionTemplate-->" + id);
		return templateService.searchById(id);
	}
	
	/**
	 * 批量删除推送模版
	 * @param templates
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/delete")
	public ExecuteResult<String> batchDeleteExtensionTemplate(@RequestBody List<ExtensionTemplate> templates) {
		logger.debug("batch delete ExtensionTemplate-->" + super.toJson(templates));
		return templateService.batchDelete(templates);
	}
	
	/**
	 * 删除推送模版
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/delete")
	public ExecuteResult<String> deleteExtensionTemplate(@PathVariable("id")Long id) {
		logger.debug("delete ExtensionTemplate-->" + id);
		return templateService.delete(id);
	}
	
	/**
	 * 更新推送模版信息
	 * @param template
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/update")
	public ExecuteResult<String> updateExtensionTemplate(ExtensionTemplate template) {
		logger.debug("update ExtensionTemplate-->" + template);
		return templateService.update(template);
	}
	
	/**
	 * 创建推送模版
	 * @param template
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/extension/template/create")
	public ExecuteResult<String> createExtensionTemplate(ExtensionTemplate template) {
		template.setCreateBy(TokenManager.getUserName());
		return templateService.create(template);
	}
	
	
}

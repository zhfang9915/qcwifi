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
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.cloud.platform.service.impl.JsServiceImpl;
import ltd.qcwifi.comm.utils.FileMD5Util;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import net.sf.json.JSONObject;

/**
 * 云平台 js 配置
 * @author 许延明
 *
 */
@RequestMapping("/js")
@Controller
public class JSConfigController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JsServiceImpl jsService;
	
	/**
	 * JS 配置视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/js/query")
	public ModelAndView toJsConfigPage(Model model) {
		logger.debug(">>>进入JS 配置页....");
		model.addAttribute("jsActive", "active");
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "jsConfig");
	}
	
	/**
	 * 表格展示JS列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/query")
	public TableResult<Jscode> table(@RequestBody String requestJson) {
		logger.debug("获取JS列表-->" + requestJson);
		JscodeParam param = (JscodeParam) JSONObject.toBean(JSONObject.fromObject(requestJson), JscodeParam.class);
		return jsService.tables(param);
	}
	
	/**
	 * 创建JS
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/create")
	public ExecuteResult<String> createJscode(Jscode jscode) {
		logger.debug("新增JS源码-->" + jscode);
		jscode.setCodeId(FileMD5Util.StringMD5(RandCodeUtil.getRandomString(16)));
		return jsService.create(jscode);
	}
	
	/**
	 * 单个删除JS
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/delete")
	public ExecuteResult<String> deleteJscode(@PathVariable("id")Long id) {
		logger.debug("删除单个JS-->" + id);
		// 判断是否为默认的JS，是的话则不能删除
		ExecuteResult<Jscode> jscodeResult = jsService.searchById(id);
		Jscode jscode = jscodeResult.getData();
		if (jscode.getIsDefault() == 1) {
			return new ExecuteResult<String>(false, "不能删除默认的JS");
		}
		return jsService.delete(id);
	}
	
	/**
	 * 批量删除JS
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/delete")
	public ExecuteResult<String> batchDeleteJscode(@RequestBody List<Jscode> jscodes) {
		logger.debug("批量删除JS-->" + super.toJson(jscodes));
		// 判断选中的JS里面有没有默认的JS，如果有则不能删除
		for (Jscode jscode : jscodes) {
			if (jscode.getIsDefault() == 1) {
				return new ExecuteResult<String>(false, "不能删除默认的JS，请重新选择");
			}
		}
		return jsService.batchDelete(jscodes);
	}
	
	/**
	 * 根据ID获取JS源码信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/query")
	public ExecuteResult<Jscode> searchById(@PathVariable("id")Long id) {
		logger.debug("查询JS-->" + id);
		return jsService.searchById(id);
	}
	
	/**
	 * 更新JS
	 * @param jscode
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/js/update")
	public ExecuteResult<String> update(Jscode jscode) {
		logger.debug("更新JS-->" + jscode);
		return jsService.update(jscode);
	}
}

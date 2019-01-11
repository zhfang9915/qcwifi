package ltd.qcwifi.backend.controller.cloud;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Qc;
import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.cloud.platform.service.impl.ExtensionTemplateServiceImpl;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.ExtensionTemplate;

/**
 * 云平台 固件 配置
 * @author 张芳
 *
 */
@RequestMapping("/extension/template")
@Controller
public class ExtensionTemplatePreviewController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="extensionTemplateService")
	ExtensionTemplateServiceImpl templateService;
	
	@Autowired
	Qc qc;
	
	/**
	 * 推送模版 预览视图
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/preview/{viewType}/{id}", method = RequestMethod.GET)
	@RequiresPermissions("/extension/template/query")
	public ModelAndView toFirmwareConfigPage(@PathVariable("viewType")String viewType,
			@PathVariable("id")Integer id, Model model) {
		logger.debug(">>>进入广告模版 预览页....");
		model.addAttribute("tempId", id);
		model.addAttribute("viewType", viewType);
		ExecuteResult<ExtensionTemplate> result = templateService.searchById(id);
		if (!result.isSuccess()) {
			model.addAttribute("preview", "生成预览失败，因为系统未查询到此模版信息");
		}else {
			ExtensionTemplate et = result.getData();
			String temp = et.getTemp();
			StringBuffer sb = new StringBuffer();
			String tempFor = et.getTempFor();
			if (et.getIsMore()) {//多资源
				for (int i = 1; i < 6; i++) {
					sb.append(tempFor
							.replace("$IMGSRC$", qc.getNginx()+"static/img/preview/pre-" + i + ".jpg")
							.replace("$IMGHREF$", qc.getNginx()));
				}
			}else{
				sb.append(tempFor
							.replace("$IMGSRC$", qc.getNginx()+"static/img/preview/pre-" + 1 + ".jpg")
							.replace("$IMGHREF$", qc.getNginx()));
			}
			temp = temp.replace("$TEMPFOR$", sb.toString()).replace("$logid$", System.currentTimeMillis()+"");
			StringBuffer jsb = new StringBuffer();
			if (StringUtils.isNotBlank(et.getTempJs())) {
				String[] jsArr = et.getTempJs().split("\n");
				for (String js : jsArr) {
					jsb.append("<script src=\"" + js + "\"></script>");
				}
			}
			temp += jsb.toString();
			model.addAttribute("preview", temp);
		}
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "extension.template.preview");
	}
	
	
	
	
}

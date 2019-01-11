package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import ltd.qcwifi.cloud.push.content.service.PushContentGroupService;
import ltd.qcwifi.cloud.push.content.service.PushContentService;
import ltd.qcwifi.cloud.push.content.service.PushContentTempService;
import ltd.qcwifi.comm.utils.SysDbPkUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.push.content.PushContent;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentChoose;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentGroup;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentSet;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentTemp;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 推广管家-推广内容
 * @author Administrator
 *
 */
@RequestMapping("/push/content/create")
@Controller
public class PushContentCreateController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(PushContentCreateController.class);

	@Autowired
	PushContentGroupService groupService;
	
	@Autowired
	PushContentTempService tempService;
	
	@Autowired
	PushContentService contentService;
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step1", method = RequestMethod.GET)
	public String step1(Model model) {
		model.addAttribute("currentHeader", "content");
		List<PushContentGroup> groups = groupService.getByUserId(TokenManager.getUserId());
		model.addAttribute("groups", groups);
		return Web.PUSH + "content.create.step1";
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step1", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step1Submit(PushContent content) {
		content.setCreateBy(TokenManager.getUserId());
		getSession().setAttribute("content_base_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/create/step2");
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step2", method = RequestMethod.GET)
	public String step2(Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null) {
			return "redirect:step1";
		}
		model.addAttribute("currentHeader", "content");
		//获取广告模版
		List<PushContentTemp> temps = tempService.getTemps();
		for (PushContentTemp temp : temps) {
			model.addAttribute(temp.getId(), temp);
		}
		return Web.PUSH + "content.create.step2";
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step2", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step2Submit(PushContentChoose choose) {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/create/step1");
		}
		content.setChoose(choose);
		System.out.println(content);
		getSession().setAttribute("content_base_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/create/step4");
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step3", method = RequestMethod.GET)
	public String step3(Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null) {
			return "redirect:step1";
		}
		if (content.getChoose() == null) {
			return "redirect:step2";
		}
		model.addAttribute("currentHeader", "content");
		return Web.PUSH + "content.create.step3";
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step3", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step3Submit(PushContentSet set) {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null || content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/create/step1");
		}
		if (content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/create/step2");
		}
		content.setSet(set);
		System.out.println(content);
		getSession().setAttribute("content_base_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/create/step4");
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step4", method = RequestMethod.GET)
	public String step4(Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null) {
			return "redirect:step1";
		}
		if (content.getChoose() == null) {
			return "redirect:step2";
		}
//		if (content.getSet() == null) {
//			return "redirect:step3";
//		}
		PushContentGroup group = groupService.getByGroupId(content.getGroupId());
		model.addAttribute("content", content);
		model.addAttribute("group", group);
		model.addAttribute("currentHeader", "content");
		return Web.PUSH + "content.create.step4";
	}
	
	/**
	 * 创建推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/step4", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step4Submit() {
		PushContent content = (PushContent) getSession().getAttribute("content_base_" + TokenManager.getUserId());
		if (content == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/create/step1");
		}
		if (content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/create/step2");
		}
//		if (content.getSet() == null) {
//			return new ExecuteResult<>(webAppPath() + "push/content/create/step3");
//		}
		//生成广告主键ID
		String contentId = SysDbPkUtil.createPk("AD");
		logger.info("生成广告ID为{}, content 内容为{}", contentId, content);
		content.setId(contentId);
		content.setStatus(true);//默认激活状态
		try {
			boolean flag = contentService.create(content);
			if (flag) {
				getSession().removeAttribute("content_base_" + TokenManager.getUserId());
				//成功后跳转到广告详情页
				return new ExecuteResult<>(webAppPath() + "push/content/detail/" + contentId);
			}
			return new ExecuteResult<>(false, "推广内容创建失败，请重试");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("广告创建异常{},广告信息为{}", e.getMessage(), content);
			return new ExecuteResult<>(false, "推广内容创建失败，请稍后重试");
		}
	}
}

package ltd.qcwifi.web.controller.cloud;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/push/content/update")
@Controller
public class PushContentUpdateController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(PushContentUpdateController.class);

	@Autowired
	PushContentGroupService groupService;
	
	@Autowired
	PushContentTempService tempService;
	
	@Autowired
	PushContentService contentService;
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step1", method = RequestMethod.GET)
	public String step1(@PathVariable("id")String id, Model model) {
		model.addAttribute("currentHeader", "content");
		PushContent content = contentService.getById(TokenManager.getUserId(), id);
		model.addAttribute("content", content);
		//不是当前账号下的广告
		if (!TokenManager.getUserId().equals(content.getCreateBy())) {
			return "redirect:/error/unauthorized";
		}
		
		List<PushContentGroup> groups = groupService.getByUserId(TokenManager.getUserId());
		model.addAttribute("groups", groups);
		
		getSession().setAttribute("content_update_" + TokenManager.getUserId(), content);
		return Web.PUSH + "content.update.step1";
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step1", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step1Submit(@PathVariable("id")String id, PushContent ct) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		content.setGroupId(ct.getGroupId());
		content.setName(ct.getName());
		content.setDayLimit(ct.getDayLimit());
		content.setTotalLimit(ct.getTotalLimit());
		content.setWeight(ct.getWeight());
		getSession().setAttribute("content_update_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/update/" + content.getId() + "/step2");
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step2", method = RequestMethod.GET)
	public String step2(@PathVariable("id")String id, Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		if (content == null) {
			return "redirect:step1";
		}
		model.addAttribute("content", content);
		
//		PushContentChoose choose = content.getChoose();
//		List<String> imgs = Arrays.asList(choose.getImgs().split(","));
//		model.addAttribute("imgs", imgs);
//		List<String> urls = Arrays.asList(choose.getUrls().split(","));
//		model.addAttribute("urls", urls);
		
		model.addAttribute("currentHeader", "content");
		//获取广告模版
		List<PushContentTemp> temps = tempService.getTemps();
		for (PushContentTemp temp : temps) {
			model.addAttribute(temp.getId(), temp);
		}
		return Web.PUSH + "content.update.step2";
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step2", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step2Submit(@PathVariable("id")String id, PushContentChoose choose) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		if (content == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step1");
		}
		content.setChoose(choose);
		getSession().setAttribute("content_update_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/update/"+content.getId()+"/step4");
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step3", method = RequestMethod.GET)
	public String step3(@PathVariable("id")String id, Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		if (content == null) {
			return "redirect:step1";
		}
		if (content.getChoose() == null) {
			return "redirect:step2";
		}
		model.addAttribute("currentHeader", "content");
		return Web.PUSH + "content.update.step3";
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step3", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step3Submit(@PathVariable("id")String id, PushContentSet set) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		if (content == null || content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step1");
		}
		if (content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step2");
		}
		content.setSet(set);
		System.out.println(content);
		getSession().setAttribute("content_update_" + TokenManager.getUserId(), content);
		return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step4");
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step4", method = RequestMethod.GET)
	public String step4(@PathVariable("id")String id, Model model) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
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
		return Web.PUSH + "content.update.step4";
	}
	
	/**
	 * 更新推广内容 基本信息视图
	 * @return
	 */
	@RequestMapping(value = "/{id}/step4", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> step4Submit(@PathVariable("id")String id) {
		PushContent content = (PushContent) getSession().getAttribute("content_update_" + TokenManager.getUserId());
		if (content == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step1");
		}
		if (content.getChoose() == null) {
			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step2");
		}
//		if (content.getSet() == null) {
//			return new ExecuteResult<>(webAppPath() + "push/content/update/"+id+"/step3");
//		}
		try {
			boolean flag = contentService.update(content);
			if (flag) {
				getSession().removeAttribute("content_update_" + TokenManager.getUserId());
				//成功后跳转到广告详情页
				return new ExecuteResult<>(webAppPath() + "push/content/detail/" + id);
			}
			return new ExecuteResult<>(false, "推广内容更新失败，请重试");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("广告更新异常{},广告信息为{}", e.getMessage(), content);
			return new ExecuteResult<>(false, "推广内容更新失败，请稍后重试");
		}
	}
	
	/**
	 * 更改广告上下架状态
	 * @Title: updateStatus
	 * @author: zhfang
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = "/{id}/status", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateStatus(@PathVariable("id")String id,
			@RequestParam("value") Boolean val,
			@RequestParam("filed") String filed) {
		if (!"status".equals(filed)) {
			return new ExecuteResult<>(false, "上下架修改失败");
		}
		PushContent content = new PushContent();
		content.setId(id);
		content.setStatus(val);
		content.setCreateBy(TokenManager.getUserId());
		try {
			boolean flag = contentService.updateStatus(content);
			if (flag) {
				return new ExecuteResult<>("修改上下架成功");
			}
			return new ExecuteResult<>(false, "上下架修改失败");
		} catch (Exception e) {
			return new ExecuteResult<>(false, "系统异常，上下架修改失败");
		}
	}
}

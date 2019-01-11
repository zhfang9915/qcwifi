package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.push.content.service.PushContentService;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.LayTableResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ShopInfoParam;
import ltd.qcwifi.model.entity.cloud.platform.ShopInfo;
import ltd.qcwifi.model.entity.cloud.push.content.PushContent;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 推广管家-推广内容
 * @author Administrator
 *
 */
@RequestMapping("/push/content")
@Controller
public class PushContentController extends BaseController {
	
	@Autowired
	PushContentService contentService;

	/**
	 * 推广内容视图
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("currentHeader", "content");
		return Web.PUSH + "content";
	}
	
	/**
	 * 分页展示推广内容信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public LayTableResult<PushContent> pagePushContent(@RequestParam("name")String name, 
			@RequestParam("page")Integer page, @RequestParam("limit")Integer limit) {
		// 设置分页
		PageHelper.startPage(page, limit);
		// 查询数据
		List<PushContent> contents = contentService.getList(TokenManager.getUserId(), name);
		// 获取分页信息
		PageInfo<PushContent> pageInfo = new PageInfo<PushContent>(contents);
		return new LayTableResult<PushContent>(pageInfo.getTotal(), contents);
	}
	
	
	/**
	 * 推广内容详情视图
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detailIndex(@PathVariable("id")String id, Model model) {
		model.addAttribute("currentHeader", "content");
		PushContent content = contentService.getById(TokenManager.getUserId(), id);
		model.addAttribute("content", content);
		return Web.PUSH + "content.detail";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult<String> deleteContent(@PathVariable("id")String id) {
		try {
			boolean flag = contentService.delete(TokenManager.getUserId(), id);
			if (flag) {
				return new ExecuteResult<>(webAppPath() + "push/content/index");
			}
			return new ExecuteResult<>(false, "删除推广内容失败，请重试");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "系统异常，请稍后再试");
		}
	}
}

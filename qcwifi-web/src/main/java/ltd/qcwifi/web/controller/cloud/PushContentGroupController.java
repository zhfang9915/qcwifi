package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.push.content.service.PushContentGroupService;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.push.content.PushContentGroup;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 推广管家-推广内容-广告主
 * @author Administrator
 *
 */
@RequestMapping("/push/content/group")
@Controller
public class PushContentGroupController extends BaseController {

	@Autowired
	PushContentGroupService groupService;
	/**
	 * 新建广告主
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<PushContentGroup> create(String name) {
		try {
			PushContentGroup g = new PushContentGroup(name, TokenManager.getUserId());
			boolean flag = groupService.create(g);
			if (flag) {
				return new ExecuteResult<PushContentGroup>(g);
			}
			return new ExecuteResult<>(false, "亲，广告主创建失败，请重试");
		} catch (Exception e) {
			return new ExecuteResult<>(false, "亲，广告主创建失败，请稍后重试");
		}
	}
	
	/**
	 * 查询广告主列表
	 * @Title: getGroups
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: ExecuteResult<List<PushContentGroup>>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<List<PushContentGroup>> getGroups() {
		try {
			List<PushContentGroup> groups = groupService.getByUserId(TokenManager.getUserId());
			if (groups != null && !groups.isEmpty()) {
				return new ExecuteResult<List<PushContentGroup>>(groups);
			}
			return new ExecuteResult<>(false, "您还没有广告主");
		} catch (Exception e) {
			return new ExecuteResult<>(false, "亲，广告主查询失败，请稍后重试");
		}
	}
}

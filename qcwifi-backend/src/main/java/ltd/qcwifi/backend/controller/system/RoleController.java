package ltd.qcwifi.backend.controller.system;

import java.util.ArrayList;
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
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.RoleParam;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.impl.RoleServiceImpl;
import ltd.qcwifi.system.service.impl.UserServiceImpl;
import net.sf.json.JSONObject;

/**
 * @author 张芳
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 注入角色管理service bean
	 */
	@Resource(name="roleService")
	RoleServiceImpl roleService;
	
	@Resource(name="userService")
	UserServiceImpl userService;
	
	/**
	 * 角色列表视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/role/query")
	public ModelAndView toRolePage(Model model) {
		logger.debug(">>>进入角色列表页....");
		model.addAttribute("roleActive", "active");
		return new ModelAndView(Web.FOLDER_SYSTEM + "role");
	}
	
	/**
	 * 表格展示角色列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/query")
	public TableResult<Role> table(@RequestBody String requestJson) {
		System.out.println(requestJson);
		RoleParam param = (RoleParam) JSONObject.toBean(JSONObject.fromObject(requestJson), RoleParam.class);
		return roleService.tables(param);
	}
	
	/**
	 * 创建角色
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/create")
	public ExecuteResult<String> createRole(Role role) {
		logger.debug("create role-->" + role);
		return roleService.create(role);
	}
	
	/**
	 * 批量删除角色
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/delete")
	public ExecuteResult<String> batchDeleteRole(@RequestBody List<Role> roles) {
		logger.debug("batch delete role-->" + super.toJson(roles));
		ExecuteResult<String> result = roleService.batchDelete(roles);
		if (result.isSuccess()) {
			List<Long> userIds = new ArrayList<>();
			for (Role role : roles) {
				userIds.addAll(userService.selectUserIdByRoleId(role.getId()));
			}
			TokenManager.clearUserAuthByUserId(userIds);
		}
		return result;
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/delete")
	public ExecuteResult<String> deleteRole(@PathVariable("id")Long id) {
		logger.debug("delete role-->" + id);
		ExecuteResult<String> result = roleService.delete(id);
		if (result.isSuccess()) {
			List<Long> userIds = new ArrayList<>();
			userIds.addAll(userService.selectUserIdByRoleId(id));
			TokenManager.clearUserAuthByUserId(userIds);
		}
		return result;
	}
	
	/**
	 * 根据ID获取角色信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/query")
	public ExecuteResult<Role> searchRoleById(@PathVariable("id")Long id) {
		logger.debug("search role-->" + id);
		return roleService.searchById(id);
	}
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/update")
	public ExecuteResult<String> updateRole(Role role) {
		logger.debug("update role-->" + role);
		return roleService.update(role);
	}
	
	
	
	
}

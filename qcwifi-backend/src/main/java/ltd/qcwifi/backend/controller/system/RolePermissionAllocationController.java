package ltd.qcwifi.backend.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.impl.RoleServiceImpl;
import ltd.qcwifi.system.service.impl.UserServiceImpl;

/**
 * 角色权限分配
 * @author 张芳
 */
@Controller
@RequestMapping("/role")
public class RolePermissionAllocationController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 注入权限管理service bean
	 */
	@Resource(name="roleService")
	RoleServiceImpl roleService;
	
	@Resource(name="userService")
	UserServiceImpl userService;
	
	/**
	 * 权限列表视图页面
	 * @return
	 */
	@RequestMapping(value = "/allocation/{roleId}", method = RequestMethod.GET)
	@RequiresPermissions("/role/permission/config")
	public ModelAndView toPermissionPage(@PathVariable("roleId")Long roleId) {
		logger.debug(">>>进入角色授权页....");
		Role role = roleService.searchById(roleId).getData();
		if (role == null) {
			//TODO
		}
		getRequest().setAttribute("role", role);
		return new ModelAndView(Web.FOLDER_SYSTEM + "role.permission");
	}
	
	/**
	 * 查询已有/未有权限列表
	 * @param method
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/allocation/{method}/{roleId}", method = RequestMethod.GET,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/query")
	public TableResult<Permission> existOrNotPermission(
			@PathVariable("method")String method, @PathVariable("roleId")Long roleId) {
		String description = getRequest().getParameter("search");//权限描述
		return roleService.existOrNotPermission(method, roleId, description);
	}
	
	/**
	 * 权限分配
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/config/{roleId}/permission/{method}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/permission/config")
	public ExecuteResult<String> permissionConfiguration(
			@PathVariable("roleId")Long roleId,
			@PathVariable("method")String method, 
			@RequestBody List<Permission> permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return new ExecuteResult<>(false, "请求参数错误");
		}
		ExecuteResult<String> result = roleService.permissionConfiguration(method, roleId, permissions);
		if (result.isSuccess()) {
			//更新拥有当前角色用户的权限缓存
			List<Long> userIds = userService.selectUserIdByRoleId(roleId);
			TokenManager.clearUserAuthByUserId(userIds);
		}
		return result;
	}
}

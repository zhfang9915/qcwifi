package ltd.qcwifi.backend.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.base.dao.mybatis.ecache.MybatisRedisCache;
import ltd.qcwifi.dao.system.PermissionDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.impl.RoleServiceImpl;
import ltd.qcwifi.system.service.impl.UserServiceImpl;

/**
 * 角色权限分配
 * @author 张芳
 */
@Controller
@RequestMapping("/user")
public class UserRoleAllocationController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 注入权限管理service bean
	 */
	@Resource(name="roleService")
	RoleServiceImpl roleService;
	
	@Resource(name = "userService")
	UserServiceImpl userService;
	
	@Autowired
	MybatisRedisCache mybatisRedisCache;
	
	/**
	 * 角色分配视图页面
	 * @return
	 */
	@RequestMapping(value = "/allocation/{userId}", method = RequestMethod.GET)
	@RequiresPermissions("/user/role/config")
	public ModelAndView toRolePage(@PathVariable("userId")Long userId) {
		logger.debug(">>>进入角色分配页....");
		User user = userService.searchById(userId).getData();
		getRequest().setAttribute("user", user);
		return new ModelAndView(Web.FOLDER_SYSTEM + "user.role");
	}
	
	/**
	 * 查询已有/未有角色列表
	 * @param method
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/allocation/{method}/{userId}", method = RequestMethod.GET,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/role/query")
	public TableResult<Role> existOrNotPermission(
			@PathVariable("method")String method, @PathVariable("userId")Long userId) {
		String description = getRequest().getParameter("search");//权限描述
		return userService.existOrNotRole(method, userId, description);
	}
	
	/**
	 * 角色分配
	 * @param userId
	 * @param method
	 * @param roles
	 * @return
	 */
	@RequestMapping(value = "/config/{userId}/role/{method}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/role/config")
	public ExecuteResult<String> permissionConfiguration(
			@PathVariable("userId")Long userId,
			@PathVariable("method")String method, 
			@RequestBody List<Role> roles) {
		if (roles == null || roles.isEmpty()) {
			return new ExecuteResult<>(false, "请求参数错误");
		}
		ExecuteResult<String> result = userService.roleConfiguration(method, userId, roles);
		if (result.isSuccess()) {
			//刷新数据库缓存
			mybatisRedisCache.clear(PermissionDao.class.getName());
			//更新拥有当前角色用户的角色缓存
			List<Long> userIds = new ArrayList<>();
			for (Role role : roles) {
				userIds.addAll(userService.selectUserIdByRoleId(role.getId()));
			}
			TokenManager.clearUserAuthByUserId(userIds);
		}
		return result;
	}
}

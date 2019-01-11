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
import ltd.qcwifi.model.dto.param.PermissionParam;
import ltd.qcwifi.model.entity.Permission;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.impl.PermissionServiceImpl;
import ltd.qcwifi.system.service.impl.UserServiceImpl;
import net.sf.json.JSONObject;

/**
 * 权限管理
 * @author 张芳
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 注入权限管理service bean
	 */
	@Resource(name="permissionService")
	PermissionServiceImpl permissionService;
	
	@Resource(name="userService")
	UserServiceImpl userService;
	
	/**
	 * 权限列表视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/permission/query")
	public ModelAndView toPermissionPage(Model model) {
		logger.debug(">>>进入权限列表页....");
		model.addAttribute("permissionActive", "active");
		return new ModelAndView(Web.FOLDER_SYSTEM + "permission");
	}
	
	/**
	 * 表格展示权限列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/query")
	public TableResult<Permission> table(@RequestBody String requestJson) {
		System.out.println(requestJson);
		PermissionParam param = (PermissionParam) JSONObject.toBean(JSONObject.fromObject(requestJson), PermissionParam.class);
		return permissionService.tables(param);
	}
	
	/**
	 * 创建权限
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/create")
	public ExecuteResult<String> createPermission(Permission p) {
		logger.debug("create permission-->" + p);
		ExecuteResult<String> result = permissionService.create(p);
		if (result.isSuccess()) {
			TokenManager.clearUserAuthByUserId(1L);//刷新系统管理员的权限缓存
		}
		return result;
	}
	
	/**
	 * 批量删除权限
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/delete")
	public ExecuteResult<String> batchDeletePermission(@RequestBody List<Permission> permissions) {
		logger.debug("batch delete permission-->" + super.toJson(permissions));
		ExecuteResult<String> result = permissionService.batchDelete(permissions);
		if (result.isSuccess()) {
			List<Long> users = userService.selectUserIdByPermission(permissions);
			TokenManager.clearUserAuthByUserId(users);//刷新拥有此权限用户的权限缓存
		}
		return result;
	}
	
	/**
	 * 删除权限
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/delete")
	public ExecuteResult<String> deletePermission(@PathVariable("id")Long id) {
		logger.debug("delete permission-->" + id);
		ExecuteResult<String> result = permissionService.delete(id);
		if (result.isSuccess()) {
			List<Permission> permissions = new ArrayList<>();
			permissions.add(new Permission(id));
			List<Long> users = userService.selectUserIdByPermission(permissions);
			TokenManager.clearUserAuthByUserId(users);//刷新拥有此权限用户的权限缓存
		}
		return result;
	}
	
	/**
	 * 根据ID获取权限信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/query")
	public ExecuteResult<Permission> searchPermissionById(@PathVariable("id")Long id) {
		logger.debug("search permission-->" + id);
		return permissionService.searchById(id);
	}
	
	/**
	 * 更新权限信息
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/permission/update")
	public ExecuteResult<String> updatePermission(Permission permission) {
		logger.debug("update permission-->" + permission);
		ExecuteResult<String> result = permissionService.update(permission);
		if (result.isSuccess()) {
			List<Permission> permissions = new ArrayList<>();
			permissions.add(permission);
			List<Long> users = userService.selectUserIdByPermission(permissions);
			TokenManager.clearUserAuthByUserId(users);//刷新拥有此权限用户的权限缓存
		}
		return result;
	}
}

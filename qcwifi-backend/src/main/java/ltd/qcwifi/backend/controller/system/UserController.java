package ltd.qcwifi.backend.controller.system;

import java.util.List;

import javax.annotation.Resource;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.model.bo.UserOnline;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.UserParam;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.shiro.session.CustomSessionManager;
import ltd.qcwifi.shiro.util.SimpleHashUtil;
import ltd.qcwifi.system.service.impl.UserServiceImpl;
import net.sf.json.JSONObject;

/**
 * 用户管理
 * @author 张芳
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource(name = "userService")
	UserServiceImpl userService;
	
	@Autowired
	CustomSessionManager customSessionManager;
	
	/**
	 * 用户列表视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/user/query")
	public ModelAndView toUserPage(Model model) {
		logger.debug(">>>进入用户列表页....");
		model.addAttribute("userActive", "active");
		return new ModelAndView(Web.FOLDER_SYSTEM + "user");
	}
	
	/**
	 * 表格展示用户列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/query")
	public TableResult<User> table(@RequestBody String requestJson) {
		System.out.println(requestJson);
		UserParam param = (UserParam) JSONObject.toBean(JSONObject.fromObject(requestJson), UserParam.class);
		return userService.tables(param);
	}
	
	/**
	 * 创建会员
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/create")
	public ExecuteResult<String> createUser(User user) {
		// 生成随机加密盐值
		user.setSalt(RandCodeUtil.getRandomString(16));
		//生成加密密码
		String result = SimpleHashUtil.createMD5Password(user.getPassword(), user.getCredentialsSalt());
		logger.debug("加密后的密码：" + result);
		user.setPassword(result);
		user.setType(0);
		logger.debug("create user-->" + user);
		return userService.create(user);
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/delete")
	public ExecuteResult<String> deleteUser(@PathVariable("id")Long id) {
		logger.debug("delete user-->" + id);
		return userService.delete(id);
	}
	
	/**
	 * 批量删除会员
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/delete")
	public ExecuteResult<String> batchDeleteUser(@RequestBody List<User> users) {
		logger.debug("batch delete user-->" + super.toJson(users));
		return userService.batchDelete(users);
	}
	
	/**
	 * 根据ID获取用户信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/query")
	public ExecuteResult<User> searchRoleById(@PathVariable("id")Long id) {
		logger.debug("search user-->" + id);
		return userService.searchById(id);
	}
	
	/**
	 * 验证用户名是否被注册
	 * @return
	 */
	@RequestMapping(value = "/validation/{username}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/query")
	public ExecuteResult<String> validationUser(@PathVariable("username")String username) {
		User user = userService.findByUsername(username);
		if (user == null) {
			return new ExecuteResult<>(false, null);
		}
		return new ExecuteResult<>(null);
	}
	
	/**
	 * 更新会员账号昵称和状态信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/update")
	public ExecuteResult<String> updateRole(User user) {
		logger.debug("update user-->" + user);
		return userService.updateUserNickAndLock(user);
	}
	
	
	/**
	 * 密码更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/user/changepwd")
	public ExecuteResult<String> changepwd(@RequestParam("id")Long id, @RequestParam("oldpwd")String oldPassword, 
			@RequestParam("newpwd")String newPassword) {
		//根据ID查找用户信息
		User user = userService.searchById(id).getData();
		if (user == null) {
			return new ExecuteResult<>(false, "用户信息错误，密码修改失败");
		}
		//比对原始密码
		String enOldPassword = SimpleHashUtil.createMD5Password(oldPassword, user.getCredentialsSalt());
		if (!enOldPassword.equals(user.getPassword())) {
			return new ExecuteResult<>(false, "原密码错误，密码修改失败");
		}
		//生成新的加密密码
		String enNewPassword = SimpleHashUtil.createMD5Password(newPassword, user.getCredentialsSalt());
		return userService.updatePasswordByUsername(user.getUsername(), enNewPassword);
	}
	
	@RequestMapping(value="/online")
	@ResponseBody
	public List<UserOnline> online(){
		List<UserOnline> list = customSessionManager.getAllUser();
		return list;
	}
}

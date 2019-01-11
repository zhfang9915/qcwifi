package ltd.qcwifi.web.controller.cloud;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.service.OperateService;
import ltd.qcwifi.cloud.service.ShopInfoService;
import ltd.qcwifi.cloud.service.ShopMarkService;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.LayTableResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.BaseLayTablePage;
import ltd.qcwifi.model.dto.param.ShopInfoParam;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.cloud.platform.ShopInfo;
import ltd.qcwifi.model.entity.cloud.platform.SubAccount;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.shiro.util.SimpleHashUtil;
import ltd.qcwifi.system.service.UserService;
import ltd.qcwifi.system.service.impl.UserServiceImpl;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/shop/sub")
public class ShopSubController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(ShopSubController.class);
	
	@Autowired
	ShopInfoService shopInfoService;
	
	@Autowired
	UserServiceImpl userService;
	
	/**
	 * 商铺创建连锁帐号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sub/index")
	public String subAccountIndex(Model model) {
		model.addAttribute("currentHeader", "sub");
		return Web.FOLDER_CLOUD_SHOP + "sub.list";
	}
	
	/**
	 * 分页展示商铺信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/list")
	public LayTableResult<SubAccount> pageSubAccount(@RequestParam("page")Integer page, @RequestParam("limit")Integer limit) {
		// 设置分页
		PageHelper.startPage(page,limit);
		// 查询数据
		List<SubAccount> sas = shopInfoService.getSubAccounts(TokenManager.getUserId());
		// 获取分页信息
		PageInfo<SubAccount> pageInfo = new PageInfo<SubAccount>(sas);
		return new LayTableResult<SubAccount>(pageInfo.getTotal(), sas);
	}
	
	/**
	 * 商铺创建连锁帐号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sub/new")
	public String subAccountNew(Model model) {
		model.addAttribute("currentHeader", "sub");
		return Web.FOLDER_CLOUD_SHOP + "sub.new";
	}

	/**
	 * 验证账户
	 * @return
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/validate")
	public ExecuteResult<String> validate(String username) {
		User user = userService.findByUsername(username);
		if (user == null) {
			return new ExecuteResult<>("");
		} else {
			return new ExecuteResult<>(false, "该账号已经被占用");
		}
	}
	
	/**
	 * 提交新连锁账号
	 * @param sa
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/new")
	public ExecuteResult<String> createSubAccount(SubAccount sa) {
		sa.setCreateBy(TokenManager.getUserId());
		// 生成随机加密盐值
		sa.setSalt(RandCodeUtil.getRandomString(16));
		//生成加密密码
		String result = SimpleHashUtil.createMD5Password(sa.getPassword(), sa.getCredentialsSalt());
		sa.setPassword(result);
		boolean r = false;
		try {
			r = shopInfoService.createShopSubAccount(sa);
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "系统异常，创建连锁账号失败");
		}
		if (r) {
			return new ExecuteResult<>(webAppPath() + "shop/sub/index");
		}
		return new ExecuteResult<>(false, "创建连锁账号失败");
	}
	
	/**
	 * 修改连锁账号信息
	 * @param id
	 * @param userId
	 * @param val
	 * @param filed
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/edit")
	public ExecuteResult<String> editSubAccount(
			@PathVariable("id") Long id, 
			@RequestParam("userId") Long userId,
			@RequestParam("value") Object val,
			@RequestParam("filed") String filed) {
		if ("locked".equals(filed)) {
			val = Boolean.parseBoolean(val.toString()) ? false : true;
		}
		boolean r = false;
		try {
			r = shopInfoService.updateSubAccount(filed, val, id, userId, TokenManager.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "系统异常，创建连锁账号失败");
		}
		if (r) {
			return new ExecuteResult<>("修改连锁账号成功");
		}
		return new ExecuteResult<>(false, "修改连锁账号失败");
	}
	
	/**
	 * 修改连锁帐号登录密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pass/{id}", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sub/pass")
	public String changePass(@PathVariable("id")Long id, Model model) {
		model.addAttribute("currentHeader", "sub");
		SubAccount sa = shopInfoService.getSubAccountById(id, TokenManager.getUserId());
		if (sa == null) {
			model.addAttribute("result", false);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("sub_username", sa.getUsername());
			model.addAttribute("sub_id", sa.getId());
		}
		return Web.FOLDER_CLOUD_SHOP + "sub.changepass";
	}
	
	/**
	 * 修改连锁帐号登录密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pass/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/pass")
	public ExecuteResult<String> changePass(@PathVariable("id")Long id) {
		SubAccount sa = shopInfoService.getSubAccountById(id, TokenManager.getUserId());
		if (sa == null) {
			return new ExecuteResult<>(false, "连锁账号信息错误，密码修改失败");
		}else {
			//根据ID查找用户信息
			User user = userService.searchById(sa.getUserId()).getData();
			if (user == null) {
				return new ExecuteResult<>(false, "连锁账号信息错误，密码修改失败");
			}
			//比对原始密码
			String oldPassword = getRequest().getParameter("oldpassword");
			String enOldPassword = SimpleHashUtil.createMD5Password(oldPassword, user.getCredentialsSalt());
			if (!enOldPassword.equals(user.getPassword())) {
				return new ExecuteResult<>(false, "旧密码错误，密码修改失败");
			}
			//生成新的加密密码
			String password = getRequest().getParameter("password");
			String enNewPassword = SimpleHashUtil.createMD5Password(password, user.getCredentialsSalt());
			return userService.updatePasswordByUsername(user.getUsername(), enNewPassword);
		}
	}
	
	/**
	 * 删除连锁帐号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/del")
	public ExecuteResult<String> deleteSubAccount(SubAccount sa) {
		boolean r = shopInfoService.delSubAccount(sa.getId(), sa.getUserId(), TokenManager.getUserId());
		if (r) {
			return new ExecuteResult<String>("删除连锁账号成功");
		}
		return new ExecuteResult<String>(false, "删除连锁账号失败");
	}
	
	/**
	 * 管理连锁账户的商铺
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chain/{id}", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sub/chain")
	public String chain(@PathVariable("id")Long id, Model model) {
		model.addAttribute("currentHeader", "sub");
		SubAccount sa = shopInfoService.getSubAccountById(id, TokenManager.getUserId());
		if (sa == null) {
			model.addAttribute("result", false);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("sub_nickname", sa.getNickname());
			model.addAttribute("sub_id", sa.getId());
		}
		return Web.FOLDER_CLOUD_SHOP + "sub.chain";
	}
	
	/**
	 * 管理连锁账户的商铺
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chain/{type}/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/chain")
	public LayTableResult<ShopInfo> chain(@PathVariable("id")Long id, @PathVariable("type")String type, 
			@RequestParam("page")Integer page, @RequestParam("limit")Integer limit) {
		// 设置分页
		PageHelper.startPage(page,limit);
		// 查询数据
		List<ShopInfo> ss = new ArrayList<>();
		if ("free".equals(type)) {
			ss = shopInfoService.getSubFreeShop(id, TokenManager.getUserId());
		}else if ("checked".equals(type)) {
			ss = shopInfoService.getSubCheckedShop(id, TokenManager.getUserId());
		}
		// 获取分页信息
		PageInfo<ShopInfo> pageInfo = new PageInfo<ShopInfo>(ss);
		return new LayTableResult<>(pageInfo.getTotal(), ss);
	}
	
	/**
	 * 添加/删除商铺
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chains/{type}/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sub/chains")
	public ExecuteResult<String> chain(@PathVariable("id")Long id, @PathVariable("type")String type,@RequestBody List<ShopInfo> shops) {
		boolean r = false;
		try {
			if ("add".equals(type)) {
				r = shopInfoService.addShopSub(id, TokenManager.getUserId(), shops);
				if (r) {
					return new ExecuteResult<>("操作成功");
				}
			}else if ("remove".equals(type)) {
				r = shopInfoService.removeShopSub(id, TokenManager.getUserId(), shops);
				if (r) {
					return new ExecuteResult<>("操作成功");
				}
			}
		} catch (Exception e) {
			return new ExecuteResult<>(false, "系统服务忙，请刷新后重试");
		}
		return new ExecuteResult<>(false, "操作失败，请刷新后重试");
	}
}

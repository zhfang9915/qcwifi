package ltd.qcwifi.backend.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ltd.qcwifi.backend.Web;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.system.service.RoleService;
import ltd.qcwifi.system.service.UserService;

/**
 * 登录控制器
 * @author 张芳
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	/**
	 * 打开登录页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		
		return new ModelAndView("login/login");
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(User user, @RequestParam(value = "rememberMe", defaultValue="0")Boolean rememberMe, 
			@RequestParam(value = "rand")String rand, RedirectAttributes redirectAttributes, HttpServletResponse response){
		redirectAttributes.addFlashAttribute("username", user.getUsername());
		redirectAttributes.addFlashAttribute("password", user.getPassword());
		try {  
			if (super.validateRandCode(rand)) {
				user = TokenManager.login(user,rememberMe);
				//判断角色是否是管理员
				Set<String> roles = roleService.findRolesByUserId(TokenManager.getUserId());
				if (roles.contains("sadmin") || roles.contains("admin")) {
					//登录成功 则查询用户的账户信息 存入session
					User userInfo = userService.findByUsername(user.getUsername());
					getSession().setAttribute(Web.LOGIN_USER, userInfo);
					
					SavedRequest savedRequest = WebUtils.getSavedRequest(getRequest());
					if (savedRequest == null) {
						return "redirect:/index";
					}
					response.sendRedirect(savedRequest.getRequestUrl());
					return null;
				}else {
					TokenManager.logout();
					redirectAttributes.addFlashAttribute("errorMsg", "您未授权登录"); 
				}
			}else {
				redirectAttributes.addFlashAttribute("errorMsg", "验证码错误"); 
			}
	    } catch (IncorrectCredentialsException e) {  
	    	redirectAttributes.addFlashAttribute("errorMsg", "账号或密码错误");  
	    } catch (ExcessiveAttemptsException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "登录失败次数过多");
	    } catch (LockedAccountException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "账号已被锁定");
	    } catch (DisabledAccountException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "账号已被禁用"); 
	    } catch (ExpiredCredentialsException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "账号已过期");   
	    } catch (UnknownAccountException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "账号不存在");   
	    } catch (UnauthorizedException e) {  
	        redirectAttributes.addFlashAttribute("errorMsg", "您未被授权访问");   
	    }  catch (Exception e) {
	    	e.printStackTrace();
	    	redirectAttributes.addFlashAttribute("errorMsg", "系统异常"); 
		}
		return "redirect:/login/index";
	}
}

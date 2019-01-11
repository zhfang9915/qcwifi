package ltd.qcwifi.web.controller.cloud;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.comm.utils.RandomUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.cloud.platform.UserBinding;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.shiro.util.SimpleHashUtil;
import ltd.qcwifi.web.Web;

/**
 * 个人中心 - 安全设备绑定
 * @author Administrator
 *
 */
@Controller
public class SafeSettingsController extends AbstractPersonalCenterController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 安全设置视图
	 * @return
	 */
	@RequestMapping(value = "/safe/settings", method = RequestMethod.GET)
	public String safeSettings(Model model) {
		model.addAttribute("currentPage", "safeSetting");
		
		//获取用户的安全设备信息
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("binding", ub);
		//安全中心入口
		getSession().setAttribute("safeStart", "safeStart");
		
		return Web.PERSONAL_CENTER + "safeSetting";
	}
	
	/**
	 * 修改当前用户的密码信息
	 * @return
	 */
	@RequestMapping(value = "/password/change", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> changePassword() {
		String oldPassword = getRequest().getParameter("oldPwd");
		String newPassword = getRequest().getParameter("newPwd");
		String newPasswordConfim = getRequest().getParameter("newPwdConfim");
		if (newPassword == null || !newPassword.equals(newPasswordConfim)) {
			logger.info("密码修改失败:新密码两次输入不一致");
			return new ExecuteResult<String>(false, "新密码两次输入不一致");
		}
		
		//获取当前用户
		User user = TokenManager.getToken();
		logger.info("当前用户信息为:{}", user);
		//比对原始密码
		String enOldPassword = SimpleHashUtil.createMD5Password(oldPassword, user.getCredentialsSalt());
		if (!enOldPassword.equals(user.getPassword())) {
			logger.info("密码修改失败:原密码错误，密码修改失败");
			return new ExecuteResult<>(false, "原密码错误，密码修改失败");
		}
		//生成新的加密密码
		String enNewPassword = SimpleHashUtil.createMD5Password(newPassword, user.getCredentialsSalt());
		return userService.updatePasswordByUsername(user.getUsername(), enNewPassword);
	}
	
	/**
	 * 绑定手机号视图
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/binding/phone", method = RequestMethod.GET)
	public String bindingPhone(Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		model.addAttribute("currentPage", "safeSetting");
		return Web.PERSONAL_CENTER + "safeSetting.binding.phone";
	}
	
	/**
	 * 发送短信验证码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sms/code", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteResult<String> sendSMScode() {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String phone = getRequest().getParameter("p");
		if (StringUtils.isBlank(phone)) {
			return new ExecuteResult<>(false, "请输入手机号");
		}
		String imgCode = getRequest().getParameter("c");
		if (StringUtils.isBlank(imgCode)) {
			return new ExecuteResult<>(false, "请输入图形验证码");
		}
		if (!super.validateRandCode(imgCode)) {
			return new ExecuteResult<>(false, "图形验证码错误，请重新输入");
		}
		//生产随机数字
		String phoneCode = RandomUtil.getRandCode();
		logger.debug(phone + " 短信验证码为： " + phoneCode);
		getSession().setAttribute("phoneCode", phoneCode);
		//同时将手机号存入Session
		getSession().setAttribute("phone", phone);
		
		String content = "【前辰科技】 尊敬的客户，您本次的验证码：" + phoneCode + "，请不要轻易泄漏给他人，本公司工作人员不会以任何理由向您索要验证码信息。感谢您对前辰科技的支持！";
		boolean isSuccess = smsService.sendMsg(phone, content);
		if (!isSuccess) {
			return new ExecuteResult<>(false, "您的操作太频繁，请稍后");
		}
		return new ExecuteResult<String>("发送成功");
	}
	
	/**
	 * 绑定手机 提交
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/binding/phone", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> submitBindingPhone() {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String phone = getRequest().getParameter("phone");
		if (StringUtils.isBlank(phone)) {
			return new ExecuteResult<>(false, "请输入手机号");
		}
		//校验获取验证码的手机号和当前提交的是否同一个
		String sPhone = (String) getSession().getAttribute("phone");
		if (!phone.equals(sPhone)) {
			return new ExecuteResult<>(false, phone + "不是接收短信验证码的手机号");
		}
		String imgCode = getRequest().getParameter("imgCode");
		if (StringUtils.isBlank(imgCode)) {
			return new ExecuteResult<>(false, "请输入图形验证码");
		}
		if (!super.validateRandCode(imgCode)) {
			return new ExecuteResult<>(false, "图形验证码错误，请重新输入");
		}
		
		//校验短信验证码
		String smsCode = getRequest().getParameter("smsCode");
		if (StringUtils.isBlank(smsCode)) {
			return new ExecuteResult<>(false, "请输入短信验证码");
		}
		String ssmsCode = (String) getSession().getAttribute("phoneCode");
		if (!smsCode.equals(ssmsCode)) {
			return new ExecuteResult<>(false, "短信验证码错误，请重新输入");
		}
		
		//验证当前手机是否已经绑定了账号
		boolean isBinding = userBindingService.isBindingPhone(phone);
		if (isBinding) {
			return new ExecuteResult<String>(false, "手机号" + phone + "已绑定了其他账号");
		}
		
		//校验通过后 绑定手机号
		boolean binding = userBindingService.bindingPhone(TokenManager.getUserId(), phone);
		if (binding) {
			return new ExecuteResult<String>(webAppPath() + "personal/center/safe/settings");
		}
		return new ExecuteResult<String>(false, "绑定失败，请重试！");
	}
	
	/**
	 * 绑定邮箱视图
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/binding/email", method = RequestMethod.GET)
	public String bindingEmail(Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		model.addAttribute("currentPage", "safeSetting");
		return Web.PERSONAL_CENTER + "safeSetting.binding.email";
	}
	
	/**
	 * 发送短信验证码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/email/code", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteResult<String> sendEmailcode() {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String email = getRequest().getParameter("e");
		if (StringUtils.isBlank(email)) {
			return new ExecuteResult<>(false, "请输入邮箱账号");
		}
		String imgCode = getRequest().getParameter("c");
		if (StringUtils.isBlank(imgCode)) {
			return new ExecuteResult<>(false, "请输入图形验证码");
		}
		if (!super.validateRandCode(imgCode)) {
			return new ExecuteResult<>(false, "图形验证码错误，请重新输入");
		}
		
		//生产随机数字
		String emailCode = RandomUtil.getRandCode();
		logger.debug(email + " 验证码为： " + emailCode);
		getSession().setAttribute("emailCode", emailCode);
		//同时将手机号存入Session
		getSession().setAttribute("email", email);
		
		String content = "【前辰科技】 尊敬的客户，您本次的验证码：" + emailCode + "，请不要轻易泄漏给他人，本公司工作人员不会以任何理由向您索要验证码信息。感谢您对前辰科技的支持！";
		boolean isSuccess = mailService.sendRandCode(email, "绑定邮箱验证码", content);
		if (!isSuccess) {
			return new ExecuteResult<>(false, "您的操作太频繁，请稍后");
		}
		return new ExecuteResult<String>("发送成功");
	}
	
	/**
	 * 绑定邮箱 提交
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/binding/email", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> submitBindingEmail() {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String email = getRequest().getParameter("email");
		if (StringUtils.isBlank(email)) {
			return new ExecuteResult<>(false, "请输入邮箱账号");
		}
		//校验获取邮箱验证码的邮箱账号和当前提交的是否同一个
		String sEmail = (String) getSession().getAttribute("email");
		if (!email.equals(sEmail)) {
			return new ExecuteResult<>(false, email + "不是接收验证码的邮箱账号");
		}
		String imgCode = getRequest().getParameter("imgCode");
		if (StringUtils.isBlank(imgCode)) {
			return new ExecuteResult<>(false, "请输入图形验证码");
		}
		if (!super.validateRandCode(imgCode)) {
			return new ExecuteResult<>(false, "图形验证码错误，请重新输入");
		}
		
		//校验邮箱验证码
		String emailCode = getRequest().getParameter("emailCode");
		if (StringUtils.isBlank(emailCode)) {
			return new ExecuteResult<>(false, "请输入邮箱验证码");
		}
		String sEmailCode = (String) getSession().getAttribute("emailCode");
		if (!emailCode.equals(sEmailCode)) {
			return new ExecuteResult<>(false, "短信验证码错误，请重新输入");
		}
		
		//验证当前手机是否已经绑定了账号
		boolean isBinding = userBindingService.isBindingEmail(email);
		if (isBinding) {
			return new ExecuteResult<String>(false, "邮箱账号" + email + "已绑定了其他账号");
		}
		
		//校验通过后 绑定手机号
		boolean binding = userBindingService.bindingEmail(TokenManager.getUserId(), email);
		if (binding) {
			return new ExecuteResult<String>(webAppPath() + "personal/center/safe/settings");
		}
		return new ExecuteResult<String>(false, "绑定失败，请重试！");
	}
	
	/**
	 * 选择验证方式试图
	 * @return
	 */
	@RequestMapping(value = "/safe/change/{changeType}/choose", method = RequestMethod.GET)
	public String changBinding(@PathVariable("changeType")String changeType, Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("changeType", changeType);
		model.addAttribute("phone", ub.getPhone());
		model.addAttribute("email", ub.getEmail());
		return Web.PERSONAL_CENTER + "safeSetting.change.step1";
	}
	
	/**
	 * 验证所选方式的视图
	 * @return
	 */
	@RequestMapping(value = "/change/{changeType}/{byType}", method = RequestMethod.GET)
	public String changBindingValidation(@PathVariable("changeType")String changeType, 
			@PathVariable("byType")String byType, Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("changeType", changeType);
		model.addAttribute("byType", byType);
		model.addAttribute("phone", ub.getPhone());
		model.addAttribute("email", ub.getEmail());
		return Web.PERSONAL_CENTER + "safeSetting.change.step2";
	}
	
	/**
	 * 根据消息类型发送验证码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/code/{msgType}", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteResult<String> sendMsgCode(@PathVariable("msgType")String msgType) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		UserBinding ub = userBindingService.getUserBindingByUserIdclear(TokenManager.getUserId());
		
		//生产随机数字
		String bindingRandCode = RandomUtil.getRandCode();
		logger.debug("修改绑定验证码为： " + bindingRandCode);
		getSession().setAttribute("bindingRandCode", bindingRandCode);
		
		String content = "【前辰科技】 尊敬的客户，您本次的验证码：" + bindingRandCode + "，请不要轻易泄漏给他人，本公司工作人员不会以任何理由向您索要验证码信息。感谢您对前辰科技的支持！";
		boolean isSuccess = true;
		if ("phone".equals(msgType)) {
			isSuccess = smsService.sendMsg(ub.getPhone(), content);
		}else if ("email".equals(msgType)) {
			isSuccess = mailService.sendRandCode(ub.getEmail(), "邮箱验证码", content);
		}
		if (!isSuccess) {
			return new ExecuteResult<>(false, "您的操作太频繁，请稍后");
		}
		return new ExecuteResult<String>("发送成功");
	}
	
	/**
	 * 验证随机码 成功则跳转到新设备修改页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/validation/code", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> validationMsgCode() {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String code = getRequest().getParameter("code");
		if (StringUtils.isBlank(code)) {
			return new ExecuteResult<>(false, "请输入验证码");
		}
		String scode = (String) getSession().getAttribute("bindingRandCode");
		if (!code.equals(scode)) {
			return new ExecuteResult<>(false, "验证码错误，请重新输入");
		}
		String changeType = getRequest().getParameter("changeType");
		String byType = getRequest().getParameter("byType");
		return new ExecuteResult<String>(webAppPath() + "personal/center/newbinding/" + changeType + "?t=" + byType);
	}
	
	/**
	 * 验证随机码 成功则跳转到新设备修改页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newbinding/{changeType}", method = RequestMethod.GET)
	public String newBinding(@PathVariable("changeType")String changeType, Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		String byType = getRequest().getParameter("t");
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("changeType", changeType);
		model.addAttribute("byType", byType);
		model.addAttribute("phone", ub.getPhone());
		model.addAttribute("email", ub.getEmail());
		return Web.PERSONAL_CENTER + "safeSetting.change.step3";
	}
	
	/**
	 * 根据消息类型发送验证码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newbinding/{changeType}/code", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteResult<String> sendNewBindingCode(@PathVariable("changeType")String changeType) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String binding = getRequest().getParameter("b");
		//生产随机数字
		String newBindingRandCode = RandomUtil.getRandCode();
		logger.debug("修改绑定验证码为： " + newBindingRandCode);
		getSession().setAttribute("newBindingRandCode", newBindingRandCode);
		
		String content = "【前辰科技】 尊敬的客户，您本次的验证码：" + newBindingRandCode + "，请不要轻易泄漏给他人，本公司工作人员不会以任何理由向您索要验证码信息。感谢您对前辰科技的支持！";
		boolean isSuccess = true;
		if ("phone".equals(changeType)) {
			isSuccess = smsService.sendMsg(binding, content);
		}else if ("email".equals(changeType)) {
			isSuccess = mailService.sendRandCode(binding, "邮箱验证码", content);
		}
		if (!isSuccess) {
			return new ExecuteResult<>(false, "您的操作太频繁，请稍后");
		}
		return new ExecuteResult<String>("发送成功");
	}
	
	/**
	 * 验证随机码 成功则跳转到新设备修改页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newbinding/{changeType}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> submitNewBinding(@PathVariable("changeType")String changeType, Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return new ExecuteResult<>(false, "请从安全中心入口进行操作");
		}
		String binding = getRequest().getParameter("binding");
		if (StringUtils.isBlank(binding)) {
			if ("phone".equals(changeType)) {
				return new ExecuteResult<>(false, "请输入新的手机号");
			}else if ("email".equals(changeType)) {
				return new ExecuteResult<>(false, "请输入新的邮箱");
			}
		}
		String code = getRequest().getParameter("code");
		if (StringUtils.isBlank(code)) {
			return new ExecuteResult<>(false, "请输入验证码");
		}
		String scode = (String) getSession().getAttribute("newBindingRandCode");
		if (!code.equals(scode)) {
			return new ExecuteResult<>(false, "验证码错误，请重新输入");
		}
		boolean isBinding = false;
		if ("phone".equals(changeType)) {
			isBinding = userBindingService.bindingPhone(TokenManager.getUserId(), binding);
		}else if ("email".equals(changeType)) {
			isBinding = userBindingService.bindingEmail(TokenManager.getUserId(), binding);
		}
		if (!isBinding) {
			return new ExecuteResult<String>(false, "修改绑定失败，请重试！");
		}
		return new ExecuteResult<String>(webAppPath() + "personal/center/newbinding/" + changeType + "/success");
	}
	
	/**
	 * 验证随机码 成功则跳转到新设备修改页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newbinding/{changeType}/success", method = RequestMethod.GET)
	public String newBindingSuccess(@PathVariable("changeType")String changeType, Model model) {
		//判断请求入口是否安全中心首页
		Object safeStart = getSession().getAttribute("safeStart");
		if (safeStart == null) {
			return "redirect:/error/notstart";
		}
		//最后一步移除入口标志
		getSession().removeAttribute("safeStart");
		
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("changeType", changeType);
		model.addAttribute("phone", ub.getPhone());
		model.addAttribute("email", ub.getEmail());
		return Web.PERSONAL_CENTER + "safeSetting.change.step4";
	}
}

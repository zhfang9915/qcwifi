package ltd.qcwifi.web.controller.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import ltd.qcwifi.cloud.platform.service.MailService;
import ltd.qcwifi.cloud.platform.service.SmsService;
import ltd.qcwifi.cloud.service.UserBindingService;
import ltd.qcwifi.system.service.UserService;
import ltd.qcwifi.web.controller.base.BaseController;

@RequestMapping("/personal/center")
public abstract class AbstractPersonalCenterController extends BaseController {

	/**
	 * 用户信息服务
	 */
	@Autowired
	UserService userService;
	
	/**
	 * 用户安全设备绑定服务
	 */
	@Autowired
	UserBindingService userBindingService;
	
	/**
	 * 短信接口服务
	 */
	@Autowired
	SmsService smsService;
	
	/**
	 * 邮件接口服务
	 */
	@Autowired
	MailService mailService;
	
	
}

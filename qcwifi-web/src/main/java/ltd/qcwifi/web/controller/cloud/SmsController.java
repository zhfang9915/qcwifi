package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.service.OrderService;
import ltd.qcwifi.cloud.service.UserBindingService;
import ltd.qcwifi.cloud.service.impl.SmsPackageServiceImpl;
import ltd.qcwifi.cloud.service.impl.SmsSettingServiceImpl;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.OrderMain;
import ltd.qcwifi.model.entity.cloud.platform.SmsPacekage;
import ltd.qcwifi.model.entity.cloud.platform.SmsRechargeHistory;
import ltd.qcwifi.model.entity.cloud.platform.SmsSetting;
import ltd.qcwifi.model.entity.cloud.platform.UserBinding;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

@RequestMapping("/shop/sms")
@Controller
public class SmsController extends BaseController {

	/**
	 * 短信业务服务接口
	 */
	@Autowired
	SmsSettingServiceImpl settingService;
	
	@Autowired
	SmsPackageServiceImpl packageService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserBindingService userBindingService;
	
	/**
	 * 短信首页首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sms/recharge")
	public String rechargeIndex(Model model) {
		model.addAttribute("currentPage", "recharge");
		model.addAttribute("currentHeader", "sms");
		//查询当前短信余量
		SmsSetting setting = settingService.querySmsInfo(TokenManager.getUserId());
		if (setting == null) {
			//没有短信业务记录则新增
			model.addAttribute("smsOverPlus", 0);
			SmsSetting ns = new SmsSetting();
			ns.setUserId(TokenManager.getUserId());
			settingService.create(ns);
		}
		model.addAttribute("smsOverPlus", setting.getCount());
		return Web.FOLDER_CLOUD_SHOP + "sms.recharge.step1";
	}
	
	@RequestMapping(value = "/packages", method = RequestMethod.GET,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sms/packages")
	public ExecuteResult<List<SmsPacekage>> getSmsPackages() {
		//短信包信息
		List<SmsPacekage> sps = packageService.querySmsPackages();
		if (sps == null || sps.isEmpty()) {
			return new ExecuteResult<>(false, "暂无短信包信息");
		}
		return new ExecuteResult<>(sps);
	}
	
	@RequestMapping(value = "/recharge/{smsPackageId}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sms/recharge")
	public ExecuteResult<String> recharge(@PathVariable("smsPackageId")String smsPackageId) {
		//获取要重置的短信包
//		String smsPackageId = getRequest().getParameter("smsPackageId");
		//获取短信
		SmsPacekage sp = packageService.querySmsPackageById(smsPackageId);
		
		//保存订单信息
		ExecuteResult<Long> r = orderService.saveSmsOrder(TokenManager.getUserId(), sp);
		if (r.isSuccess()) {
			return new ExecuteResult<>("recharge/success/" + r.getData());
		}
		return new ExecuteResult<>(false, r.getMsg());
	}
	
	/**
	 * 订单提交成功
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge/success/{orderNo}", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sms/recharge/success")
	public String rechargeSuccess(@PathVariable("orderNo")Long orderNo, Model model) {
		model.addAttribute("currentPage", "recharge");
		model.addAttribute("currentHeader", "sms");
		OrderMain order = orderService.selectOrderByNo(orderNo, TokenManager.getUserId());
		model.addAttribute("order", order);
		return Web.FOLDER_CLOUD_SHOP + "sms.recharge.step2";
	}
	
	/**
	 * 短信订单支付
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge/pay/{orderNo}", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sms/recharge/pay")
	public String rechargePay(@PathVariable("orderNo")Long orderNo, Model model) {
		model.addAttribute("currentPage", "recharge");
		model.addAttribute("currentHeader", "sms");
		return Web.FOLDER_CLOUD_SHOP + "sms.recharge.step1";
	}
	
	
	/**
	 * 短信充值成功记录
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge/history", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sms/recharge/history")
	public String rechargeSuccessHistory(Model model) {
		model.addAttribute("currentPage", "history");
		model.addAttribute("currentHeader", "sms");
		return Web.FOLDER_CLOUD_SHOP + "sms.recharge.history";
	}
	
	/**
	 * 短信充值成功记录查询
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge/history", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("/shop/sms/recharge/history")
	public ExecuteResult<List<SmsRechargeHistory>> rechargeSuccessHistory() {
		String start = getRequest().getParameter("start");
		String end = getRequest().getParameter("end");
		List<SmsRechargeHistory> srhs = packageService.rechargeHistory(TokenManager.getUserId(), start, end);
		return new ExecuteResult<List<SmsRechargeHistory>>(srhs);
	}
	
	/**
	 * 短信告警通知
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/alarm", method = RequestMethod.GET)
	@RequiresPermissions("/shop/sms/alarm")
	public String alarmIndex(Model model) {
		model.addAttribute("currentPage", "alarm");
		model.addAttribute("currentHeader", "sms");
		//查询当前用户的绑定信息
		UserBinding ub = userBindingService.getUserBindingByUserId(TokenManager.getUserId());
		model.addAttribute("bindingPhone", ub.getPhone());
		
		//查询用户的告警信息
		SmsSetting ss = settingService.querySmsInfo(TokenManager.getUserId());
		getSession().setAttribute("sms-setting-" + TokenManager.getUserId(), ss);
		model.addAttribute("ss", ss);
		
		return Web.FOLDER_CLOUD_SHOP + "sms.alarm";
	}
	
	/**
	 * 短信告警通知
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/alarm", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/sms/alarm")
	public ExecuteResult<String> alarm() {
		String alarmSwitch = getRequest().getParameter("switch");
		String alarmMin = getRequest().getParameter("alarmMin");
		SmsSetting ss = (SmsSetting) getSession().getAttribute("sms-setting-" + TokenManager.getUserId());
		if ("on".equals(alarmSwitch)) {
			if (ss.getAlarmMin()==Integer.parseInt(alarmMin) && ss.isAlarmSwitch()) {
				return new ExecuteResult<String>("短信告警通知已开启");
			}
			return settingService.openAlarm(TokenManager.getUserId(), Integer.parseInt(alarmMin));
		}
		if (!ss.isAlarmSwitch()) {
			return new ExecuteResult<String>("短信告警通知已关闭");
		}
		return settingService.cancleAlarm(TokenManager.getUserId(), Integer.parseInt(alarmMin));
	}
}

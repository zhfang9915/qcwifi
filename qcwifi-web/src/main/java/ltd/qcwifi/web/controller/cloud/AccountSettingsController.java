package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.service.impl.DeliveryInfoServiceImpl;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;

/**
 * 个人中心
 * @author Administrator
 *
 */
@Controller
public class AccountSettingsController extends AbstractPersonalCenterController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 配送信息服务
	 */
	@Autowired
	DeliveryInfoServiceImpl deliveryService;
	
//	/**
//	 * 用户信息服务
//	 */
//	@Autowired
//	UserService userService;

	/**
	 * 账户设置视图
	 * @return
	 */
	@RequestMapping(value = "/account/settings", method = RequestMethod.GET)
	public String accountSettings(Model model) {
		model.addAttribute("currentPage", "accountSetting");
		return Web.PERSONAL_CENTER + "accountSetting";
	}
	
	/**
	 * 更新昵称
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "/account/nickname", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateNickName(String nickname) {
		//获取当前用户信息
		User user = TokenManager.getToken();
		//更新用户昵称
		user.setNickname(nickname);
		ExecuteResult<String> r = userService.updateUserNickAndLock(user);
		if (r.isSuccess()) {
			//更新session中的信息
			getSession().setAttribute(Web.LOGIN_USER, user);
		}
		return r;
	}
	
	/**
	 * 查询名下所有的配送信息
	 * @param delivery
	 * @return
	 */
	@RequestMapping(value = "/account/delivery", method = RequestMethod.GET,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<List<DeliveryInfo>> queryDelivery() {
		//获取登录信息
		Long userId = TokenManager.getUserId();
		logger.info("查询全量配送地址,uerId--> {}", userId);
		ExecuteResult<List<DeliveryInfo>> r = deliveryService.selectByUserId(userId);
		
		return r;
	}
	
	/**
	 * 创建配送信息
	 * @param delivery
	 * @return
	 */
	@RequestMapping(value = "/account/delivery", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<DeliveryInfo> addDelivery(DeliveryInfo delivery) {
		delivery.setUserId(TokenManager.getUserId());
		//根据表单是否有id来判断是新增地址还是更新地址
		ExecuteResult<String> result = null;
		if (delivery.getId() == null) {
			//先判断配送地址总数 是否超过10个
			int hasCount = deliveryService.hasCount(TokenManager.getUserId());
			if (hasCount >= 10) {
				return new ExecuteResult<>(false, "您已创建10个地址，最多可创建10个");
			}
			result = deliveryService.create(delivery);
			if (result.isSuccess()) {
				delivery.setId(Long.valueOf(result.getData()));
				return new ExecuteResult<DeliveryInfo>(delivery);
			}
		}else{
			result = deliveryService.update(delivery);
			if (result.isSuccess()) {
				return new ExecuteResult<DeliveryInfo>(delivery);
			}
		}
		return new ExecuteResult<>(false, result.getMsg());
	}
	
	/**
	 * 删除配送信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/account/delivery/{id}", method = RequestMethod.DELETE,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> deleteDelivery(@PathVariable("id")Long id) {
		
		return deliveryService.deleteDelivery(id, TokenManager.getUserId());
	}
	
	/**
	 * 删除配送信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/account/delivery/default/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> setDefault(@PathVariable("id")Long id) {
		return deliveryService.setDefault(id, TokenManager.getUserId());
	}
}

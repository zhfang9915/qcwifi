package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.entity.cloud.platform.UserBinding;

/**
 * 用户安全设备绑定服务
 * @author 张芳
 *
 */
public interface UserBindingService {

	/**
	 * 获取用户的设备绑定信息
	 * @param userId
	 * @return
	 */
	UserBinding getUserBindingByUserId(Long userId);
	
	/**
	 * 获取明文的用户设备信息
	 * @param userId
	 * @return
	 */
	UserBinding getUserBindingByUserIdclear(Long userId);
	
	/**
	 * 是否已绑定的手机号
	 * @param phone
	 * @return
	 */
	boolean isBindingPhone(String phone);
	
	/**
	 * 绑定手机号
	 * @param userId
	 * @param phone
	 * @return
	 */
	boolean bindingPhone(Long userId, String phone);
	
	/**
	 * 是否已绑定的邮箱
	 * @param phone
	 * @return
	 */
	boolean isBindingEmail(String email);
	
	/**
	 * 绑定邮箱
	 * @param userId
	 * @param email
	 * @return
	 */
	boolean bindingEmail(Long userId, String email);
}

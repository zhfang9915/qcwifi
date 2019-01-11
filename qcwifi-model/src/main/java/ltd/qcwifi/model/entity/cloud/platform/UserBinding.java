package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户绑定设备信息
 * @author 张芳
 *
 */
public class UserBinding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875140850154993932L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 绑定的手机号
	 */
	private String phone;
	/**
	 * 手机号绑定时间
	 */
	private Date phoneBindingTime;
	/**
	 * 绑定邮箱
	 */
	private String email;
	/**
	 * 邮箱绑定时间
	 */
	private Date emailBindingTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getPhoneBindingTime() {
		return phoneBindingTime;
	}

	public void setPhoneBindingTime(Date phoneBindingTime) {
		this.phoneBindingTime = phoneBindingTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEmailBindingTime() {
		return emailBindingTime;
	}

	public void setEmailBindingTime(Date emailBindingTime) {
		this.emailBindingTime = emailBindingTime;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
	
}

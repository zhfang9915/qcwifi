package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 配送信息
 * 
 * @author Administrator
 *
 */
public class DeliveryInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5374488043256111044L;

	/**
	 * 地址ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 收货人
	 */
	private String receiver;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 配送地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postCode;
	/**
	 * 联系手机
	 */
	private String phoneNum;
	/**
	 * 是否是默认地址 0非默认 1默认
	 */
	private boolean isDefault;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

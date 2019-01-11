package ltd.qcwifi.model.entity.cloud.platform;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SubAccount {

	private Long id;

	/**
	 * 分配地用户ID
	 */
	private Long userId;
	/**
	 * 连锁管理名称
	 */
	private String nickname;
	/**
	 * 连锁管理账号
	 */
	private String username;
	/**
	 * 联系人
	 */
	private String name;
	/**
	 * 联系手机
	 */
	private String phone;
	/**
	 * 账户是否锁定
	 */
	private Boolean locked = Boolean.FALSE;
	/**
	 * 商铺数量
	 */
	private int shopCount;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 密码加密盐值
	 */
	private String salt;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String createTime;
	/**
	 * 创建人
	 */
	private Long createBy;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public int getShopCount() {
		return shopCount;
	}

	public void setShopCount(int shopCount) {
		this.shopCount = shopCount;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

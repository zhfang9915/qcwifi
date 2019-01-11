package ltd.qcwifi.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.sf.json.JSONObject;

/**
 * 用户登录信息
 * 
 * @author 张芳
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3437586434551132790L;

	// 0:禁止登录
	public static final Long _0 = new Long(0);
	// 1:有效
	public static final Long _1 = new Long(1);

	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 密码加密盐值
	 */
	private String salt;
	/**
	 * 账户类型
	 * 0为一级用户  1为连锁用户(二级用户)
	 */
	private int type;
	/**
	 * 账户是否锁定
	 */
	private Boolean locked = Boolean.FALSE;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

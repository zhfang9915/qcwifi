package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

/**
 * 用户列表查询参数
 * 
 * @author 张芳
 *
 */
public class UserParam extends BaseTableParam {

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 账号状态
	 */
	private String locked;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

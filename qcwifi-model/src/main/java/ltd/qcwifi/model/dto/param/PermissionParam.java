package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

/**
 * 权限列表查询参数
 * 
 * @author 张芳
 *
 */
public class PermissionParam extends BaseTableParam {

	/**
	 * 权限
	 */
	private String permission;
	/**
	 * 权限描述
	 */
	private String description;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

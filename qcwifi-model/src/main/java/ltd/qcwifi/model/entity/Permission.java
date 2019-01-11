package ltd.qcwifi.model.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 权限
 * 
 * @author 张芳
 *
 */
public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778807287287998392L;
	
	private Long id;
	/**
	 * 权限标识 程序中判断使用
	 */
	private String permission;
	/**
	 * 权限描述,UI界面显示使用
	 */
	private String description;
	/**
	 * 是否可用,如果不可用将不会添加给用户
	 */
	private Boolean available = Boolean.FALSE;

	public Permission() {
	}

	public Permission(Long id) {
		super();
		this.id = id;
	}

	public Permission(String permission, String description, Boolean available) {
		this.permission = permission;
		this.description = description;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Permission role = (Permission) o;

		if (id != null ? !id.equals(role.id) : role.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

package ltd.qcwifi.model.entity;

import java.io.Serializable;

/**
 * 角色
 * 
 * @author 张芳
 *
 */
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8068064128362780734L;

	private Long id;
	/**
	 * 
	 */
	private String role;
	/**
	 * 角色描述,UI界面显示使用
	 */
	private String description;
	/**
	 * 是否可用,如果不可用将不会添加给用户
	 */
	private Boolean available = Boolean.FALSE;

	public Role() {
	}

	public Role(Long id) {
		super();
		this.id = id;
	}

	public Role(String role, String description, Boolean available) {
		this.role = role;
		this.description = description;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

		Role role = (Role) o;

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
		return "Role{" + "id=" + id + ", role='" + role + '\'' + ", description='" + description + '\'' + ", available="
				+ available + '}';
	}
}

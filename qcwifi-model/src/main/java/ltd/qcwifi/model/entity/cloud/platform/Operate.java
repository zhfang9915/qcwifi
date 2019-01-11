package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 经营分类信息
 * 
 * @author Administrator
 *
 */
public class Operate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4769424815033915906L;

	/**
	 * 分类编码
	 */
	private int id;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 父类编码
	 */
	private int parent;
	/**
	 * 级别
	 */
	private int level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

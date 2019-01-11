package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;

import ltd.qcwifi.model.BaseEntity;
import net.sf.json.JSONObject;

/**
 * backend_extension_template 推送模版实体类
 * 
 * @author Administrator
 *
 */
public class ExtensionTemplate extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -214937805991699421L;

	/**
	 * 主键
	 */
	private int id;
	/**
	 * 模版名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private Boolean available = Boolean.FALSE;
	/**
	 * 是否多图
	 */
	private Boolean isMore = Boolean.FALSE;
	/**
	 * 模版主体
	 */
	private String temp;
	/**
	 * 模版资源显示部分，多图模版时循环显示此部分
	 */
	private String tempFor;
	/**
	 * 模版JS，多个以换行隔开
	 */
	private String tempJs;

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

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Boolean getIsMore() {
		return isMore;
	}

	public void setIsMore(Boolean isMore) {
		this.isMore = isMore;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTempFor() {
		return tempFor;
	}

	public void setTempFor(String tempFor) {
		this.tempFor = tempFor;
	}

	public String getTempJs() {
		return tempJs;
	}

	public void setTempJs(String tempJs) {
		this.tempJs = tempJs;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

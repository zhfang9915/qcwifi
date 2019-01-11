package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

/**
 * 插件实体类
 * 
 * @author 张芳
 *
 */
public class PluginParam extends BaseTableParam {

	/**
	 * 版本
	 */
	private String version;
	/**
	 * 交叉编译版本
	 */
	private String crosstool;
	/**
	 * 状态
	 */
	private String available;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCrosstool() {
		return crosstool;
	}

	public void setCrosstool(String crosstool) {
		this.crosstool = crosstool;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

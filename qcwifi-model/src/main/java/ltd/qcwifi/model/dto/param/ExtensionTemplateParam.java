package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

/**
 * 推送模版搜索
 * 
 * @author 张芳
 *
 */
public class ExtensionTemplateParam extends BaseTableParam {

	/**
	 * 模版名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private String available;
	/**
	 * 是否多图
	 */
	private String isMore;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getIsMore() {
		return isMore;
	}

	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

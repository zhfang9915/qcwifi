package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

/**
 *定时任务列表查询参数
 * 
 * @author 张芳
 *
 */
public class JobParam extends BaseTableParam {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private String currentStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}

package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

public class ApiLogParam extends BaseTableParam {

	private String invokeApi;

	private String startTime;

	private String endTime;

	public String getInvokeApi() {
		return invokeApi;
	}

	public void setInvokeApi(String invokeApi) {
		this.invokeApi = invokeApi;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

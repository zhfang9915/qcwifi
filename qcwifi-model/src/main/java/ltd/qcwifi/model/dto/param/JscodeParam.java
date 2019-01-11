package ltd.qcwifi.model.dto.param;

import net.sf.json.JSONObject;

public class JscodeParam extends BaseTableParam {
	
	private String codeId;
	
	private String codeName;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

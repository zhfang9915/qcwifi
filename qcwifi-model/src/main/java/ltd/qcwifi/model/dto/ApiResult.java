package ltd.qcwifi.model.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * 接口公共结果集
 * 
 * @author 张芳
 *
 * @param <T>
 */
public class ApiResult<T> {

	private int code;

	private T data;

	private String token;

	public ApiResult() {
		super();
	}

	public ApiResult(int code) {
		super();
		this.code = code;
	}

	public ApiResult(T data) {
		super();
		this.code = 10000;
		this.data = data;
		this.token = MD5.getMD5(JSONObject.toJSONString(data) + "ltd.qcwifi").toLowerCase();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

package ltd.qcwifi.model.dto;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * layui table 返回数据基础泛型封装对象
 * 
 * @author zhfang
 * 
 */
public class LayTableResult<T> {

	/**
	 * 状态码
	 */
	private int code = 0;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 总记录数
	 */
	private long count;
	/**
	 * 行数据
	 */
	private List<T> data;
	
	

	public LayTableResult() {
		super();
	}

	public LayTableResult(long count, List<T> data) {
		super();
		this.count = count;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

package ltd.qcwifi.model.dto;

import java.io.Serializable;

/**
 * 执行结果封装类
 * 
 * @author 张芳
 *
 */
public class ExecuteResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4735798009613524519L;

	/**
	 * 执行结果状态
	 */
	private boolean success;
	/**
	 * 执行成功返回的结果
	 */
	private T data;
	/**
	 * 错误提示信息
	 */
	private String msg;

	public ExecuteResult() {
		super();
	}

	public ExecuteResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ExecuteResult(T t) {
		super();
		this.success = true;
		this.data = t;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

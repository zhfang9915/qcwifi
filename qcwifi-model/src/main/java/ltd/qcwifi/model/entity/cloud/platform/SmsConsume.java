package ltd.qcwifi.model.entity.cloud.platform;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信重置记录
 * 
 * @author 张芳
 *
 */
public class SmsConsume {

	private long id;
	/**
	 * 用户ID
	 */
	private long userId;
	/**
	 * 短信内容
	 */
	private String context;
	/**
	 * 发送日期
	 */
	private Date sendTime;

	public SmsConsume() {
		super();
	}

	public SmsConsume(long userId, String context, Date sendTime) {
		super();
		this.userId = userId;
		this.context = context;
		this.sendTime = sendTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

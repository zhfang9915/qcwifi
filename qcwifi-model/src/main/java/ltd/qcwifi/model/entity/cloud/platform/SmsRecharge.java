package ltd.qcwifi.model.entity.cloud.platform;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信重置记录
 * 
 * @author 张芳
 *
 */
public class SmsRecharge {

	private long id;
	/**
	 * 用户ID
	 */
	private long userId;
	/**
	 * 短信数量
	 */
	private int smsId;
	/**
	 * 充值日期
	 */
	private Date buyTime;

	public SmsRecharge() {
		super();
	}

	public SmsRecharge(long userId, int smsId, Date buyTime) {
		super();
		this.userId = userId;
		this.smsId = smsId;
		this.buyTime = buyTime;
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

	public int getSmsId() {
		return smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信包
 * 
 * @author 张芳
 *
 */
public class SmsSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2902285480605836787L;
	/**
	 * 用户ID
	 */
	private long userId;
	/**
	 * 短信数量
	 */
	private int count;
	/**
	 * 最小余量 是发送告警信息
	 */
	private int alarmMin;
	/**
	 * 告警开关，是否开启
	 */
	private boolean alarmSwitch;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getAlarmMin() {
		return alarmMin;
	}

	public void setAlarmMin(int alarmMin) {
		this.alarmMin = alarmMin;
	}

	public boolean isAlarmSwitch() {
		return alarmSwitch;
	}

	public void setAlarmSwitch(boolean alarmSwitch) {
		this.alarmSwitch = alarmSwitch;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

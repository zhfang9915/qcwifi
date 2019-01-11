package ltd.qcwifi.model.entity.jobs;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 定时任务实体类
 */
public class Jobs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4485152390441959374L;

	private String id;// 主键
	private String name;// 任务名称
	private String schedualTime;// 间隔时间
	private String currentStatus;// 任务状态1：开启，0：停止
	private String className;// 任务类全路径
	private String jobDesc;// 任务描述

	public Jobs() {
		super();
	}

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

	public String getSchedualTime() {
		return schedualTime;
	}

	public void setSchedualTime(String schedualTime) {
		this.schedualTime = schedualTime;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

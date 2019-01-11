package ltd.qcwifi.jobs.model;

/**
 * 定时任务实体类
 */
public class Jobs {
	
	private String fId;//主键
	private String fName;//任务名称
	private String fSchedualTime;//间隔时间
	private String fCurrentStatus;//任务状态1：开启，0：停止
	private String fClassName;//任务类全路径
	private String fJobDesc;//任务描述
	public Jobs() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Jobs(String fId, String fName, String fSchedualTime,
			String fCurrentStatus, String fClassName, String fJobDesc) {
		super();
		this.fId = fId;
		this.fName = fName;
		this.fSchedualTime = fSchedualTime;
		this.fCurrentStatus = fCurrentStatus;
		this.fClassName = fClassName;
		this.fJobDesc = fJobDesc;
	}
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getfSchedualTime() {
		return fSchedualTime;
	}
	public void setfSchedualTime(String fSchedualTime) {
		this.fSchedualTime = fSchedualTime;
	}
	public String getfCurrentStatus() {
		return fCurrentStatus;
	}
	public void setfCurrentStatus(String fCurrentStatus) {
		this.fCurrentStatus = fCurrentStatus;
	}
	public String getfClassName() {
		return fClassName;
	}
	public void setfClassName(String fClassName) {
		this.fClassName = fClassName;
	}
	public String getfJobDesc() {
		return fJobDesc;
	}
	public void setfJobDesc(String fJobDesc) {
		this.fJobDesc = fJobDesc;
	}
	
	
}

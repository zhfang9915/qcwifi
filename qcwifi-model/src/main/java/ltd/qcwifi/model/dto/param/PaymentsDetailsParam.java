package ltd.qcwifi.model.dto.param;

public class PaymentsDetailsParam {
	private int page;
	private int offset;
	private int limit;
	
	private Long userId;
	private Long id;
	
	private String startTime;
	private String endTime;
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long setId() {
		return id;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
}

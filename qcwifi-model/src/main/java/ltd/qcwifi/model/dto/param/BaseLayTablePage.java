package ltd.qcwifi.model.dto.param;

public class BaseLayTablePage {

	/**
	 * 当前页
	 */
	private int pageNum = 1;
	/**
	 * 每页记录数
	 */
	private int pageSize = 15;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}

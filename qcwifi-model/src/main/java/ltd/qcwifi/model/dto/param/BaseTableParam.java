package ltd.qcwifi.model.dto.param;

/**
 * 分页
 * 
 * @author 张芳
 *
 */
public class BaseTableParam {

	/**
	 * 每页数量
	 */
//	private String limit;
	/**
	 * 页码
	 */
//	private String offset;
	/**
	 * 页码
	 */
	private Integer pageNumber;
	/**
	 * 每页大小
	 */
	private Integer pageSize;

//	public String getLimit() {
//		return limit;
//	}

//	public void setLimit(String limit) {
//		this.limit = limit;
//	}

//	public String getOffset() {
//		return offset;
//	}

//	public void setOffset(String offset) {
//		this.offset = offset;
//	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

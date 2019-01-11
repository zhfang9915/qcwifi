package ltd.qcwifi.model.dto;

import java.util.List;

/**
 * Bootstrap table 返回数据基础泛型封装对象
 * 
 * @author zhfang
 * 
 */
public class TableResult<T> {

	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 总页数
	 */
	private int pages;
	/**
	 * 总行数
	 */
	private long total;
	/**
	 * 行数据
	 */
	private List<T> rows;

	public TableResult(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public TableResult(int page, int pages, long total, List<T> rows) {
		super();
		this.page = page;
		this.pages = pages;
		this.total = total;
		this.rows = rows;
	}

	public TableResult() {
		super();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

}

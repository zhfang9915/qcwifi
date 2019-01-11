package ltd.qcwifi.web.file.upload;

import java.io.Serializable;

/**
 * 进度条信息
 * 
 * @author 张芳
 *
 */
public class Progress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7120591206731288565L;
	/**
	 * 已读字节
	 */
	private long pBytesRead = 0L;
	/**
	 * 总字节
	 */
	private long pContentLength = 0L;
	/**
	 * 目前正在读取第几个文件
	 */
	private int pItems;

	public long getpBytesRead() {
		return pBytesRead;
	}

	public void setpBytesRead(long pBytesRead) {
		this.pBytesRead = pBytesRead;
	}

	public long getpContentLength() {
		return pContentLength;
	}

	public void setpContentLength(long pContentLength) {
		this.pContentLength = pContentLength;
	}

	public int getpItems() {
		return pItems;
	}

	public void setpItems(int pItems) {
		this.pItems = pItems;
	}

	@Override
	public String toString() {
		return "ProgressEntity [pBytesRead=" + pBytesRead + ", pContentLength=" + pContentLength + ", pItems=" + pItems
				+ "]";
	}
}

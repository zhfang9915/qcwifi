/**
 * created at 2016年9月29日
 * created by zhfang
 */
package ltd.qcwifi.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * remark:
 * 
 * @author zhfang
 */
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8808656946343814169L;

	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}

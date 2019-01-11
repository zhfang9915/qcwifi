package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 广告主实体类
 * 
 * @author Administrator
 *
 */
public class PushContentGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2167960117655908101L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 广告主名称
	 */
	private String name;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 广告内容
	 */
	private List<PushContent> contents;

	public PushContentGroup(String name, Long createBy) {
		super();
		this.name = name;
		this.createBy = createBy;
	}

	public PushContentGroup() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<PushContent> getContents() {
		return contents;
	}

	public void setContents(List<PushContent> contents) {
		this.contents = contents;
	}

}

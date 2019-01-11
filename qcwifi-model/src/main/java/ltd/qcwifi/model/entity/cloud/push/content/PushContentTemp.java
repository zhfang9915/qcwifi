package ltd.qcwifi.model.entity.cloud.push.content;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 广告模版
 * 
 * @ClassName: PushContentTemp
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 下午8:21:36
 */
public class PushContentTemp {

	/**
	 * 模版ID
	 */
	private String id;
	/**
	 * 模版名称
	 */
	private String name;
	/**
	 * 状态 Y启用 N禁用
	 */
	private String state;
	/**
	 * 广告总数 N单个 Y多个
	 */
	private String isMore;
	/**
	 * 模版
	 */
	private String temp;
	/**
	 * 循环体
	 */
	private String tempFor;

	private String tempJs;

	private Long createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsMore() {
		return isMore;
	}

	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTempFor() {
		return tempFor;
	}

	public void setTempFor(String tempFor) {
		this.tempFor = tempFor;
	}

	public String getTempJs() {
		return tempJs;
	}

	public void setTempJs(String tempJs) {
		this.tempJs = tempJs;
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

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

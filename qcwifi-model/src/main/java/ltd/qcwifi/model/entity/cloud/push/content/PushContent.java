package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 推广内容实体类
 * 
 * @author Administrator
 *
 */
public class PushContent implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 6897026813089000847L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 广告主ID
	 */
	private Long groupId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 日限额，值为空时表示不限制
	 */
	private Integer dayLimit;
	/**
	 * 总限额，值为空时表示不限制
	 */
	private Integer totalLimit;
	/**
	 * 状态
	 */
	private boolean status;
	/**
	 * 权重 默认1
	 */
	private int weight = 1;
	/**
	 * 所属者
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 投放选择
	 */
	private PushContentChoose choose;
	/**
	 * 定向设置
	 */
	private PushContentSet set;
	
	private PushContentGroup group;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public PushContentChoose getChoose() {
		return choose;
	}

	public void setChoose(PushContentChoose choose) {
		this.choose = choose;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public PushContentSet getSet() {
		return set;
	}

	public void setSet(PushContentSet set) {
		this.set = set;
	}

	public PushContentGroup getGroup() {
		return group;
	}

	public void setGroup(PushContentGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

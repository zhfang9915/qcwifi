package ltd.qcwifi.model.entity.cloud.platform;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信包
 * 
 * @author 张芳
 *
 */
public class SmsPacekage {

	/**
	 * 短信包ID
	 */
	private long id;
	/**
	 * 短信包名称
	 */
	private String name;
	/**
	 * 短信包价格
	 */
	private double price;
	/**
	 * 短信数量
	 */
	private int count;
	/**
	 * 创建人
	 */
	private long createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
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

package ltd.qcwifi.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.sf.json.JSONObject;

/*
 * 优惠券
 */
public class Coupon implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4991053128192073124L;
	
	private long id;
	/*
	 *	优惠券类型：1-抵用券，2-优惠券
	 */
	private int type;
	/*
	 *	金额
	 */
	private double price;
	/*
	 *	状态：0-已使用，1-未使用
	 */
	private int status;
	/*
	 *	有效期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;
	/*
	 *	券类别：如购机券、续费券等
	 */
	private int group;
	/*
	 *	备注说明
	 */
	private String remark;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

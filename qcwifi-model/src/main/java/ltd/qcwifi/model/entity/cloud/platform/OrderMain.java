package ltd.qcwifi.model.entity.cloud.platform;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单住图
 * 
 * @author 张芳
 *
 */
public class OrderMain {

	/**
	 * 订单号
	 */
	private long orderNo;
	/**
	 * 订单标题
	 */
	private String orderTitle;
	/**
	 * 订单金额
	 */
	private double amount;
	/**
	 * 订单状态 101 待支付 102 已支付 103 待发货 104 已发货 105 已完成 110 已取消
	 */
	private int status;
	/**
	 * 订单所有人
	 */
	private long createBy;
	/**
	 * 下单时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private List<OrderDetail> details;

	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public OrderMain() {
		super();
	}

	public OrderMain(long orderNo, String orderTitle, double amount, int status, long createBy) {
		super();
		this.orderNo = orderNo;
		this.orderTitle = orderTitle;
		this.amount = amount;
		this.status = status;
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

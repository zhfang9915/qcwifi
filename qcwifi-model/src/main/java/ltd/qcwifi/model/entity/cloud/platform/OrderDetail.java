package ltd.qcwifi.model.entity.cloud.platform;

import com.alibaba.fastjson.JSONObject;

/**
 * 订单详情
 * 
 * @author 张芳
 *
 */
public class OrderDetail {

	/**
	 * 主键
	 */
	private long id;
	/**
	 * 订单号
	 */
	private long orderNo;
	/**
	 * 商品ID
	 */
	private long offerId;
	/**
	 * 数量
	 */
	private int qty;

	private Offer offer;

	public OrderDetail(long orderNo, long offerId, int qty) {
		super();
		this.orderNo = orderNo;
		this.offerId = offerId;
		this.qty = qty;
	}

	public OrderDetail() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

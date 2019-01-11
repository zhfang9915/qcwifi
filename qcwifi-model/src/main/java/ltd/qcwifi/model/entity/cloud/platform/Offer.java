package ltd.qcwifi.model.entity.cloud.platform;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品信息
 * 
 * @author 张芳
 *
 */
public class Offer {

	/**
	 * 商品ID
	 */
	private long offerId;
	/**
	 * 商品名称
	 */
	private String offerName;
	/**
	 * 商品类型
	 */
	private int offerType;
	/**
	 * 单价
	 */
	private double price;
	/**
	 * 短信总量
	 */
	private int smsCount;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 创建人
	 */
	private long createBy;

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public int getOfferType() {
		return offerType;
	}

	public void setOfferType(int offerType) {
		this.offerType = offerType;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

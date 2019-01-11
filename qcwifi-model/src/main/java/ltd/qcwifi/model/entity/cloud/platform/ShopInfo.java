package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 商铺信息
 * 
 * @author Administrator
 *
 */
public class ShopInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022024532092981495L;

	/**
	 * 主键
	 */
	private long shopId;
	/**
	 * 绑定的设备号
	 */
	private String devNo;
	/**
	 * 商铺名
	 */
	private String shopName;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 县、区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 一级经营类目
	 */
	private String primaryCategory;
	/**
	 * 二级经营类目
	 */
	private String secondaryCategory;
	/**
	 * 联系手机号
	 */
	private String phone;
	/**
	 * 标签
	 */
	private String mark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 所属用户
	 */
	private long createBy;

	/**
	 * 经度
	 */
	private Double lng;
	/**
	 * 纬度
	 */
	private Double lat;
	/**
	 * 备注
	 */
	private String remark;

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrimaryCategory() {
		return primaryCategory;
	}

	public void setPrimaryCategory(String primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public String getSecondaryCategory() {
		return secondaryCategory;
	}

	public void setSecondaryCategory(String secondaryCategory) {
		this.secondaryCategory = secondaryCategory;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
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

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

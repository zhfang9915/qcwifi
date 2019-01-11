package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 路由器绑定商铺
 * 
 * @ClassName: ShopRouter
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月28日 下午8:45:50
 */
public class ShopRouter implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 7770784637185046238L;

	/**
	 * 路由器编码
	 */
	private String devNo;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 设备版本
	 */
	private String version;
	/**
	 * 设备型号
	 */
	private String model;
	/**
	 * 固件版本
	 */
	private String fwv;
	/**
	 * 交叉编译版本
	 */
	private String gccv;
	/**
	 * 固件ID
	 */
	private String fwid;
	/**
	 * 插件版本
	 */
	private String pv;
	/**
	 * 商铺ID
	 */
	private Long shopId;
	/**
	 * 绑定日期
	 */
	private Date bindingTime;
	/**
	 * 上线日期
	 */
	private Date onlineTime;
	/**
	 * 离线日期
	 */
	private Date offlineTime;
	/**
	 * 所属账号
	 */
	private Long createBy;
	/**
	 * 2g wifi ssid
	 */
	private String ssid2g;
	/**
	 * 2g wifi 免授权 ssid
	 */
	private String ssid2gb;
	/**
	 * 5g wifi ssid
	 */
	private String ssid5g;
	/**
	 * 5g wifi 免授权 ssid
	 */
	private String ssid5gb;

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFwv() {
		return fwv;
	}

	public void setFwv(String fwv) {
		this.fwv = fwv;
	}

	public String getGccv() {
		return gccv;
	}

	public void setGccv(String gccv) {
		this.gccv = gccv;
	}

	public String getFwid() {
		return fwid;
	}

	public void setFwid(String fwid) {
		this.fwid = fwid;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getSsid2g() {
		return ssid2g;
	}

	public void setSsid2g(String ssid2g) {
		this.ssid2g = ssid2g;
	}

	public String getSsid2gb() {
		return ssid2gb;
	}

	public void setSsid2gb(String ssid2gb) {
		this.ssid2gb = ssid2gb;
	}

	public String getSsid5g() {
		return ssid5g;
	}

	public void setSsid5g(String ssid5g) {
		this.ssid5g = ssid5g;
	}

	public String getSsid5gb() {
		return ssid5gb;
	}

	public void setSsid5gb(String ssid5gb) {
		this.ssid5gb = ssid5gb;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

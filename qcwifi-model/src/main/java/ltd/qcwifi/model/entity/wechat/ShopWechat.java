package ltd.qcwifi.model.entity.wechat;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 *  商铺关联的微信门店（一个商铺只会关联一个门店，这是一对一的关系）
 * @author Xu
 */
public class ShopWechat implements Serializable {

	private static final long serialVersionUID = 4685173936793353478L;

	/**
	 *  公众号门店id
	 */
	private Long wechatShopId;
	
	/**
	 *  公众号门店名称
	 */
	private String wechatShopName;
	
	/**
	 *  公众号appId
	 */
	private String wechatAppId;
	
	/**
	 *  公众号名称
	 */
	private String wechatAppName;
	
	/**
	 *  商铺id
	 */
	private Long shopId;
	
	/**
	 *  WiFi名称
	 */
	private String ssid;
	
	/**
	 *  改造portal页面所需参数（连WiFi的时候要用到）
	 */
	private String wechatShopSecretkey;

	public Long getWechatShopId() {
		return wechatShopId;
	}

	public void setWechatShopId(Long wechatShopId) {
		this.wechatShopId = wechatShopId;
	}

	public String getWechatShopName() {
		return wechatShopName;
	}

	public void setWechatShopName(String wechatShopName) {
		this.wechatShopName = wechatShopName;
	}

	public String getWechatAppId() {
		return wechatAppId;
	}

	public void setWechatAppId(String wechatAppId) {
		this.wechatAppId = wechatAppId;
	}

	public String getWechatAppName() {
		return wechatAppName;
	}

	public void setWechatAppName(String wechatAppName) {
		this.wechatAppName = wechatAppName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getWechatShopSecretkey() {
		return wechatShopSecretkey;
	}

	public void setWechatShopSecretkey(String wechatShopSecretkey) {
		this.wechatShopSecretkey = wechatShopSecretkey;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

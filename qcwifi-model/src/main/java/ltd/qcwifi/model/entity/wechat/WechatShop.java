package ltd.qcwifi.model.entity.wechat;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

/**
 *  微信门店
 * @author Xu
 */
public class WechatShop implements Serializable {

	private static final long serialVersionUID = 7159639090245696771L;

	/**
	 *  门店id
	 */
	private Long shopId;
	
	/**
	 *  门店名称
	 */
	private String shopName;
	
	/**
	 * 无线网络设备的ssid，未添加设备为空，多个ssid时显示第一个
	 */
	private String ssid;
	
	/**
	 * 门店内设备的设备类型，0-未添加设备，1-专业型设备，4-密码型设备，5-portal自助型设备，31-portal改造型设备
	 */
	private Integer protocolType;
	
	/**
	 * 商户自己的id，与门店poi_id对应关系，建议在添加门店时候建立关联关系，具体请参考“微信门店接口”
	 */
	private Integer sid;
	
	/**
	 * 门店ID（适用于微信卡券、微信门店业务），具体定义参考微信门店，与shop_id一一对应
	 */
	private Integer poiId;
	
	/**
	 * 无线网络设备的ssid列表，返回数组格式
	 */
	private List<String> ssidList;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPoiId() {
		return poiId;
	}

	public void setPoiId(Integer poiId) {
		this.poiId = poiId;
	}

	public List<String> getSsidList() {
		return ssidList;
	}

	public void setSsidList(List<String> ssidList) {
		this.ssidList = ssidList;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}

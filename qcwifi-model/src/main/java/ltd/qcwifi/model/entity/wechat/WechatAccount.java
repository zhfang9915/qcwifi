package ltd.qcwifi.model.entity.wechat;

import java.io.Serializable;
import net.sf.json.JSONObject;

/**
 * 授权给平台的公众号
 * @author Xu
 *
 */
public class WechatAccount implements Serializable {

	private static final long serialVersionUID = 1506066302601119025L;
	
	/**
	 * 授权方的开发者ID（AppID）
	 */
	private String appId;
	
	/**
	 * 授权方公众号的微信号（每个自然年可以修改一次）
	 */
	private String wechatId;
	
	/**
	 * 授权方的原始ID
	 */
	private String originalId;
	
	/**
	 * 登录的用户ID
	 */
	private Long userId;
	
	/**
	 * 授权方的名称
	 */
	private String nickName;
	
	/**
	 * 授权方的头像链接
	 */
	private String headImgUrl;
	
	/**
	 * 授权方的二维码链接
	 */
	private String qrcodeUrl;

	/**
	 * 授权方的签名
	 */
	private String signature;
	
	/**
	 * 公众号授权给我们的权限集列表，ID为1到15时分别代表： 
	 * 		1.消息管理权限 2.用户管理权限 3.帐号服务权限 4.网页服务权限 5.微信小店权限 
	 * 		6.微信多客服权限 7.群发与通知权限 8.微信卡券权限 9.微信扫一扫权限 10.微信连WIFI权限 
	 * 		11.素材管理权限 12.微信摇周边权限 13.微信门店权限 14.微信支付权限 15.自定义菜单权限 
	 * 请注意：该字段的返回不会考虑公众号是否具备该权限集的权限（因为可能部分具备），请根据公众号的帐号类型和认证情况，来判断公众号的接口权限。
	 */
	private String funcInfo;
	
	/**
	 * 用以了解以下功能的开通状况（0代表未开通，1代表已开通）： 
	 * 		open_store:是否开通微信门店功能
	 * 		open_scan:是否开通微信扫商品功能
	 * 		open_pay:是否开通微信支付功能 
	 * 		open_card:是否开通微信卡券功能
	 * 		open_shake:是否开通微信摇一摇功能
	 */
	private String businessInfo;
	
	/**
	 * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
	 */
	private Integer serviceTypeInfo;
	
	/**
	 * 授权方认证类型：
	 * 		-1代表未认证，
	 * 		0代表微信认证，
	 * 		1代表新浪微博认证，
	 *		2代表腾讯微博认证，
	 *		3代表已资质认证通过但还未通过名称认证，
	 *		4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，
	 *		5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
	 */
	private Integer verifyTypeInfo;
	
	/**
	 * 关联的商铺数量
	 */
	private Integer shopCount;
	
	/**
	 * 接口访问令牌
	 */
	private String accessToken;
	
	/**
	 * 刷新接口访问令牌的令牌
	 */
	private String refreshAccessToken;
	
	/**
	 * 公众号是否已开通微信连WiFi插件，true(1)：已开通，false(0)：未开通。
	 * PS：如果要开通WiFi插件，所需的wifi_token现取现用。
	 */
	private boolean isWifiOpen;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getFuncInfo() {
		return funcInfo;
	}
	public void setFuncInfo(String funcInfo) {
		this.funcInfo = funcInfo;
	}
	public String getBusinessInfo() {
		return businessInfo;
	}
	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}
	public Integer getServiceTypeInfo() {
		return serviceTypeInfo;
	}
	public void setServiceTypeInfo(Integer serviceTypeInfo) {
		this.serviceTypeInfo = serviceTypeInfo;
	}
	public Integer getVerifyTypeInfo() {
		return verifyTypeInfo;
	}
	public void setVerifyTypeInfo(Integer verifyTypeInfo) {
		this.verifyTypeInfo = verifyTypeInfo;
	}
	public Integer getShopCount() {
		return shopCount;
	}
	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshAccessToken() {
		return refreshAccessToken;
	}
	public void setRefreshAccessToken(String refreshAccessToken) {
		this.refreshAccessToken = refreshAccessToken;
	}
	public boolean getIsWifiOpen() {
		return isWifiOpen;
	}
	public void setIsWifiOpen(boolean isWifiOpen) {
		this.isWifiOpen = isWifiOpen;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
			
}

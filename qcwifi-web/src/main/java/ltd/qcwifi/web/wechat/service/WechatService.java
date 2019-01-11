package ltd.qcwifi.web.wechat.service;

import java.util.List;

import ltd.qcwifi.model.entity.wechat.ShopWechat;
import ltd.qcwifi.model.entity.wechat.WechatAccount;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;

/**
 * 操作微信相关的数据
 * @author Xu
 *
 */
public interface WechatService {
	
	/** 开通微信连WiFi插件 */
	public String API_OPEN_WIFI_PLUGIN_URL = "https://api.weixin.qq.com/bizwifi/openplugin/token";
	
	/** 获取门店列表 */
	public String API_GET_SHOP_LIST_URL = "https://api.weixin.qq.com/bizwifi/shop/list";
	
	/** 添加Portal型设备 */
	public String API_CREATE_PORTAL_DEVICE_URL = "https://api.weixin.qq.com/bizwifi/apportal/register";
	
	public WxOpenMessageRouter getWxOpenMessageRouter();
	
	/**
	 * 查询授权过的公众号信息
	 * @param appId 公众号的appId
	 * @return WechatAccount对象
	 */
	public WechatAccount getWechatAccount(String appId);
	
	/**
	 * 查询用户授权过的公众号
	 * @param userId 用户id
	 * @return WechatAccount的List
	 */
	public List<WechatAccount> getWechatAccounts(Long userId);
	
	/**
	 * 新增授权的公众号
	 * @param account 公众号的信息
	 */
	int insertWechatAccount(WechatAccount account);
	
	/**
	 * 更新已授权的公众号
	 * @param account 公众号的信息
	 */
	public int updateWechatAccount(WechatAccount account);
	
	/**
	 * 更新已授权的公众号绑定的商铺数量
	 * @param account 公众号的信息
	 */
	public int updateWechatShopCount(WechatAccount account);
	
	/**
	 * 更新已授权的公众号的WiFi插件是否开通
	 * @param isOpen true(1)：已开通，false(0)：未开通
	 */
	public int updateWechatIsOpenWifiPlugin(String appId, boolean isOpen);
	
	/**
	 * 根据商铺id去查询关联的微信门店
	 * @param shopId 商铺id
	 * @return ShopWechat对象
	 */
	public ShopWechat getWechatShopByShopId(Long shopId);
	
//====================================================================================================================//
	
	/**
	 * 获取接口访问凭证
	 * @param appId 公众号的appId
	 * @return access token
	 */
	public String getAccessTokenByAppId(String appId);
	
	/**
	 * 发POST请求
	 * @param appId
	 * @param uri
	 * @param postDataJsonString
	 * @return 请求返回的字符串
	 */
	public String doPost(String appId, String uri, String postDataJsonString);
	
//====================================================================================================================//
	
}

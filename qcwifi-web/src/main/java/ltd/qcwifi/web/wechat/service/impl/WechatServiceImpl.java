package ltd.qcwifi.web.wechat.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ltd.qcwifi.dao.wechat.WechatDao;
import ltd.qcwifi.model.entity.wechat.ShopWechat;
import ltd.qcwifi.model.entity.wechat.WechatAccount;
import ltd.qcwifi.web.wechat.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import redis.clients.jedis.JedisPool;

/**
 * 微信服务
 * @author Xu
 *
 */
@Service("wxService")
public class WechatServiceImpl extends WxOpenServiceImpl implements WechatService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${wechat.componetAppId}")
	private String componetAppId;

	@Value("${wechat.componetAppSecret}")
	private String componetAppSecret;
	
	@Value("${wechat.componetToken}")
	private String componetToken;
	
	@Value("${wechat.componetAesKey}")
	private String componetAesKey;
	
	private WxOpenMessageRouter wxOpenMessageRouter;
	
	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	WechatDao wxDao;

	@PostConstruct
	public void init () {
		WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(this.jedisPool);
		
		inRedisConfigStorage.setComponentAppId(componetAppId);
		inRedisConfigStorage.setComponentAppSecret(componetAppSecret);
		inRedisConfigStorage.setComponentToken(componetToken);
		inRedisConfigStorage.setComponentAesKey(componetAesKey);
		
		setWxOpenConfigStorage(inRedisConfigStorage);
		
		wxOpenMessageRouter = new WxOpenMessageRouter(this);
        wxOpenMessageRouter.rule().handler(new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
                logger.debug("\n接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
                return null;
            }
        }).next();
	}
	
	//====================================================================================================================//
	
	@Override
	public String getAccessTokenByAppId(String appId) {
		String accessToken = "";
		try {
			accessToken = getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	@Override
	public String doPost(String appId, String uri, String postDataJsonString) {
		String accessToken = getAccessTokenByAppId(appId);
		String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;
		logger.info("\n======uriWithAccessToken======={}\n", uriWithAccessToken);
		String responseContent = "";
		try {
			responseContent = post(uriWithAccessToken, postDataJsonString);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		logger.info("\n/////////////////////////////:{}\n", responseContent);
		return responseContent;
	}
	
	//====================================================================================================================//
	
	public String getComponetAppId() {
		return componetAppId;
	}

	public void setComponetAppId(String componetAppId) {
		this.componetAppId = componetAppId;
	}

	public String getComponetAppSecret() {
		return componetAppSecret;
	}

	public void setComponetAppSecret(String componetAppSecret) {
		this.componetAppSecret = componetAppSecret;
	}

	public String getComponetToken() {
		return componetToken;
	}

	public void setComponetToken(String componetToken) {
		this.componetToken = componetToken;
	}

	public String getComponetAesKey() {
		return componetAesKey;
	}

	public void setComponetAesKey(String componetAesKey) {
		this.componetAesKey = componetAesKey;
	}
	
	//====================================================================================================================//
	
	@Override
	public WxOpenMessageRouter getWxOpenMessageRouter() {
		return wxOpenMessageRouter;
	}
	
	@Override
	public WechatAccount getWechatAccount(String appId) {
		return wxDao.selectOneById(appId);
	}
	
	@Override
	public List<WechatAccount> getWechatAccounts(Long userId) {
		return wxDao.selectByUserId(userId);
	}

	@Override
	public int insertWechatAccount(WechatAccount account) {
		return wxDao.insert(account);
	}

	@Override
	public int updateWechatAccount(WechatAccount account) {
		return wxDao.update(account);
	}
	
	@Override
	public ShopWechat getWechatShopByShopId(Long shopId) {
		return wxDao.getWechatShopByShopId(shopId);
	}

	@Override
	public int updateWechatShopCount(WechatAccount account) {
		return wxDao.updateWechatShopCount(account);
	}
	
	@Override
	public int updateWechatIsOpenWifiPlugin(String appId, boolean isOpen) {
		return wxDao.updateWechatIsOpenWifiPlugin(appId, isOpen);
	}
	
}

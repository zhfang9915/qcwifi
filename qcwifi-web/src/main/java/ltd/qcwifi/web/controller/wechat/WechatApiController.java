package ltd.qcwifi.web.controller.wechat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.wechat.ShopWechat;
import ltd.qcwifi.model.entity.wechat.WechatAccount;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Qc;
import ltd.qcwifi.web.controller.base.BaseController;
import ltd.qcwifi.web.wechat.service.WechatService;
import ltd.qcwifi.web.wechat.service.impl.WechatServiceImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;

/**
 * 平台关于微信的相关接口
 * @author Xu
 */
@Controller
@RequestMapping("/wxapi")
public class WechatApiController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    WechatServiceImpl wxService;
    
    @Autowired
	ShopRouterService routerService;
    
    @Autowired
	Qc qc;
    
    @RequestMapping(value = "/auth/goto_auth_url_show", method = RequestMethod.GET)
    @ResponseBody
    public String gotoPreAuthUrlShow() {
    		logger.info("-----gotoPreAuthUrlShow-----");
//		getWechatShopList("wx3fd8d6dc48581862", 1, 10);
    		checkWifiPluginIsOpen("wx3fd8d6dc48581862");
        return "<a href='jump'>click me and jump to wechat authorization page</a>";
    }
    
    /**
	 * 跳转到微信公众号的授权页面（是微信自己的页面）
	 */
    @RequestMapping(value = "/auth/jump", method = RequestMethod.GET)
    public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response) {
    		checkWifiPluginIsOpen("wx65856841e22da98e");
    		// 这个 url 是公众号授权成功后回调的
        String url = qc.getRootPath() + "wxapi/auth/callback";
        try {
            url = wxService.getWxOpenComponentService().getPreAuthUrl(url);
            logger.info("\n-----authPageUrl-----\n", url);
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("\n-----authPageUrl-----\n", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
	 * 微信公众号授权成功并跳转回来，新增或者更新授权信息
	 */
    @RequestMapping(value = "/auth/callback", method = RequestMethod.GET)
    public void callback(@RequestParam("auth_code") String authorizationCode, HttpServletResponse response) {
        try {
            WxOpenQueryAuthResult queryAuthResult = wxService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            logger.info("\n-----getQueryAuth-----\n", queryAuthResult);
            
            String appId = queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
            String accessToken = queryAuthResult.getAuthorizationInfo().getAuthorizerAccessToken();
            String refreshAccessToken = queryAuthResult.getAuthorizationInfo().getAuthorizerRefreshToken();
            
            // 刷新token
            wxService.getWxOpenConfigStorage().setAuthorizerRefreshToken(appId, refreshAccessToken);
            wxService.getWxOpenConfigStorage().updateAuthorizerAccessToken(appId, accessToken, queryAuthResult.getAuthorizationInfo().getExpiresIn());;
            
            // 查询公众号的一些信息
            WxOpenAuthorizerInfoResult authorizerInfoResult = wxService.getWxOpenComponentService().getAuthorizerInfo(appId);
            logger.info("\n-----getAuthorizerInfo-----\n", authorizerInfoResult);
            
            WechatAccount account = wxService.getWechatAccount(appId);
            boolean isUpdate = true;
            if (account == null) {
        			account = new WechatAccount();
        			account.setAppId(appId);
        			isUpdate = false;
            }
            
            // 获取当前用户信息
			User user = TokenManager.getToken();
			account.setUserId(user.getId());
            
            account.setWechatId(authorizerInfoResult.getAuthorizerInfo().getAlias());
            account.setOriginalId(authorizerInfoResult.getAuthorizerInfo().getUserName());
            account.setNickName(authorizerInfoResult.getAuthorizerInfo().getNickName());
            account.setHeadImgUrl(authorizerInfoResult.getAuthorizerInfo().getHeadImg());
            account.setQrcodeUrl(authorizerInfoResult.getAuthorizerInfo().getQrcodeUrl());
            account.setSignature(authorizerInfoResult.getAuthorizerInfo().getSignature());
            account.setAccessToken(accessToken);
            account.setRefreshAccessToken(refreshAccessToken);
            List<Integer> funcInfo = queryAuthResult.getAuthorizationInfo().getFuncInfo();
            account.setFuncInfo(StringUtils.join(funcInfo.toArray(), ","));
            Map<String, Integer> businessInfoMap = authorizerInfoResult.getAuthorizerInfo().getBusinessInfo();
            String businessInfo = JSON.toJSONString(businessInfoMap);
            account.setBusinessInfo(businessInfo);
            account.setServiceTypeInfo(authorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo());
            account.setVerifyTypeInfo(authorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());
            
            // 开通WiFi插件
            String wifiToken = checkWifiPluginIsOpen(appId);
            if (wifiToken != null) {
            		account.setIsWifiOpen(false);
            } else {
            		account.setIsWifiOpen(true);
            }
            
            if (isUpdate) {
        			wxService.updateWechatAccount(account);
            } else {
        			wxService.insertWechatAccount(account);
            }
            
			try {
				String url = qc.getRootPath() + "wxapi/auth/goto_auth_url_show";
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }
    
	/**
	 * 获取微信门店列表
	 * @param appid 公众号的appId
	 */
    @RequestMapping(value = "/shop/list", method = RequestMethod.POST)
	public String getWechatShopList(@RequestParam("appId") String appId) {
    		getWechatShopList(appId, 1, 10);
		return "aaa";
	}
    
    /**
	 * 获取已授权的微信公众号列表
	 */
    public List<WechatAccount> getAuthenticatedWechatList() {
		// 获取当前用户信息
		User user = TokenManager.getToken();
    		return wxService.getWechatAccounts(user.getId());
    }
    
    /**
	 * 检查微信连WiFi插件是否开通。如果返回值为null，则说明已经开通；如果不为null，则说明暂未开通WiFi插件。
	 * @param appId 公众号的appId
	 * @return wifi_token
	 */
    public String checkWifiPluginIsOpen(String appId) {
    		String callbackUrl = qc.getRootPath() + "wxapi/auth/goto_auth_url_show";
    		
    		JsonObject param = new JsonObject();
		param.addProperty("callback_url", callbackUrl);
		
		String responseContent = wxService.doPost(appId, WechatService.API_OPEN_WIFI_PLUGIN_URL, param.toString());
		
		Gson gson = new Gson();
		Map<String,?> map = gson.fromJson(responseContent, new TypeToken<Map<String,?>>() {}.getType());
		double errcode = (double)map.get("errcode");
		if (errcode == 0) {
			@SuppressWarnings("unchecked")
			Map<String,String> data = (Map<String, String>)map.get("data");
			String isOpen = data.get("is_open");
			if (Boolean.valueOf(isOpen)) {
				return null;
			} else {
				return data.get("wifi_token");
			}
		}
		return "error";
    }

    /**
	 * 连 WiFi 第①步：获取所需的参数：appId、shop_id、secretkey。
	 * 路由器需要与商铺对应起来，
	 * @param devNo 设备号
	 * @param ssid WiFi名称
	 */
    @RequestMapping(value = "/wifi/params", method = RequestMethod.POST)
    public String getWifiParams(@RequestParam("devNo") String devNo, @RequestParam("ssid") String ssid) {
    		// ①先获取appId和shopId
    		// 获取设备及其关联的的商铺
		ShopRouter router = routerService.getRouterByNo(devNo);
		if (router == null) {
			logger.debug("设备 {} 不存在", devNo);
		}
		if (router.getShopId() == null) {
			logger.debug("此设备并未被商铺关联起来");
		}
		// ②获取商铺关联的微信门店
		ShopWechat shop = wxService.getWechatShopByShopId(router.getShopId());
		if (shop == null) {
			logger.debug("此商铺并未与微信门店关联");
		}
		// ③再获取secretkey
		String secretkey = shop.getWechatShopSecretkey();
		if (secretkey == null || secretkey == "") {
			secretkey = createWifiDevice(shop.getWechatAppId(), shop.getShopId(), ssid);
		}
		
		JsonObject result = new JsonObject();
		result.addProperty("appId", shop.getWechatAppId());
		result.addProperty("shopId", shop.getShopId());
		result.addProperty("secretkey", secretkey);
		
    		return result.toString();
    }
    
    /**
     * 连 WiFi 第②步：微信连WiFi的authUrl（认证服务端URL，微信客户端将把用户微信身份信息向此URL提交并获得认证放行）。
     * 微信客户端被呼起后，将自动向authUrl（JSAPI的传入参数）发起请求，提交认证所需的用户微信身份信息参数，包括extend、openId、tid。
     * @param extend
     * @param openId 用户的微信openId
     * @param tid 为加密后的用户手机号码（仅作网监部门备案使用）
     */
    @RequestMapping(value = "/wifi/auth", method = RequestMethod.POST)
    public String doWifiAuth(@RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid) {
    		return "";
    }
    
    /**
	 * 分页获取微信门店列表
	 * @param appId 公众号的AppID
	 * @param pageindex 分页下标，默认从1开始
	 * @param pagesize 每页数量，默认10个，最大20个
	 */
	public void getWechatShopList(String appId, int pageindex, int pagesize) {
		
		JsonObject jsonObject = new JsonObject();
	    jsonObject.addProperty("pageindex", pageindex);
	    jsonObject.addProperty("pagesize", pagesize);
	    
	    wxService.doPost(appId, WechatService.API_GET_SHOP_LIST_URL, jsonObject.toString());
	}
	
	/**
	 * 添加Portal型设备
	 * @param appid 公众号id
	 * @param shopId 商铺id
	 * @param ssid WiFi名称
	 * @return secretkey
	 */
	public String createWifiDevice(String appId, Long shopId, String ssid) {
		
		JsonObject param = new JsonObject();
		param.addProperty("shop_id", shopId);
		param.addProperty("ssid", ssid);
		param.addProperty("reset", false);
		
		String responseContent = wxService.doPost(appId, WechatService.API_CREATE_PORTAL_DEVICE_URL, param.toString());	
		
		String secretkey = "";
		
		Gson gson = new Gson();
		Map<String,?> map = gson.fromJson(responseContent, new TypeToken<Map<String,?>>() {}.getType());
		double errcode = (double)map.get("errcode");
		if (errcode == 0) {
			@SuppressWarnings("unchecked")
			Map<String,String> data = (Map<String, String>)map.get("data");
			secretkey = data.get("secretkey");
		}
		return secretkey;
	}
}
//====================================================================================================================//
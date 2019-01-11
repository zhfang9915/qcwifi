package ltd.qcwifi.web.controller.wechat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ltd.qcwifi.web.controller.base.BaseController;
import ltd.qcwifi.web.wechat.service.impl.WechatServiceImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;

/**
 * 接受来自微信服务器的推送消息
 * @author Xu
 *
 */
@Controller
@RequestMapping("/openweixin")
public class WechatNotifyController extends BaseController  {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    protected WechatServiceImpl wxService;
	
	/**
	 * 接收ticket、接收取消授权、授权成功、授权更新等的通知
	 * @return
	 */
	@RequestMapping("/auth/message")
	public Object receiveTicket (@RequestBody(required = false) String requestBody, 
								 @RequestParam("timestamp") String timestamp,
								 @RequestParam("nonce") String nonce,
								 @RequestParam("signature") String signature,
								 @RequestParam(name = "encrypt_type", required = false) String encType,
								 @RequestParam(name = "msg_signature", required = false) String msgSignature) {
		
		this.logger.info("\nTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT\n");
		
		if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
		
		// aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        this.logger.info("\n消息解密后内容为：\n{} ", inMessage.toString());
        try {
            String out = wxService.getWxOpenComponentService().route(inMessage);
            this.logger.info("\n组装回复信息：{}", out);
        } catch (WxErrorException e) {
            this.logger.error("receive_ticket", e);
        }
		
		return "success";
	}
	
	/**
	 * 接收公众号的消息和事件
	 * @return
	 */
	@RequestMapping("/message/{appId}")
	public Object callback (@RequestBody(required = false) String requestBody,
							@PathVariable("appId") String appId,
							@RequestParam("signature") String signature,
							@RequestParam("timestamp") String timestamp,
							@RequestParam("nonce") String nonce,
							@RequestParam("openid") String openid,
							@RequestParam("encrypt_type") String encType,
							@RequestParam("msg_signature") String msgSignature) {
				
		this.logger.info("\nQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ\n");
		
		this.logger.info("\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
		
        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = "success";
        // aes加密的消息
        WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody, wxService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        this.logger.info("\n消息解密后内容为：\n{} ", inMessage.toString());
        // 全网发布测试用例
        if (StringUtils.equalsIgnoreCase(appId, "wxd101a85aa106f53e")) {
            try {
                if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                    if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                        out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                        .fromUser(inMessage.getToUser())
                                        .toUser(inMessage.getFromUser())
                                        .build(),
                                        wxService.getWxOpenConfigStorage()
                        );
                    } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                        String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                        wxService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    }
                } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                    wxService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                }
            } catch (WxErrorException e) {
                logger.error("callback", e);
            }
        } else {
            WxMpXmlOutMessage outMessage = wxService.getWxOpenMessageRouter().route(inMessage, appId);
            if (outMessage != null) {
                out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, wxService.getWxOpenConfigStorage());
            }
        }
        return out;
	}
}

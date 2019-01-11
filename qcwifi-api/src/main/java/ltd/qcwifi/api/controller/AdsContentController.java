package ltd.qcwifi.api.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.api.annotation.AopLog;
import ltd.qcwifi.api.rabbit.mq.RouterProducer;
import ltd.qcwifi.cloud.push.content.service.AdsContentService;
import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.comm.utils.SysDbPkUtil;
import ltd.qcwifi.model.dto.ApiResult;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.cloud.push.content.AdsContent;
import ltd.qcwifi.model.entity.cloud.push.content.AdsSeq;

/**
 * @ClassName: RouterController
 * @Description: 路由器相关API
 * @author: zhfang
 * @date: 2018年2月3日 下午7:31:57
 */
@RestController
@RequestMapping("/foIcyc2jgoY3K/bdstatics")
public class AdsContentController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdsContentController.class);
	
	@Autowired
	AdsContentService adsService;
	
	@Autowired
	ShopRouterService routerService;
	
	@Autowired
    RouterProducer routerProducer;
	
	/**
	 * 请求广告资源
	 * @Title: getAdsContent
	 * @author: zhfang
	 * @Description: TODO
	 * @param seq
	 * @return
	 * @return: ApiResult<JSONObject>
	 */
	@RequestMapping(value = "/content", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	@AopLog("/ads/content")
	public ApiResult<JSONObject> getAdsContent(@RequestBody AdsSeq seq) {
		try {
			if (StringUtils.isBlank(seq.getDevNo())) {
				return new ApiResult<>(10013);
			}
			//获取设备信息
			ShopRouter router = routerService.getRouterByNo(seq.getDevNo());
			if (router == null) {
				logger.info("设备 {} 不存在", seq.getDevNo());
				return new ApiResult<>(10010);
			}
			//设备存在，则获取当前设备所属账号下的所有广告信息
			//并且满足日推送剩余量>0和总推送剩余量>0 切状态是激活的
			List<AdsContent> ads = adsService.getAds(router.getCreateBy());
			if (ads == null || ads.isEmpty()) {
				logger.info("平台下无可推送的广告资源");
				return new ApiResult<>(10021);
			}
			AdsContent ad = adsService.getRandomAd(ads);
			logger.info("{}推送的广告为：{}", seq.getDevNo(), ad.toString());
			
			String logid = SysDbPkUtil.createSeq();
			logger.info("{}推送的广告流水号为：{}", seq.getDevNo(), logid);
			// 异步记录广告推送日志
			seq.setLogid(logid);
			seq.setAdId(ad.getId());
			seq.setPush(new Date());
			routerProducer.adsSeqPushToQueue(seq);
			
			JSONObject obj = adsService.createAdHtml(ad, logid);
			return new ApiResult<>(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
	
	
	/**
	 * 流水号上传
	 * @Title: pushSeq
	 * @author: zhfang
	 * @Description: TODO
	 * @param seq
	 * @return
	 * @return: ApiResult<JSONObject>
	 */
	@RequestMapping(value = "/seq", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	public ApiResult<String> pushSeq(@RequestBody AdsSeq seq) {
		try {
			if (StringUtils.isBlank(seq.getLogid())) {
				return new ApiResult<>(10022);
			}
			if (seq.getType() == 1) {
				seq.setShew(new Date());
			}else if (seq.getType() == 2) {
				seq.setClick(new Date());
			}else if (seq.getType() == 3) {
				seq.setCloze(new Date());
			}
			//信息放入MQ
			routerProducer.adsSeqToQueue(seq);
			return new ApiResult<>("success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
}

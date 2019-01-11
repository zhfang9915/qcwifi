package ltd.qcwifi.cloud.push.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.cloud.push.content.service.AdsContentService;
import ltd.qcwifi.comm.utils.WeightRandomUtil;
import ltd.qcwifi.dao.cloud.push.content.AdsContentDao;
import ltd.qcwifi.model.entity.cloud.push.content.AdsContent;

/**
 * 广告推送服务实现类
 * @ClassName: AdsContentServiceImpl
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年3月10日 下午9:22:04
 */
@Service
public class AdsContentServiceImpl implements AdsContentService {

	@Autowired
	AdsContentDao adsContentDao;
	
	@Override
	public List<AdsContent> getAds(Long createBy) {
		// 获取当前用户下的所有可推送的广告资源
		List<AdsContent> ads = adsContentDao.queryAdsByCreateBy(createBy);
		if (ads != null && !ads.isEmpty()) {
			return ads;
		}
		// 如果当前用户名下无广告资源，则推送管理员名下的广告资源信息
//		ads = adsContentDao.queryAdsForQcwifi();
		return ads;
	}
	
	@Override
	public AdsContent getRandomAd(List<AdsContent> ads) {
		//将广告信息放入算法
		List<Pair<AdsContent, Integer>> list = new ArrayList<>();
		for (AdsContent ad : ads) {
			list.add(new Pair<AdsContent, Integer>(ad, ad.getWeight()));
		}
		WeightRandomUtil<AdsContent, Integer> random = new WeightRandomUtil<>(list);
		return random.random();
	}
	
	@Override
	public JSONObject createAdHtml(AdsContent ad,String logid) {
		JSONObject obj = new JSONObject();
		//控制JS
		obj.put("js", ad.getTempJs());
		String tfors = "";
		String[] imgs = ad.getImgs().split(",");
		String[] urls = null;
		if (ad.getUrls() != null) {
			urls = ad.getUrls().split(",");
		}
		for (int i = 0; i < imgs.length; i++) {
			tfors += ad.getTempFor().replace("$advertUrl$", imgs[i]);
			if (urls != null) {
				tfors.replace("$toUrl$", urls[i]);
			}
		}
		//广告主体
		obj.put("html", ad.getTemp().replace("$advert$", tfors).replace("$logid$", logid));
		return obj;
	}

}

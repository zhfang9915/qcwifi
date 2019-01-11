package ltd.qcwifi.cloud.push.content.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.model.entity.cloud.push.content.AdsContent;

/**
 * 广告推送服务接口
 * @ClassName: AdsContentService
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年3月10日 下午9:21:11
 */
public interface AdsContentService {

	/**
	 * 获取可推送的广告资源
	 * @Title: getAds
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @return
	 * @return: List<AdsContent>
	 */
	List<AdsContent> getAds(Long createBy);

	/**
	 * 根据权重随机算法获取被推送的一条广告信息
	 * @Title: getRandomAd
	 * @author: zhfang
	 * @Description: TODO
	 * @param ads
	 * @return
	 * @return: AdsContent
	 */
	AdsContent getRandomAd(List<AdsContent> ads);

	/**
	 * 生成广告网页脚本
	 * @Title: createAdHtml
	 * @author: zhfang
	 * @Description: TODO
	 * @param ad
	 * @param logid
	 * @return
	 * @return: JSONObject
	 */
	JSONObject createAdHtml(AdsContent ad, String logid);
}

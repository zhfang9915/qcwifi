package ltd.qcwifi.api.rabbit.mq;

import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.cloud.push.content.AdsSeq;

/**
 * 消息生产者
 * @ClassName: RouterProducer
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月24日 下午8:46:33
 */
public interface RouterProducer {

	/**
	 * 发送消息到指定队列
	 * @Title: sendRouterInfoToQueue
	 * @author: zhfang
	 * @Description: TODO
	 * @param sr
	 * @return: void
	 */
    void sendRouterInfoToQueue(ShopRouter sr);
    
    /**
     * 发送广告流水到队列
     * @Title: sendAdsSeqToQueue
     * @author: zhfang
     * @Description: TODO
     * @param seq
     * @return: void
     */
    void adsSeqPushToQueue(AdsSeq seq);
    
    void adsSeqToQueue(AdsSeq seq);
    
}

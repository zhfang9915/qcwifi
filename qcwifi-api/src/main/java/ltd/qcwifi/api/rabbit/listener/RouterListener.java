package ltd.qcwifi.api.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.dao.cloud.ShopRouterDao;
import ltd.qcwifi.dao.cloud.push.content.AdsContentDao;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.cloud.push.content.AdsSeq;

/**
 * 路由器信息监听
 * @ClassName: RouterListener
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年3月10日 上午11:27:42
 */
@Component
public class RouterListener {
	
	private static final Logger logger = LoggerFactory.getLogger(RouterListener.class);
	
	@Autowired
	ShopRouterDao routerDao;
	
	@Autowired
	AdsContentDao adsContentDao;

	@RabbitListener(queues = "topic.router.info")
    public void RouterInfomation(Message obj) {
		String info = new String(obj.getBody());
		logger.info("MQ 设备上传的信息为：{}", info);
		ShopRouter router = JSONObject.parseObject(info, ShopRouter.class);
		int count = routerDao.insert(router);
		logger.info("MQ 设备信息持久化状态： {}", count);
    }
	
	@RabbitListener(queues = "topic.ads.seq")
    public void adsSeq(Message obj) {
		String info = new String(obj.getBody());
		logger.info("MQ 广告流水的信息为：{}", info);
		AdsSeq seq = JSONObject.parseObject(info, AdsSeq.class);
		int count = adsContentDao.insertAdsSeq(seq);
		logger.info("MQ 广告流水持久化状态： {}", count);
    }
	
	@RabbitListener(queues = "topic.ads.seq.upload")
    public void adsSeqUpload(Message obj) {
		String info = new String(obj.getBody());
		logger.info("MQ 广告流水的信息为：{}", info);
		AdsSeq seq = JSONObject.parseObject(info, AdsSeq.class);
		int count = adsContentDao.updateAdsSeq(seq);
		logger.info("MQ 广告流水持久化状态： {}", count);
    }
	
	
}

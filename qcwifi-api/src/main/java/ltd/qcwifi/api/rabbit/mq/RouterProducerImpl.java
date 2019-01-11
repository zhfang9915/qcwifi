package ltd.qcwifi.api.rabbit.mq;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.cloud.push.content.AdsSeq;


@Service
public class RouterProducerImpl implements RouterProducer {

	
	@Resource(name="rabbitTemplate")
    private RabbitTemplate rabbitTemplate;
	
	@Override
	public void sendRouterInfoToQueue(ShopRouter sr) {
		/**
		 * convertAndSend：将Java对象转换为消息发送到匹配Key的交换机中Exchange，
		 * 由于配置了JSON转换，这里是将Java对象转换成JSON字符串的形式。
		 */
		//topic
		rabbitTemplate.convertAndSend("topic.exchange.qcwifi", "topic.router.info", sr);
	}
	
	@Override
	public void adsSeqPushToQueue(AdsSeq seq) {
		rabbitTemplate.convertAndSend("topic.exchange.qcwifi", "topic.ads.seq", seq);
	}
	
	@Override
	public void adsSeqToQueue(AdsSeq seq) {
		rabbitTemplate.convertAndSend("topic.exchange.qcwifi", "topic.ads.seq.upload", seq);
	}
	

}

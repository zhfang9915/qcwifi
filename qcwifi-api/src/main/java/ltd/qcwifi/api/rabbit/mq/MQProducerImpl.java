package ltd.qcwifi.api.rabbit.mq;

import javax.annotation.Resource;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class MQProducerImpl implements MQProducer {

	
	@Resource(name="rabbitTemplate")
    private RabbitTemplate rabbitTemplate;
	
	@Override
	public void sendDataToQueue(Object object) {
		/**
		 * convertAndSend：将Java对象转换为消息发送到匹配Key的交换机中Exchange，
		 * 由于配置了JSON转换，这里是将Java对象转换成JSON字符串的形式。
		 */
		//direct
//		rabbitTemplate.convertAndSend("qcwifi.direct.exchange", "qcwifi.binding.async", object);
//		rabbitTemplate.convertAndSend("qcwifi.direct.exchange", "qcwifi.binding.sync", object);
		
		//fanout
		rabbitTemplate.convertAndSend("qcwifi.fanout.exchange", "router.info", object);
		
		//topic
//		rabbitTemplate.convertAndSend("qcwifi.topic.exchange", "qcwifi.binding.test", object);
	}

}

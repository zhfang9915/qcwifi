package ltd.qcwifi.api.rabbit.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 消费者监听
 * @ClassName: AsyncQueueListener
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月25日 下午3:20:22
 */
@Component
public class FanoutQueueListener {

//	@RabbitListener(queues = "router.info")
//	@SendTo("qcwifi.queue.async.reply")
//    public String fanout1(Message obj) {
//		System.out.println("Received request fanout1 for id " + new String(obj.getBody()));
//        return "fanout1 reply message";
//    }
//	
//	@RabbitListener(queues = "router.info")
//	@SendTo("qcwifi.queue.async.reply")
//    public String fanout2(Message obj) {
//		System.out.println("Received request fanout2 for id " + new String(obj.getBody()));
//        return "fanout2 reply message";
//    }
}

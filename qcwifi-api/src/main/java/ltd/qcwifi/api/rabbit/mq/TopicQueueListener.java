//package ltd.qcwifi.api.rabbit.mq;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//
///**
// * 消费者监听
// * @ClassName: AsyncQueueListener
// * @Description: TODO
// * @author: zhfang
// * @date: 2018年2月25日 下午3:20:22
// */
//@Component
//public class TopicQueueListener {
//
//	@RabbitListener(queues = "qcwifi.queue.topic")
//	@SendTo("qcwifi.queue.async.reply")
//    public String topic(Message obj) {
//		System.out.println("Received request topic for id " + new String(obj.getBody()));
//        return "topic message";
//    }
//}

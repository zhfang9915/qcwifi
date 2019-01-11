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
public class AsyncQueueListener {

    /**
     * 同步队列
     * @param id 任务ID
     * @param type 任务名称
     */
    @RabbitListener(queues = "qcwifi.queue.sync")
    public void hello(Message obj) {
        System.out.println("Received request sync for id " + new String(obj.getBody()));
    }

    /**
     * 异步队列，SendTo为回复的队列名称
     * @param id 任务ID
     * @param type 任务名称
     * @return
     */
    @RabbitListener(queues = "qcwifi.queue.async")
    @SendTo("qcwifi.queue.async.reply")
    public String hello2(Message obj) {
        System.out.println("Received request async for id " + new String(obj.getBody()));
        return "helle , rabbit MQ";
    }
}

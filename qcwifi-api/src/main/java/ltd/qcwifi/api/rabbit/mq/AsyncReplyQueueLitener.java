package ltd.qcwifi.api.rabbit.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 回调监听
 * @ClassName: AsyncReplyQueueLitener
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月25日 下午3:20:08
 */
@Component
public class AsyncReplyQueueLitener {

    @RabbitListener(queues = { "qcwifi.queue.async.reply", "qcwifi.queue.bds.reply", "qcwifi.queue.etl.reply", "qcwifi.queue.mal.reply" })
    public void asyncReply(String response) {
        System.out.println("reply----> " + response);
    }
}
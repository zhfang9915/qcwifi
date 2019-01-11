package ltd.qcwifi.api.rabbit.mq;

/**
 * 消息生产者
 * @ClassName: MQProducer
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月24日 下午8:46:33
 */
public interface MQProducer {

	/**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
    public void sendDataToQueue(Object object);
}

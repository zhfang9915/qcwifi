package ltd.qcwifi.cloud.platform.service;

/**
 * 消息发送
 * 	短信、邮件等
 * @author Administrator
 *
 */
public interface SmsService {

	/**
	 * 消息发送方法
	 * @param toAccount
	 * @param msg
	 */
	boolean sendMsg(String toAccount, String msg);
}

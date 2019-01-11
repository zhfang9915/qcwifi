package ltd.qcwifi.cloud.platform.service;

import javax.mail.MessagingException;

public interface MailService {

	/**
	 * 发送验证码
	 * @param to 收件人
	 * @param subject 主题
	 * @param text 内容
	 */
	boolean sendRandCode(String to, String subject, String text);
	
	/**
	 * 发送文本
	 * @param to 收件人
	 * @param subject 主题
	 * @param text 内容
	 */
	void sendText(String to, String subject, String text);

	/**
	 * 发送html邮件
	 * @param to 收件人
	 * @param subject 主题
	 * @param html 内容
	 * @throws MessagingException
	 */
	void sendHtml(String to, String subject, String html);

	/**
	 * 发送附件
	 * @param to 收件人
	 * @param subject 主题
	 * @param html 内容
	 * @param path 附件存放路径
	 */
	void sendAttachedFile(String to, String subject, String html, String path);

}

package ltd.qcwifi.cloud.platform.service.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.platform.service.MailService;
import ltd.qcwifi.cloud.platform.service.RedisService;

@Service
public class MailServiceImpl implements MailService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${mail.username}")
	private String from;
	
	@Autowired
	ThreadPoolTaskExecutor threadPool;
	
	@Autowired
	RedisService redis;
	
	@Override
	public boolean sendRandCode(final String to, final String subject, final String text) {
		long count = redis.smsToRedis(to);	//手机号放入redis一分钟
		if (count > 1) {
			return false;		//前端提示短信发送频繁
		}else {
			threadPool.execute(() -> {
				try {
					SimpleMailMessage mailMessage = new SimpleMailMessage();  
					mailMessage.setTo(to);//收件人
					String nick=MimeUtility.encodeText("前辰无线"); 
					mailMessage.setFrom(new InternetAddress(nick+" <"+from+">").toString());//发件人
					mailMessage.setSubject(subject); //主题
					mailMessage.setText(text);//邮件内容
					mailSender.send(mailMessage);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			});
			return true;
		}
		
	}

	/**
	 * 发送文本
	 * @param to 收件人
	 * @param subject 主题
	 * @param text 内容
	 */
	@Override
	public void sendText(String to, String subject, String text) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();  
			mailMessage.setTo(to);//收件人
			String nick=MimeUtility.encodeText("前辰无线"); 
			mailMessage.setFrom(new InternetAddress(nick+" <"+from+">").toString());//发件人
			mailMessage.setSubject(subject); //主题
			mailMessage.setText(text);//邮件内容
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 发送html邮件
	 * @param to 收件人
	 * @param subject 主题
	 * @param html 内容
	 * @throws MessagingException
	 */
	@Override
	public void sendHtml(String to, String subject, String html){
		try {
			logger.info("开始发送html邮件");
			// 建立邮件消息,发送简单邮件和html邮件的区别  
			MimeMessage mailMessage = mailSender.createMimeMessage();  
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");  
  
			// 设置收件人，寄件人  
			messageHelper.setTo(to);  
			String nick=MimeUtility.encodeText("前辰无线"); 
			messageHelper.setFrom(new InternetAddress(nick+" <"+from+">").toString());//发件人
			messageHelper.setSubject(subject);  
			// true 表示启动HTML格式的邮件  
			messageHelper.setText(html, true);
			mailSender.send(mailMessage);
			logger.info("发送html邮件成功");
		} catch (Exception e) {
			logger.info("发送html邮件异常" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送附件
	 * @param to 收件人
	 * @param subject 主题
	 * @param html 内容
	 * @param path 附件存放路径
	 */
	@Override
	public void sendAttachedFile(String to, String subject, String html, String path){
		try {
			// 建立邮件消息,发送简单邮件和html邮件的区别  
			MimeMessage mailMessage = mailSender.createMimeMessage();  
			// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，  
			// multipart模式 为true时发送附件 可以设置html格式  
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");  
			// 设置收件人，寄件人  
			messageHelper.setTo(to);  
			messageHelper.setFrom(from);  
			messageHelper.setSubject(subject);  
			// true 表示启动HTML格式的邮件  
			messageHelper.setText(html, true);  
  
			FileSystemResource file = new FileSystemResource(new File(path));  
			// 这里的方法调用和插入图片是不同的。  
			messageHelper.addAttachment(file.getFilename(), file);
			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}

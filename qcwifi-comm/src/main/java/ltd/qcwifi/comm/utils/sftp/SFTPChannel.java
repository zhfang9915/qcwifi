package ltd.qcwifi.comm.utils.sftp;

import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Component
public class SFTPChannel {
	Session session = null;
	Channel channel = null;

	@Value("${sftp.host}")
	private String host;

	@Value("${sftp.port}")
	private int port;

	@Value("${sftp.username}")
	private String username;

	@Value("${sftp.password}")
	private String password;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ChannelSftp getChannel(int timeout) throws Exception {
		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(username, host, port); // 根据用户名，主机ip，端口获取一个Session对象
		logger.debug("SFTP Session created.");
		if (password != null) {
			session.setPassword(password); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间 0为不超时
		session.connect(); // 通过Session建立链接
		logger.debug("SFTP Session connected.");

		logger.debug("SFTP Opening Channel.");
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		logger.debug("SFTP Connected successfully to ftpHost = " + host + ",as ftpUserName = " + username
				+ ", returning: " + channel);
		ChannelSftp sftp = (ChannelSftp) channel;
		Class<ChannelSftp> cl = ChannelSftp.class;  
    	Field f =cl.getDeclaredField("server_version");  
    	f.setAccessible(true);  
    	f.set(sftp, 2);  
    	sftp.setFilenameEncoding("GBK");
    	return sftp;
	}

	public void closeChannel() {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
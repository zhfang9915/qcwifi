package ltd.qcwifi.comm.utils.sftp;

import com.jcraft.jsch.ChannelSftp;

/**
 * SFTP工具集
 * @author 张芳
 *
 */
public class SFTPUtil {

	/**
	 * 本地路径文件上传至FTP
	 * @param src
	 * @param dst
	 * @param timeout
	 * @return
	 */
	public boolean uploadByFile(String src, String dst, int timeout) {
        SFTPChannel channel = new SFTPChannel();
        ChannelSftp chSftp = null;
        try {
        	chSftp = channel.getChannel(timeout);
			chSftp.put(src, dst, ChannelSftp.OVERWRITE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  finally {
			if (chSftp != null) {
				chSftp.quit();
			}
			if (channel != null) {
				channel.closeChannel();
			}
		}
	}
}

/**
 * 创建于 2016年7月6日 下午2:41:58
 *
 */

package ltd.qcwifi.comm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.*; 
import org.apache.commons.io.IOUtils;

/** 
 * @ClassName: FileMD5Util 
 * @Description: TODO
 * @author zhfang
 *  
 */
public class FileMD5Util {
	
static MessageDigest MD5 = null;
	
	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件的MD5
	 * @Title: getMD5 
	 * @Description: TODO
	 * @param @param file
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	public static String getLargeFileMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int len;
			while ((len = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, len);
			}
			return new String(org.apache.commons.codec.binary.Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 字符串的MD5
	 * @Title: StringMD5 
	 * @Description: TODO
	 * @param @param target
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	public static String StringMD5(String target) {
		return DigestUtils.md5Hex(target);
	}
	

	/**
	 * 根据文件获取文件的MD5
	 * @Title: getMd5ByFile 
	 * @Description: TODO
	 * @param @param file
	 * @param @return
	 * @param @throws FileNotFoundException    
	 * @return String    
	 * @throws
	 */
	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 根据流获取文件的MD5
	 * @Title: getMd5ByStream 
	 * @Description: TODO
	 * @param @param in
	 * @param @return
	 * @param @throws IOException    
	 * @return String    
	 * @throws
	 */
	public static String getMd5ByInputStream(InputStream in) throws IOException {
		String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(in));
		IOUtils.closeQuietly(in);
		return md5;
	}
	
}

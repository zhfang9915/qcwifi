package ltd.qcwifi.shiro.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class SimpleHashUtil {

	/**
	 * 生成shiro的加密规则的密码
	 * @param password
	 * @param customSalt
	 * @return
	 */
	public static String createMD5Password(String password, String customSalt) {
		String hashAlgorithmName = "MD5";
		Object credentials = password;
		Object salt = ByteSource.Util.bytes(customSalt);;
		int hashIterations = 5;
		return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
		 
	}
}

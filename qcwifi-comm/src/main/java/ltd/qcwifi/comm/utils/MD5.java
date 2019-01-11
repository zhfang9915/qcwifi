package ltd.qcwifi.comm.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author xuwj 2014-6-9 上午09:31:31
 */
public class MD5 {
	private static final String HEX_NUMS_STR = "0123456789abcdef";
	//private static final Integer SALT_LENGTH = 12;

	/**
	 * 将16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
		}
		return result;
	}

	/**
	 * 将指定byte数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	public static boolean validPassword(String password, String passwordInDb) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		byte[] pwdInDb = hexStringToByte(passwordInDb);
		// 声明盐变量
		//byte[] salt = new byte[SALT_LENGTH];
		// 将盐从数据库中保存的口令字节数组中提取出来
		//System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
		// 创建消息摘要对象
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 将盐数据传入消息摘要对象
		//md.update(salt);
		// 将口令的数据传给消息摘要对象
		md.update(password.getBytes("UTF-8"));
		// 生成输入口令的消息摘要
		byte[] digest = md.digest();
		// 声明一个保存数据库中口令消息摘要的变量
		//byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
		//byte[] digestInDb = new byte[pwdInDb.length];
		// 取得数据库中口令的消息摘要
		//System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
		// 比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
		if (Arrays.equals(digest, pwdInDb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得加密后的16进制口令
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncryptedPwd(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//byte[] pwd = null;
		//SecureRandom random = new SecureRandom();
		// 声明盐变量
		//byte[] salt = new byte[SALT_LENGTH];
		//random.nextBytes(salt);

		// 创建消息摘要对象
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 将盐数据传入消息摘要对象
		//md.update(salt);
		// 将口令的数据传给消息摘要对象
		md.update(password.getBytes("UTF-8"));
		// 生成输入口令的消息摘要
		byte[] digest = md.digest();
		//pwd = new byte[digest.length + SALT_LENGTH];
		// 将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便石验证口令时取出盐
		//System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
		// 将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
		//System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);

		// 将字节数组格式加密后的口令转化为16字符串格式的口令
		return byteToHexString(digest);
	}

	public static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

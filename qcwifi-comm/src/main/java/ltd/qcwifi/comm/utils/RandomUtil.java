/**
 * 创建于 2016年8月28日 下午8:56:37
 *
 */

package ltd.qcwifi.comm.utils;

/**
 * @ClassName: RandomUtil
 * @Description: 随机工具类
 * @author zhfang
 * 
 */
public class RandomUtil {

	/**
	 * 获取6位数字随机码
	 * @return
	 */
	public static String getRandCode() {
		int randNum = (int)((Math.random()*9+1)*100000);
		return randNum + "";
	} 
	
}

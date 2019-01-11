package ltd.qcwifi.comm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SysDbPkUtil {

	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");

	/**
	 * 主键
	 * @Title: createPk
	 * @author: zhfang
	 * @Description: TODO
	 * @param subx
	 * @return
	 * @return: String
	 */
	public static String createPk(String subx) {
		//DEV20182910120304204-925132983644365
		return (subx + format.format(new Date()) + new Random().nextInt(1000000)).replace("-", "");
	}
	
	/**
	 * 流水序列号
	 * @Title: createSeq
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public static String createSeq(){
		String now = CalendarUtil.parseLongTime(new Date());
		int random = (int)((Math.random()*9+1)*1000000);
		return now + "0000" + random;
	}
	
}

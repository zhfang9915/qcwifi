package ltd.qcwifi.comm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 作者 Immortal long
 * @version 创建时间：2010-4-12 下午12:34:45 类说明
 */
public class CalendarUtil {

	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 2003-10-17 21:15:37
	 */
	public static String parseStdTime(Date date) {
		String fmt = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(fmt);
		return df.format(date);
	}

	/**
	 * YYYYMMDDHHMMSS
	 */
	public static String parseLongTime(Date date) {
		String fmt = "yyyyMMddHHmmss";
		DateFormat df = new SimpleDateFormat(fmt);
		String str = df.format(date);
		return str;
	}

	/**
	 * YYYY-MM-DD
	 */
	public static String getLongDate(Date date) {
		String fmt = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(fmt);
		String str = df.format(date);
		return str;
	}

	/**
	 * 是否是指定标准格式的日期
	 * 
	 * @return
	 */
	public static boolean isFmtDate(String str, String fmt) {
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		boolean dateflag = true;
		try {
			format.parse(str);
		} catch (ParseException e) {
			dateflag = false;
		}
		return dateflag;
	}
	
	/**
	 * 获取指定的日期
	 * @param amount
	 * @param format
	 * @return
	 */
	public static String getAssignDate(int amount, String format) {
		Date date=new Date();//取时间  
		Calendar calendar = new GregorianCalendar();  
		calendar.setTime(date);  
		calendar.add(Calendar.DATE,amount);//把日期往后增加一天.整数往后推,负数往前移动  
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果   
		SimpleDateFormat formatter = new SimpleDateFormat(format);  
		return formatter.format(date); 
	}
}

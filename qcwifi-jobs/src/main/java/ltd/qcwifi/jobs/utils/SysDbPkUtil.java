package ltd.qcwifi.jobs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class SysDbPkUtil {
	
	public static SimpleDateFormat format = new SimpleDateFormat("yyyymmddHHMMssSS");

	public static String createPk(String tableName){
		
		return "PK"+format.format(new Date())+tableName.hashCode()+new Random().nextInt(1000000);
	}
}

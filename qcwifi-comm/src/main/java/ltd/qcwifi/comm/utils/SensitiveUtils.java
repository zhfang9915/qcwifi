package ltd.qcwifi.comm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏工具类
 * @author 张芳
 *
 */
public class SensitiveUtils {


	public static String sensitive(Type type,String value){
		switch (type) {
		case address:
			Pattern pattern= Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
			Matcher matcher= pattern.matcher(value);
			List<String> list = new ArrayList<String>();
			while ( matcher.find() ) {
				Pattern pat = Pattern.compile("[0-9]*"); 
				Matcher isNum = pat.matcher(matcher.group());
				if(isNum.matches() ){
					list.add(judgeSize(matcher.group()));
				}else{
					list.add(matcher.group());
				}
			}
			String address = "";
			for (int i = 0; i < list.size(); i++) {
				address = address + list.get(i);
			}
			return address;
		case name:
			String regex = "[\u4E00-\u9FA5]+";
			if(value.matches(regex)){
				String name = StringUtils.right(value, 1);
				return StringUtils.leftPad(name, StringUtils.length(value), "*");  
			}else{
				String name = StringUtils.left(value, 2);
				return StringUtils.rightPad(name, StringUtils.length(value), "*");
			}
		case email:
			int index = StringUtils.indexOf(value, "@");  
			if (index <= 1){  
				return value;  
			}else { 
				return StringUtils.rightPad(StringUtils.left(value, 2), index, "*").concat(StringUtils.mid(value, index, StringUtils.length(value)));  
			}

		case phone:
			switch (value.length()) {
			case 8:
				return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 1), StringUtils.length(value), "*"), "***"));  
			case 11:
				return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 4), StringUtils.length(value), "*"), "***"));  
			case 10:
				return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 5), StringUtils.length(value), "*"), "***"));  
			case 12:
				return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 5), StringUtils.length(value), "*"), "***"));  
			default:
				return "数据出错";
			}
		case IDcard:
			return StringUtils.left(value, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 1), StringUtils.length(value), "*"), "***"));  
		default:
			return "数据不存在";
		}
	}

	/**
	 * 对地址的长度经行判断
	 */
	public static String judgeSize(String values){
		if(values.length() == 1){
			return "*";
		}else{
			String num = StringUtils.right(values, 1);
			return StringUtils.leftPad(num, StringUtils.length(values), "*"); 
		}
	}
	public enum Type {
		/**
		 * 地址
		 */
		address,
		/**
		 * 姓名
		 */
		name,
		/**
		 * 邮箱
		 */
		email,
		/**
		 * 电话
		 */
		phone,
		/**
		 * 身份证
		 */
		IDcard
	}
}

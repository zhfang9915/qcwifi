package ltd.qcwifi.comm.utils;

public class SourceCodeFormat {
	/**
	 * 将源码中的特殊字符转义为符号实体
	 * @param code
	 * @return
	 */
	public static String sourceCodeEncode(String code) {
		code = code.replace(">", "&gt;")
				.replace("<", "&lt;")
				.replace(" ", "&nbsp;")
				.replace("\"", "&quot;")
				.replace("\'", "&#39;");
//				.replace("\n", "<br/>");
		return code;
	}
	
	/**
	 * 将转义后的源码解析为源码
	 * @param code
	 * @return
	 */
	public static String sourceCodeDiscode(String code) {
		code = code.replace("&gt;", ">")
				.replace("&lt;", "<")
				.replace("&nbsp;", " ")
				.replace("&quot;", "\"")
				.replace("&#39;", "\'");
//				.replace("<br/>", "\n");
		return code;
	}
}

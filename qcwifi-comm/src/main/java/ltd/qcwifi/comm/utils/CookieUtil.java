package ltd.qcwifi.comm.utils;

/**
 * Cookie工具
 */
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CookieUtil {
	
	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		try {
			Cookie cookie = new Cookie(name, value);
			if (maxAge > 0)
				cookie.setMaxAge(maxAge);
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception ex) {
			System.err.println("创建Cookies发生异常:" + ex.getMessage());
		}
	}

	/**
	 * 清空Cookie操作 clearCookie
	 * 
	 * @param request
	 * @param response
	 * @return boolean
	 * @author JIANG FEI Jun 19, 2014 10:12:17 AM
	 */
	public static boolean clearCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		boolean bool = false;
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return bool;
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie(name, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");// 根据你创建cookie的路径进行填写
				response.addCookie(cookie);
				bool = true;
			}
		} catch (Exception ex) {
			System.err.println("清空Cookies发生异常！" + ex.getMessage());
		}
		return bool;
	}

	/**
	 * 清空Cookie操作 clearCookie
	 * 
	 * @param request
	 * @param response
	 * @return boolean
	 * @author JIANG FEI Jun 19, 2014 10:12:17 AM
	 */
	public static boolean clearCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		boolean bool = false;
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return bool;
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie(name, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");// 根据你创建cookie的路径进行填写
				cookie.setDomain(domain);
				response.addCookie(cookie);
				bool = true;
			}
		} catch (Exception ex) {
			System.err.println("清空Cookies发生异常！" + ex.getMessage());
		}
		return bool;
	}

	/**
	 * 获取指定cookies的值 findCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return String
	 */
	public static String findCookieByName(HttpServletRequest request,
			String name) {
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return null;
		String string = null;
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String cname = cookie.getName();
				if (StringUtils.isNotBlank(cname) && cname.equals(name)) {
					string = cookie.getValue();
				}

			}
		} catch (Exception ex) {
			System.err.println("获取Cookies发生异常！" + ex.getMessage());
		}
		return string;
	}

}

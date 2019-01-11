/**
 * 创建于 2016年8月28日 下午9:07:11
 *
 */

package ltd.qcwifi.backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: BaseController
 * @Description: TODO
 * @author zhfang
 * 
 */
public class BaseController {
	
	/**
	 * getRequest()
	 */

	public HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return attributes.getRequest();
	}

	/**
	 * getSession()
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public Cookie addCookie(String name) {
		if (getCookie(name) == null) {
			Cookie cookie = new Cookie(name, UUID.randomUUID().toString());
			cookie.setMaxAge(60 * 60 * 24 * 30);
			return cookie;
		}
		return null;
	}

	/**
	 * 得到cookie 的值 getSession()
	 * 
	 * @param name
	 *            cookie_name
	 */
	public Cookie getCookie(String name) {
		Cookie[] cookies = getRequest().getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (Cookie thisCookie : cookies) {
			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;

					break;
				}
			}
		}

		return returnCookie;
	}

	public String webAppPath() {
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + path + "/";
		return basePath;
	}

	/**
	 * 将request中的参数转为Map
	 * 
	 * @param request
	 */
	protected Map<String, Object> transformMapParam(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String[]> params_strs = request.getParameterMap();
		if (null != params_strs && !params_strs.isEmpty()) {
			for (String params_name : params_strs.keySet()) {
				String[] str = params_strs.get(params_name);
				if (null == str) {
					continue;
				} else if (str.length == 1) {
					params.put(params_name, str[0]);
				} else {
					params.put(params_name, str);
				}
			}
		}
		return params;
	}
	
	protected boolean validateRandCode(String randCode) {
		String sessionRandCode = (String) getSession().getAttribute("randCode");
		if (StringUtils.isBlank(randCode) || StringUtils.isBlank(sessionRandCode)) {
			return false;
		}
		randCode = randCode.toLowerCase();	//转成小写
		if (randCode.equals(sessionRandCode)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 转JSON
	 * @param object
	 * @return
	 */
	protected String toJson(Object object) {
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}

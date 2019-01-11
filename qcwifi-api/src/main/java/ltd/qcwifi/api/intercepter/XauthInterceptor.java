package ltd.qcwifi.api.intercepter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.comm.utils.MD5;
import ltd.qcwifi.model.dto.ApiResult;

/**
 * 认证
 * @author 张芳
 * 请求中必须带有 x-auth 头，格式为： x-auth: <渠道编码>;<路由器Mac>;<流水号>;<摘要> ，请求接收方必须检查摘要的合法性。 
 *	渠道编码：由前辰分配。 
 *  Mac地址：路由器的mac地址。
 *	流水号：1~30位数字、字母组合，大小写敏感，请求发起方应保证其唯一性。
 *	摘要：MD5（渠道编码+路由器Mac+流水号+前辰分配的密钥+Request URI+Request Body） 
 */
@Component
public class XauthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			ApiResult<Boolean> result = null;
			response.setContentType("application/json;charset=UTF-8");
			
			//1. 获取头部信息 x-auth
			String xauth = request.getHeader("x-auth");
			System.out.println("xauth-->" + xauth);
			if (xauth == null) {
				result = new ApiResult<>(10002);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}
			
			String channel = null;	//渠道编码
			String mac = null;	//路由器MAC地址
			String seq = null;	//流水号
			String md5 = null;	//摘要
			//解析x-auth
			try {
				String[] xauthArr = xauth.split(";");
				channel = xauthArr[0];
				mac = xauthArr[1];
				seq = xauthArr[2];
				md5 = xauthArr[3];
			} catch (Exception e) {
				result = new ApiResult<>(10003);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}
			
			//2. 验证渠道
			Properties propertie = new Properties();
			try {
				propertie.load(XauthInterceptor.class.getClassLoader().getResourceAsStream("partner.properties"));
			} catch (IOException e) {
				result = new ApiResult<>(10001);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}
			String channels = propertie.getProperty("channel");
			if (!channels.contains(channel)) {
				result = new ApiResult<>(10004);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}
			
			//3. 验证mac地址  验证路由器是否注册到云
			
			
			//4. 验证流水号
			if (!seq.matches("[0-9A-Za-z]{8,30}")) {
				result = new ApiResult<>(10005);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}

			//5. requestURI
			String requestUri = request.getRequestURI();
			String quesyString = request.getQueryString();
			if (StringUtils.isNotBlank(quesyString)) {
				requestUri += "?" + request.getQueryString();
			}
			System.out.println("requestUri-->" + requestUri);
			
			//6. requestBody
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String requestBody = IOUtils.read(reader);
			System.out.println("requestBody-->" + requestBody);

			//获取当前渠道的密钥 生成摘要
			String key = propertie.getProperty(channel);
			String context = channel 
					+ mac
					+ seq
					+ key
					+ requestUri
					+ requestBody;
			System.out.println("context-->" + context);
			String localMd5 = MD5.getMD5(context).toLowerCase();
			System.out.println("localMd5-->" + localMd5);
			if (!md5.equals(localMd5)) {
				result = new ApiResult<>(10006);
				response.getWriter().print(JSONObject.toJSONString(result));
				return false;
			}
		}
		return true;
	}
	
}

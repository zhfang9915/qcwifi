/**  

 
			2018年2月5日
                               _(\_/) 
                             ,((((^`\
                            ((((  (6 \ 
                          ,((((( ,    \
      ,,,_              ,(((((  /"._  ,`,
     ((((\\ ,...       ,((((   /    `-.-'
     )))  ;'    `"'"'""((((   (      
    (((  /            (((      \
     )) |      zhfang          |
    ((  |        .       '     |
    ))  \     _ '      `t   ,.')
    (   |   y;- -,-""'"-.\   \/  
    )   / ./  ) /         `\  \
       |./   ( (           / /'
       ||     \\          //'|
       ||      \\       _//'||
       ||       ))     |_/  ||
       \_\     |_/          ||
       `'"                  \_\
                            `'" 
 *
 * @Title: EmallLog.java
 * @Prject: VistaMall-pojo
 * @Package: org.vista.emall.log
 * @Description: TODO
 * @author: zhfang  
 * @date: 2018年2月5日 上午9:56:16
 * @version: V1.0  

 */
package ltd.qcwifi.model.entity.log;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @ClassName: EmallLog
 * @Description: 卖场公共日志
 * @author: zhfang
 * @date: 2018年2月5日 上午9:56:16
 */
public class ApiLog {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 请求时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date requestDate;
	/**
	 * 请求IP
	 */
	private String ip;
	/**
	 * 请求API
	 */
	private String invokeApi;
	/**
	 * 执行耗时
	 */
	private String invokeTime;
	/**
	 * 入参
	 */
	private String params;
	/**
	 * 结果
	 */
	private String result;
	/**
	 * 异常
	 */
	private String exception;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getInvokeApi() {
		return invokeApi;
	}

	public void setInvokeApi(String invokeApi) {
		this.invokeApi = invokeApi;
	}

	public String getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(String invokeTime) {
		this.invokeTime = invokeTime;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

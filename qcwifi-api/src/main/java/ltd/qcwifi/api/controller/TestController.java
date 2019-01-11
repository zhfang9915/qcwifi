package ltd.qcwifi.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ltd.qcwifi.api.rabbit.mq.MQProducer;
import ltd.qcwifi.model.dto.ApiResult;

@RestController
public class TestController {

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ApiResult<String> testGet(HttpServletRequest request) {
		System.out.println("get---" + request.getParameter("name"));
		return new ApiResult<String>("get is success");
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	public ApiResult<String> testPost(HttpServletRequest request, @RequestBody String requestBody) {
		System.out.println("post---" + request.getParameter("name"));
		System.out.println(requestBody);
		return new ApiResult<String>("get is success");
	}
	
	@Autowired
    MQProducer mqProducer;
	
	@RequestMapping(value = "/rabbit/mq", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult<String> testRabbmit() {
		Map<String,Object> msg = new HashMap<>();
        msg.put("data","hello,rabbmitmq!");
        mqProducer.sendDataToQueue(msg);
		return new ApiResult<String>("get is success");
	}
}

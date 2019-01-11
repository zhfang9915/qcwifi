package ltd.qcwifi.socket.controller;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ltd.qcwifi.socket.pojo.RouterLog;

@Controller
public class RouterController {
	
	public SimpMessagingTemplate template;  
	  
    @Autowired  
    public RouterController(SimpMessagingTemplate template) {  
        this.template = template;  
    }  
    
	@Resource(name="rabbitTemplate")
    RabbitTemplate rabbitTemplate;

	/**
	 * 路由器信息上传
	 * @Title: greeting
	 * @author: zhfang
	 * @Description: TODO
	 * @param log
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@MessageMapping("/router/log")
    public String greeting(RouterLog log) throws Exception {
		System.out.println("hello------ "+log);
		return "success";
    }
	
	
	
}

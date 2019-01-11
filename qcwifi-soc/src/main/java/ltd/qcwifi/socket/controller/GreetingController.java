package ltd.qcwifi.socket.controller;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.socket.pojo.Greeting;
import ltd.qcwifi.socket.pojo.HelloMessage;

@Controller
public class GreetingController {
	
	public SimpMessagingTemplate template;  
	  
    @Autowired  
    public GreetingController(SimpMessagingTemplate template) {  
        this.template = template;  
    }  
    
	@Resource(name="rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

	@MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
		System.out.println("hello------ "+message.getName());
		rabbitTemplate.convertAndSend("fanout.exchange.socket", "qc.socket.test", message);
    }
	
	@RabbitListener(queues = "qc.socket.test")
    public void RouterInfomation(Message obj) {
		String info = new String(obj.getBody());
		System.out.println("info ----> " + info);
		template.convertAndSend("/topic/greetings", new Greeting("Hello, " + info + "!"));
    }
	
	
}

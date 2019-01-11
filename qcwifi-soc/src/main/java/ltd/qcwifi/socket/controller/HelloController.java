package ltd.qcwifi.socket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("/index2")
	public String index2() {
		
		return "index2";
	}
}

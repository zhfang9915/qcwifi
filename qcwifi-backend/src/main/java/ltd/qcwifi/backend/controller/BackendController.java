package ltd.qcwifi.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BackendController extends BaseController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "main";
	}
}

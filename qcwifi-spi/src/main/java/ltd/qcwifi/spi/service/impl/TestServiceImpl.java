package ltd.qcwifi.spi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.spi.service.TestService;
import ltd.qcwifi.system.service.UserService;
import ltd.qcwifi.system.service.impl.UserServiceImpl;

public class TestServiceImpl implements TestService {
	
	@Override
	public ExecuteResult<String> execute(Object... args) {
		for (Object object : args) {
			System.out.println(object);
		}
		Map<String, Object> param = (Map<String, Object>)args[0];
		ApplicationContext context = (ApplicationContext) param.get("ApplicationContext");
		UserService userService = context.getBean(UserServiceImpl.class);
		List<Long> ls = userService.selectUserIdByRoleId(1L);
		System.out.println(JSONObject.toJSON(ls));
		return new ExecuteResult<>("执行成功");
	}

}

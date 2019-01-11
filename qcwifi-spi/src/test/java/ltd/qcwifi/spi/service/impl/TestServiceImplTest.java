package ltd.qcwifi.spi.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.protocal.factory.SpiExtensionFactory;

public class TestServiceImplTest {

	@Test
	public void testExecute() {
		SpiExtensionFactory spiFactory = new SpiExtensionFactory();
		
		ExecuteResult<String> result = spiFactory.execute("qcwifi.spi.test", "Hello world");
		
		System.out.println(JSONObject.toJSONString(result));
	}

}

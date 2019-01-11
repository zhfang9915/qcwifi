package ltd.qcwifi.cloud.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.platform.service.RedisService;
import ltd.qcwifi.cloud.platform.service.SmsService;

/**
 * 短信发送
 * @author Administrator
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	ThreadPoolTaskExecutor threadPool;
	
	@Autowired
	RedisService redis;
	
	@Override
	public boolean sendMsg(final String toAccount, final String msg) {
		long count = redis.smsToRedis(toAccount);	//手机号放入redis一分钟
		if (count > 1) {
			return false;		//前端提示短信发送频繁
		}else {
			threadPool.execute(() -> {
				System.out.println("this is sms msg! toAcccount:" + toAccount + ", msg:" + msg);
			});
			return true;
		}
	}
	

}

package ltd.qcwifi.cloud.platform.service.impl;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.platform.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	RedisTemplate<Serializable, Serializable> redisTemplate;
	
	/**
	 * 一分钟内，同一手机号不能多次发送短信验证码
	 * @param phone
	 * @return
	 */
	public long smsToRedis(String phone){
		String redisKey = "SMS_LIMIT_" + phone;
		long count = redisTemplate.opsForValue().increment(redisKey, 1);
		System.out.println("count--" + count);
		if (count == 1) {
			//设置有效期一分钟
			redisTemplate.expire(redisKey, 60, TimeUnit.SECONDS);
		}
		return count;
	}
}

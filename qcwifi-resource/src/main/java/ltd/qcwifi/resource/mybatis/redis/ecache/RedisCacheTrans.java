package ltd.qcwifi.resource.mybatis.redis.ecache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 使用 RedisCacheTrans 中间类解决 RedisCache.jedisConnectionFactory 的静态注入
 * 从而使MyBatis实现第三方缓存
 * @author 张芳
 */
public class RedisCacheTrans {

	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
	}
}

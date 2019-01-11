package ltd.qcwifi.base.dao.mybatis.ecache;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * 手动刷新redis 二级缓存
 * @author Administrator
 *
 */
@Component
public class MybatisRedisCache {
	
	private Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
	
	private static final int DB_INDEX = 1;
	
    private final String COMMON_CACHE_KEY = "Qcwifi:";
    
    private static final String UTF8 = "utf-8";
	
	@Autowired
	JedisConnectionFactory jedisConnectionFactory;

	/**
     * redis key规则前缀
     */
    private String getKeys(String nameSpace) {
    	logger.debug(COMMON_CACHE_KEY + nameSpace + ":*");
        return COMMON_CACHE_KEY + nameSpace + ":*";
    }
    
	public void clear(String nameSpace) {
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.select(DB_INDEX);
            Set<byte[]> keys = connection.keys(getKeys(nameSpace).getBytes(UTF8));
            logger.debug("手动清空指清空对应Mapper缓存======>" + keys.size());
            for (byte[] key : keys) {
            	connection.del(key);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}


}

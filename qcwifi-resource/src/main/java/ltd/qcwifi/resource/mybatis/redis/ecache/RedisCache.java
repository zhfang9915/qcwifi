package ltd.qcwifi.resource.mybatis.redis.ecache;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisCache implements Cache {
	
	private Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private static JedisConnectionFactory jedisConnectionFactory;

	private final String id;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private static final int DB_INDEX = 1;
	
    private final String COMMON_CACHE_KEY = "Qcwifi:";
    
    private static final String UTF8 = "utf-8";

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug("MybatisRedisCache:id=" + id);
		this.id = id;
	}

	/**
     * 按照一定规则标识key
     */
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(COMMON_CACHE_KEY);
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }
    
	/**
     * redis key规则前缀
     */
    private String getKeys() {
    	logger.debug(COMMON_CACHE_KEY + this.id + ":*");
        return COMMON_CACHE_KEY + this.id + ":*";
    }
    
	@Override
	public void clear() {
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.select(DB_INDEX);
            // 如果有删除操作，会影响到整个表中的数据，因此要清空一个mapper的缓存（一个mapper的不同数据操作对应不同的key）
            Set<byte[]> keys = connection.keys(getKeys().getBytes(UTF8));
            logger.debug("出现CUD操作，清空对应Mapper缓存======>" + keys.size());
            for (byte[] key : keys) {
            	connection.del(key);
            }
//			connection.flushDb();
//			connection.flushAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Object getObject(Object key) {
		Object result = null;
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.select(DB_INDEX);
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = serializer.deserialize(connection.get(getKey(key).getBytes(UTF8)));
			logger.debug("缓存中获取");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	@Override
	public int getSize() {
		int result = 0;
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.select(DB_INDEX);
            Set<byte[]> keys = connection.keys(getKeys().getBytes(UTF8));
            if (null != keys && !keys.isEmpty()) {
                result = keys.size();
            }
            logger.debug(this.id + "总缓存数为：" + result);
//			result = Integer.valueOf(connection.dbSize().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	@Override
	public void putObject(Object key, Object value) {
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			connection.select(DB_INDEX);

            byte[] keys = getKey(key).getBytes(UTF8);
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            connection.set(keys, serializer.serialize(value));
            logger.debug("添加缓存--------" + this.id);
//			connection.set(serializer.serialize(key), serializer.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public Object removeObject(Object key) {
		JedisConnection connection = null;
		Object result = null;
		try {
			connection = jedisConnectionFactory.getConnection();
//			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
//			result = connection.expire(serializer.serialize(key), 0);
			connection.select(DB_INDEX);
			result = connection.del(getKey(key).getBytes(UTF8));
            logger.debug("LRU算法从缓存中移除-----" + this.id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.jedisConnectionFactory = jedisConnectionFactory;
	}

}

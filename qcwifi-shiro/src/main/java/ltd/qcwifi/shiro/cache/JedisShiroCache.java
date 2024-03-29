package ltd.qcwifi.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qcwifi.shiro.util.SerializeUtil;


/**
 * 缓存获取 Manager
 * @author 张芳
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class JedisShiroCache<K, V> implements Cache<K, V> {
	
	Logger logger = LoggerFactory.getLogger(JedisShiroCache.class);

	/**
	 * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
	 */
    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    /**
     * Redis 分片(分区)，也可以在配置文件中配置
     */
    private static final int DB_INDEX = 4;

    private JedisManager jedisManager;
    
    private String name;

    
	@SuppressWarnings("rawtypes")
	static final Class<JedisShiroCache> SELF = JedisShiroCache.class;
    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public V get(K key) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
            logger.debug("get value by cache throw exception:{}", e.getMessage());
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
        	 logger.debug("put cache throw exception:{}", e.getMessage());
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)));
        } catch (Exception e) {
            logger.debug("remove cache throw exception:{}", e.getMessage());
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        //TODO--
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        //TODO
        return null;
    }

    @Override
    public Collection<V> values() {
        //TODO
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

}

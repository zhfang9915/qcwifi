package ltd.qcwifi.shiro.cache.impl;

import org.apache.shiro.cache.Cache;

import ltd.qcwifi.shiro.cache.JedisManager;
import ltd.qcwifi.shiro.cache.JedisShiroCache;
import ltd.qcwifi.shiro.cache.ShiroCacheManager;


/**
 * Jedis 管理shiro cache
 * @author 张芳
 *
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    @Override
    public void destroy() {
    	//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}

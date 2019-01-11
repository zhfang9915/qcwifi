package ltd.qcwifi.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedisConnection {

	
	@Test
	public void testConnection() {
		Jedis j = new Jedis("192.168.137.154", 6379);
		
		j.connect();
		j.auth("p@ssw0rd");
		
		System.out.println(j.ping());
		
		j.close();
	}
}

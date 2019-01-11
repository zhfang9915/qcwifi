package ltd.qcwifi.util;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {

	@Test
	public void testConnection() {
		Jedis j = new Jedis("192.168.137.129", 6399);
		j.auth("p@ssw0rd");
		
		System.out.println(j.ping());
		
		j.close();
	}
}

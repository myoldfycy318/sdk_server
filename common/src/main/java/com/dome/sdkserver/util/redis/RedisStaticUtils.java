package com.dome.sdkserver.util.redis;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.dome.sdkserver.common.Constants;

import redis.clients.jedis.JedisPoolConfig;

public class RedisStaticUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisStaticUtils.class);
	
	
	public static RedisTemplate<String, ?> createRedisTemplate(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		RedisConfig redisConfig=new RedisConfig();
		poolConfig.setMaxTotal(redisConfig.getMaxTotal());
		poolConfig.setMaxIdle(redisConfig.getMaxIdle());
		poolConfig.setTestOnBorrow(redisConfig.isTestOnBorrow());
		
		
		JedisConnectionFactory connectionFactory= new JedisConnectionFactory(poolConfig);
		connectionFactory.setHostName(redisConfig.getIp());
		connectionFactory.setPort(redisConfig.getPort());
		// 会报shardInfo为null
		connectionFactory.afterPropertiesSet();
		RedisTemplate<String, ?> redisTemplate=new StringRedisTemplate(connectionFactory);
		return redisTemplate;
	}
	
	@Autowired
	private RedisTemplate redisTemplate;
	public String get(String key){
		if (redisTemplate==null){
			redisTemplate= createRedisTemplate();
		}
		
		Object value = redisTemplate.opsForValue().get(key);
		return value!=null?value.toString():"";
	}
	/**
	 * 
	 * redis.properties文件内容
	 * redis.ip=192.168.130.132
		redis.port=6379
		redis.maxTotal=5
		redis.maxIdle=5
		redis.testOnBorrow=true
	 * @author lilongwei
	 *
	 */
	static class RedisConfig{
		private static Properties props=new Properties();
		static {
			InputStream is=null;
			try {
				is =RedisConfig.class.getResourceAsStream("/redis.properties");
				props.load(is);
			} catch (Exception e) {
				logger.error("load redis.properties config file failed.");
			} finally {
				IOUtils.closeQuietly(is);
			}
			
		}
		
		public String getIp(){
			return props.getProperty("redis.ip");
		}
		
		public int getPort(){
			String portStr =props.getProperty("redis.port");
			if (StringUtils.isEmpty(portStr)
					|| !Constants.PATTERN_NUM.matcher(portStr).matches()){
				return 6379;
			}
			return Integer.parseInt(portStr);
		}
		
		public int getMaxTotal(){
			String maxTotalStr =props.getProperty("redis.maxTotal");
			return Integer.parseInt(maxTotalStr);
		}
		
		public int getMaxIdle(){
			String maxIdleStr =props.getProperty("redis.maxIdle");
			return Integer.parseInt(maxIdleStr);
		}
		
		public boolean isTestOnBorrow(){
			String testOnBorrowStr =props.getProperty("redis.testOnBorrow");
			return "true".equalsIgnoreCase(testOnBorrowStr);
		}
	}
}

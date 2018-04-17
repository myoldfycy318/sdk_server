package com.dome.sdkserver.util.redis;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisStaticUtilsTest {

	private RedisTemplate<String, ?> redisTemplate=RedisStaticUtils.createRedisTemplate();
	@Test
	public void test() {
		
		System.out.println("redis template object: " +redisTemplate);
		Set<String> keySet=redisTemplate.keys("*");
		if (keySet!=null){
			System.out.println("redis key set:" +keySet.toString());
		}
		
		String appTypeRedisVal=redisTemplate.opsForValue().get("domesdk:apptype:appTypeCode10308000")+"";
		System.out.println("redis value: " +appTypeRedisVal);
	}

	@Test
	public void testGet() {
		RedisStaticUtils redisUtil=new RedisStaticUtils();
		String key="domesdk:data:dict:APPROVE_RESULT_NOTIFY";
		String val = redisUtil.get(key);
		System.out.println("redis value: " +val);
	}
}

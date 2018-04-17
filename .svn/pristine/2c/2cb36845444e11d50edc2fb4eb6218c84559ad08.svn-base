package com.dome.sdkserver.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis工具类
 * 
 * @author
 */
public class RedisUtil {

	private static Log log = LogFactory.getLog(RedisUtil.class);

    /**
     * redis hash 已经发送的宝券的用户
     */
    public static final String REDIS_HASH_KEY_HAS_SEND_BQ_USER= "QbSdkServer:HasSendBqUser";

	private JedisPool pool;

	/**
	 * redis的List集合 ，向key这个list添加元素
	 * 
	 * @param key
	 *            List别名
	 * @param string
	 *            元素
	 * @return
	 */
	/*
	 * public long rpush(String key, String string) { try { shardedJedis =
	 * pool.getResource(); long ret = shardedJedis.rpush(key, string);
	 * pool.returnResource(shardedJedis); return ret; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 获取key这个List，从第几个元素到第几个元素 LRANGE key start
	 * stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 *            List别名
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下标
	 * @return
	 */
	/*
	 * public List<String> lrange(String key, long start, long end) { try {
	 * shardedJedis = pool.getResource(); List<String> ret =
	 * shardedJedis.lrange(key, start, end); pool.returnResource(shardedJedis);
	 * return ret; } catch (Exception e) { e.printStackTrace(); if (shardedJedis
	 * != null) { pool.returnBrokenResource(shardedJedis); } throw new
	 * JedisException(e); } }
	 */

	public void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);

		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	
	/**
     * 把对象放入Hash中
     */
    public void hset(String key, String field, String value, int seconds) {
        this.hset(key, field, value);
        this.expire(key, seconds);
    }

	public long hincrBy(String key, String field, long value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);

		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public void del(String... key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public void hdel(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * 向key赋值
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);

		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.get(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public byte[] getBytes(byte[] key) { try { shardedJedis =
	 * pool.getResource(); byte[] value = shardedJedis.get(key);
	 * pool.returnResource(shardedJedis); return value; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 */
	public void hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hmset(key, map);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public void sset(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.sadd(key, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public Set<String> sget(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.smembers(key);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public long sdel(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmget(key, fields);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */

	public void setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */

	public Long setnx(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Long nx = jedis.setnx(key, value);
			jedis.expire(key, seconds);
			return nx;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 */
	public void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zscore(key, member);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrank(key, member);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(int dbIndex, String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrank(key, member);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Long zrevrank(int idx, String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zrevrank(key, member);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Set<Tuple> zrangeWithScores(int idx, String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Set<Tuple> zrevrangeWithScores(int idx, String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(int idx, String key, double max, double min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Set<Tuple> zrangeByScoreWithScores(int idx, String key, double min, double max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Long zcard(int idx, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zcard(key);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Double zscore(int idx, String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zscore(key, member);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 Sorted-Sets的值
	 *
	 * @param key
	 * @return
	 */
	public Long zcount(int idx, String key, double min, double max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			if (idx != 0) {
				jedis.select(idx);
			}
			return jedis.zcount(key, min, max);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 List的值
	 * 
	 * @param key
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取 List的值
	 * 
	 * @param key
	 * @return
	 */
	public List<String> lrange(int dbIndex, String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.select(dbIndex);
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	
	/**
     * 从哈希表key中获取field的value
     *
     * @param key
     */

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            return map;
        } catch (Exception e) {
            if (jedis != null) {
                this.pool.returnBrokenResource(jedis);
            }
            throw new JedisException(e);
        } finally {
            if (this.pool != null) {
                this.pool.returnResource(jedis);
            }
        }
    }

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 */

	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.hget(key, field);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 */
	public String hget(int dbIndex, String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.select(dbIndex);
			String value = jedis.hget(key, field);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	
	/**
     * 从Hash中获取对象,转换成制定类型
     */
    @SuppressWarnings("unchecked")
    public <T> T hget(String key, String field, Type clazz) {
        String jsonContext = this.hget(key, field);
        return (T) JSONObject.parseObject(jsonContext, clazz);
    }

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 */
	/*
	 * public byte[] hgetBytes(byte[] key, byte[] field) { try { shardedJedis =
	 * pool.getResource(); byte[] value = shardedJedis.hget(key, field);
	 * pool.returnResource(shardedJedis); return value; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 返回哈希表key中，所有的域和值
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public Map<String, String> hgetAll(String key) { try { shardedJedis =
	 * pool.getResource(); Map<String, String> map = shardedJedis.hgetAll(key);
	 * pool.returnResource(shardedJedis); return map; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * 
	 * }
	 */

	/**
	 * 返回哈希表key中，所有的域和值
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public Set<?> smembers(String key) { try { shardedJedis =
	 * pool.getResource(); Set<?> set = shardedJedis.smembers(key);
	 * pool.returnResource(shardedJedis); return set; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 移除集合中的member元素
	 * 
	 * @param key
	 *            List别名
	 * @param field
	 *            键
	 */
	/*
	 * public void delSetObj(String key, String field) { try { shardedJedis =
	 * pool.getResource(); shardedJedis.srem(key, field);
	 * pool.returnResource(shardedJedis); } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 判断member元素是否是集合key的成员。是（true），否则（false）
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismembers(String key, String member) {
		Jedis jedis = null;
		boolean res = false;
		try {
			jedis = pool.getResource();
			res = jedis.sismember(key, member);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			log.error("RedisUtil.sismembers(String key, String member) exception {}", e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
		return res;
	}

	/**
	 * 如果key已经存在并且是一个字符串，将value追加到key原来的值之后
	 * 
	 * @param key
	 * @param value
	 */
	/*
	 * public void append(String key, String value) { try { shardedJedis =
	 * pool.getResource(); shardedJedis.append(key, value);
	 * pool.returnResource(shardedJedis); } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */
    
    /**
     * 获取某一个模式的所有key
     * 等价于命令keys *domesdk*
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
    	Jedis jedis = null;
    	Set<String> keys = null;
		try {
			jedis = pool.getResource();
			keys = jedis.keys(pattern);
			
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
		return keys;
    }
    
    /**
     * 删除某一模式的所有key
     * 先获取所有key，再逐一删除
     * @param pattern
     */
    public void delPatternKey (String pattern){
    	Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Set<String> keys = jedis.keys(pattern);
			if (keys != null && !keys.isEmpty()){
				for (String key : keys) {
					jedis.del(key);
				}
			}
			
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
    }

    /**
     * 校验hash table里面的field是否存在
     * @Title: hexists
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param  @param key
     * @param  @param field
     * @return void    返回类型
     * @throws
     */
    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hexists(key, field);
        } catch (Exception e) {
            log.error(e);
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
            throw new JedisException(e);

        } finally {
            if (pool != null) {
                pool.returnResource(jedis);
            }
        }
    }
    
    /**
     * 查询key剩余缓存时间ttl
     * @param key
     * @return
     */
    public long ttl(String key){
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("redis查询key剩余缓存时间ttl出错", e);
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
            throw new JedisException(e);

        } finally {
            if (pool != null) {
                pool.returnResource(jedis);
            }
        }
    }

    /**
     * redis锁定key
     *
     * @param key
     * @param seconds
     * @param value
     * @return true:锁定|false:未锁定
     */
    public boolean tryLock(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long nx = jedis.setnx(key, value);
            if (nx == 1)
                jedis.expire(key, seconds);
            if (nx == 0)
                return true;
            return false;
        } catch (Exception e) {
            log.error(e);
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
            throw new JedisException(e);
        } finally {
            if (pool != null) {
                pool.returnResource(jedis);
            }
        }
    }
}
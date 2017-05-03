package cn.runjen.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisCommands;
import cn.runjen.common.utils.ConvertUtil;

/**
 * 对hash的操作
 * @author runjen
 *
 */
public class HashJedis extends AbstractJedis {

	public HashJedis() {
	}

	public HashJedis(Boolean isCluster) {
		super(isCluster);
	}

	public HashJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	public Long hdel(String key, String... fields) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hdel(key, fields);
		} finally {
			close(jedis);
		}
	}

	public Boolean hexists(String key, String field) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hexists(key, field);
		} finally {
			close(jedis);
		}
	}

	public String setex(String key, int seconds, String value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.setex(key, seconds, value);
		} finally {
			close(jedis);
		}
	}

	public <T> T hget(Class<T> clazz, String key, String field) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.hget(key, field), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> Map<String, T> hgetAll(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanMap(jedis.hgetAll(key), clazz);
		} finally {
			close(jedis);
		}

	}

	public Long hincrBy(String key, String field, long value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hincrBy(key, field, value);
		} finally {
			close(jedis);
		}
	}

	public Set<String> hkeys(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hkeys(key);
		} finally {
			close(jedis);
		}

	}

	public Long hlen(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hlen(key);
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> hmget(Class<T> clazz, String key, String... fields) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz, jedis.hmget(key, fields));
		} finally {
			close(jedis);
		}
	}

	public <T> String hmset(String key, Map<String, T> hash) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hmset(key, ConvertUtil.bean2StringMap(hash));
		} finally {
			close(jedis);
		}
	}

	public Long hset(String key, String field, Object value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hset(key, field, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	public Long hsetnx(String key, String field, Object value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.hsetnx(key, field, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> hvals(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz, jedis.hvals(key));
		} finally {
			close(jedis);
		}
	}

}

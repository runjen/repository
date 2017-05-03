package cn.runjen.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import cn.runjen.common.utils.ConvertUtil;

public class KeyJedis extends AbstractJedis {

	public KeyJedis() {
		super();
	}

	public KeyJedis(Boolean isCluster) {
		super(isCluster);
	}

	public KeyJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	public Long del(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.del(key);
		} finally {
			close(jedis);
		}
	}

	public Boolean exists(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.exists(key);
		} finally {
			close(jedis);
		}
	}

	public Long expire(String key, int seconds) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.expire(key, seconds);
		} finally {
			close(jedis);
		}
	}

	public Long expireAt(String key, long unixTime) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.expireAt(key, unixTime);
		} finally {
			close(jedis);
		}
	}

	public Long persist(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.persist(key);
		} finally {
			close(jedis);
		}
	}

	public Long pexpire(String key, long milliseconds) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.pexpire(key, milliseconds);
		} finally {
			close(jedis);
		}
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.pexpireAt(key, millisecondsTimestamp);
		} finally {
			close(jedis);
		}
	}

	public Long ttl(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.ttl(key);
		} finally {
			close(jedis);
		}
	}

	public String type(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.type(key);
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> sort(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz, jedis.sort(key));
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> sort(Class<T> clazz, String key,
			SortingParams sortingParameters) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz,
					jedis.sort(key, sortingParameters));
		} finally {
			close(jedis);
		}
	}

	public Long del(String... keys) {
		JedisCommands jedis = getJedis();
		try {
			long count = 0;
			for (String key : keys) {
				count += jedis.del(key);
			}
			return count;
		} finally {
			close(jedis);
		}
	}

	public Set<String> keys(String pattern) {
		JedisCommands jedis = getJedis();
		if (jedis instanceof Jedis) {
			try {
				return ((Jedis) jedis).keys(pattern);
			} finally {
				close(jedis);
			}
		} else if (jedis instanceof JedisCluster) {
			Set<String> keys = new TreeSet<String>();
			Map<String, JedisPool> clusterNodes = ((JedisCluster) jedis)
					.getClusterNodes();
			for (String k : clusterNodes.keySet()) {
				JedisPool jp = clusterNodes.get(k);
				Jedis connection = jp.getResource();
				try {
					keys.addAll(connection.keys(pattern));
				} catch (Exception e) {
				} finally {
					connection.close();// 用完一定要close这个链接！！！
				}
			}
			return keys;
		}
		return null;
	}

}

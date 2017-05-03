package cn.runjen.common.redis;

import java.util.Set;

import redis.clients.jedis.JedisCommands;
import cn.runjen.common.utils.ConvertUtil;

public class SortedSetJedis extends AbstractJedis {

	public SortedSetJedis() {
		super();
	}

	public SortedSetJedis(Boolean isCluster) {
		super(isCluster);
	}

	public SortedSetJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	public Long zadd(String key, double score, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zadd(key, score, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}
	}

	public Long zcard(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zcard(key);
		} finally {
			close(jedis);
		}
	}

	public Long zcount(String key, double min, double max) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zcount(key, min, max);
		} finally {
			close(jedis);
		}
	}

	public double zincrby(String key, double score, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zincrby(key, score, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}
	}

	public <T> Set<T> zrange(Class<T> clazz, String key, long start, long end) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2ObjSet(jedis.zrange(key, start, end),
					clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> Set<T> zrangeByScore(Class<T> clazz, String key, double min,
			double max) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2ObjSet(
					jedis.zrangeByScore(key, min, max), clazz);
		} finally {
			close(jedis);
		}
	}

	public Long zrank(String key, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zrank(key, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}
	}

	public <T> Long zrem(String key, T... members) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zrem(key, ConvertUtil.bean2StringArray(members));
		} finally {
			close(jedis);
		}
	}

	public Long zremrangeByRank(String key, long start, long end) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zremrangeByRank(key, start, end);
		} finally {
			close(jedis);
		}
	}

	public Long zremrangeByScore(String key, double start, double end) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			close(jedis);
		}
	}

	public <T> Set<T> zrevrange(Class<T> clazz, String key, long start, long end) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2ObjSet(jedis.zrevrange(key, start, end),
					clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> Set<T> zrevrangeByScore(Class<T> clazz, String key, double max,
			double min) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2ObjSet(
					jedis.zrevrangeByScore(key, max, min), clazz);
		} finally {
			close(jedis);
		}
	}

	public Long zrevrank(String key, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zrevrank(key, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}

	}

	public double zscore(String key, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.zscore(key, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}
	}
}

package cn.runjen.common.redis;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.JedisCommands;
import cn.runjen.common.utils.ConvertUtil;

public class SetJedis extends AbstractJedis {

	public SetJedis() {
		super();
	}

	public SetJedis(Boolean isCluster) {
		super(isCluster);
	}

	public SetJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	public <T> Long sadd(String key, T... members) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.sadd(key, ConvertUtil.bean2StringArray(members));
		} finally {
			close(jedis);
		}
	}

	public Long scard(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.scard(key);
		} finally {
			close(jedis);
		}
	}

	public boolean sismember(String key, Object member) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.sismember(key, ConvertUtil.bean2String(member));
		} finally {
			close(jedis);
		}
	}

	public <T> Set<T> smembers(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2ObjSet(jedis.smembers(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> T spop(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.spop(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> srandmember(Class<T> clazz, String key, int count) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz,
					jedis.srandmember(key, count));
		} finally {
			close(jedis);
		}
	}

	public <T> T srandmember(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.srandmember(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> Long srem(String key, T... members) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.srem(key, ConvertUtil.bean2StringArray(members));
		} finally {
			close(jedis);
		}
	}

}

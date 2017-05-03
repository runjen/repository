package cn.runjen.common.redis;

import java.util.List;

import redis.clients.jedis.JedisCommands;
import cn.runjen.common.utils.ConvertUtil;

public class ListJedis extends AbstractJedis {

	public ListJedis() {
		super();
	}

	public ListJedis(Boolean isCluster) {
		super(isCluster);
	}

	public ListJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	public <T> T lindex(String key, long index, Class<T> clazz) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.lindex(key, index), clazz);
		} finally {
			close(jedis);
		}
	}

	public Long llen(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.llen(key);
		} finally {
			close(jedis);
		}
	}

	public <T> T lpop(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.lpop(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> T rpop(Class<T> clazz, String key) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.rpop(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> Long lpush(String key, T... obj) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.lpush(key, ConvertUtil.bean2StringArray(obj));
		} finally {
			close(jedis);
		}
	}

	public <T> Long lpushx(String key, T... obj) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.lpushx(key, ConvertUtil.bean2StringArray(obj));
		} finally {
			close(jedis);
		}
	}

	public <T> Long rpush(String key, T... obj) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.rpush(key, ConvertUtil.bean2StringArray(obj));
		} finally {
			close(jedis);
		}
	}

	public <T> Long rpushx(String key, T... obj) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.rpushx(key, ConvertUtil.bean2StringArray(obj));
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> lrange(String key, long start, long end, Class<T> calzz) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(calzz,
					jedis.lrange(key, start, end));
		} finally {
			close(jedis);
		}
	}

	public Long lrem(String key, long count, Object value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.lrem(key, count, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	public String lset(String key, long index, Object value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.lset(key, index, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	public String ltrim(String key, long start, long end) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.ltrim(key, start, end);
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> brpop(Class<T> clazz, String key, int time) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz, jedis.brpop(time, key));
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> blpop(Class<T> clazz, String key, int time) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2BeanList(clazz, jedis.blpop(time, key));
		} finally {
			close(jedis);
		}
	}

}

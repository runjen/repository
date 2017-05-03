package cn.runjen.common.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import cn.runjen.common.utils.ConvertUtil;

public class StringJedis extends AbstractJedis {

	public StringJedis() {
		super();
	}

	public StringJedis(Boolean isCluster) {
		super(isCluster);
	}

	public StringJedis(String key, Boolean isCluster) {
		super(key, isCluster);
	}

	/**
	 * 设置一个Object的缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void setObject(String key, Object value) {
		JedisCommands jedis = getJedis();
		try {
			jedis.set(key, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置一个Object的缓存,并且有超期时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void setObject(String key, Object value, Integer seconds) {
		JedisCommands jedis = getJedis();
		try {
			jedis.set(key, ConvertUtil.bean2String(value));
			jedis.expire(key, seconds);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置二进制数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setByte(byte[] key, byte[] value, Integer seconds) {
		JedisCommands jedis = getJedis();
		try {
			((Jedis) jedis).set(key, value);
			((Jedis) jedis).expire(key, seconds);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置二进制数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setByte(byte[] key, byte[] value) {
		JedisCommands jedis = getJedis();
		try {
			((Jedis) jedis).set(key, value);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获得二进制数据
	 * 
	 * @param key
	 * @return
	 */
	public byte[] getByte(byte[] key) {
		JedisCommands jedis = getJedis();
		try {
			return ((Jedis) jedis).get(key);
		} finally {
			close(jedis);
		}
	}

	public void set(String key, String value) {
		JedisCommands jedis = getJedis();
		try {
			jedis.set(key, value);
		} finally {
			close(jedis);
		}
	}

	public void set(String key, String value, Integer seconds) {
		JedisCommands jedis = getJedis();
		try {
			jedis.set(key, value);
			jedis.expire(key, seconds);
		} finally {
			close(jedis);
		}
	}

	public String get(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			close(jedis);
		}
	}

	public <T> T getObject(String key, Class<T> clazz) {
		JedisCommands jedis = getJedis();
		try {
			return ConvertUtil.string2Bean(jedis.get(key), clazz);
		} finally {
			close(jedis);
		}
	}

	public <T> List<T> getListObject(String key, Class<T> clazz) {
		JedisCommands jedis = getJedis();
		try {
			String string = jedis.get(key);
			return ConvertUtil.string2List(string, clazz);
		} finally {
			close(jedis);
		}
	}

	public Long append(String key, String value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.append(key, value);
		} finally {
			close(jedis);
		}
	}

	public Long bitcount(String key, long start, long end) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.bitcount(key, start, end);
		} finally {
			close(jedis);
		}
	}

	public Long decr(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.decr(key);
		} finally {
			close(jedis);
		}
	}

	public Long decrBy(String key, long integer) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.decrBy(key, integer);
		} finally {
			close(jedis);
		}
	}

	public boolean getbit(String key, long offset) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.getbit(key, offset);
		} finally {
			close(jedis);
		}
	}

	public String getrange(String key, long startOffset, long endOffset) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.getrange(key, startOffset, endOffset);
		} finally {
			close(jedis);
		}
	}

	public String getSet(String key, Object value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.getSet(key, ConvertUtil.bean2String(value));
		} finally {
			close(jedis);
		}
	}

	public Long incr(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.incr(key);
		} finally {
			close(jedis);
		}
	}

	public Long incrBy(String key, long integer) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.incrBy(key, integer);
		} finally {
			close(jedis);
		}
	}

	public double incrByFloat(String key, double value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.incrByFloat(key, value);
		} finally {
			close(jedis);
		}
	}

	public boolean setbit(String key, long offset, boolean value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.setbit(key, offset, value);
		} finally {
			close(jedis);
		}
	}

	public boolean setbit(String key, long offset, String value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.setbit(key, offset, value);
		} finally {
			close(jedis);
		}
	}

	public Long setnx(String key, String value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.setnx(key, value);
		} finally {
			close(jedis);
		}
	}

	public Long setrange(String key, long offset, String value) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.setrange(key, offset, value);
		} finally {
			close(jedis);
		}
	}

	public Long strlen(String key) {
		JedisCommands jedis = getJedis();
		try {
			return jedis.strlen(key);
		} finally {
			close(jedis);
		}
	}

	public List<String> mget(String... keys) {
		JedisCommands jedis = getJedis();
		try {
			List<String> retList = new ArrayList<String>();
			for (String key : keys) {
				retList.add(jedis.get(key));
			}
			return retList;
		} finally {
			close(jedis);
		}
	}

	public void mset(String... keysvalues) {
		JedisCommands jedis = getJedis();
		try {
			for (int i = 0; i < keysvalues.length; i += 2) {
				jedis.set(keysvalues[i], keysvalues[i + 1]);
			}
		} finally {
			close(jedis);
		}
	}

}

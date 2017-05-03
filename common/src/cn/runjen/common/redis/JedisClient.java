package cn.runjen.common.redis;


import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * jedis实例,入口类
 * @author runjen
 * JedisClient jc = new JedisClient(Boolean.TRUE);
 * KeyJedis keyJedis=jc.getKeyJedis();
 * keyJedis.del("testKey");
 * 或者
 * KeyJedis keyJedis=new KeyJedis(Boolean.TRUE);
 * keyJedis.del("testKey");
 */
public class JedisClient {
	//是否集群环境
	private Boolean isCluster;
	
	private String key;
	
	public JedisClient() {
		this.isCluster=Boolean.TRUE;//默认是集群环境,false为单机版环境
	}

	public JedisClient(Boolean isCluster) {
		this.isCluster=isCluster;
	}
	
	public JedisClient(String key,JedisCluster jedisCluster) {
		this.key=key;
		this.isCluster=Boolean.TRUE;
		ResourcePool.registerJedisCluster(key, jedisCluster);
	}
	public JedisClient(String key,JedisPool jedisPool) {
		this.key=key;
		this.isCluster=Boolean.FALSE;
		ResourcePool.registerResourcePool(key, jedisPool);
	}

	public Boolean getIsCluster() {
		return isCluster;
	}

	public void setIsCluster(Boolean isCluster) {
		this.isCluster = isCluster;
	}
	
	public HashJedis getHashJedis() {
		HashJedis hashJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			hashJedis=new HashJedis(this.key,this.isCluster);
		}else{
			hashJedis=new HashJedis(isCluster);
		}
		return (HashJedis) new JedisInterceptor().newHashJedisInstance(hashJedis);
	}

	public KeyJedis getKeyJedis() {
		KeyJedis keyJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			keyJedis=new KeyJedis(this.key,this.isCluster);
		}else{
			keyJedis=new KeyJedis(isCluster);
		}
		return (KeyJedis) new JedisInterceptor().newKeyJedisInstance(keyJedis);
	}

	public ListJedis getListJedis() {
		ListJedis listJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			listJedis=new ListJedis(this.key,this.isCluster);
		}else{
			listJedis=new ListJedis(isCluster);
		}
		return (ListJedis) new JedisInterceptor().newListJedisInstance(listJedis);
	}

	public SetJedis getSetJedis() {
		SetJedis setJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			setJedis=new SetJedis(this.key,this.isCluster);
		}else{
			setJedis=new SetJedis(isCluster);
		}
		return (SetJedis) new JedisInterceptor().newSetJedisInstance(setJedis);
	}

	public SortedSetJedis getSortedSetJedis() {
		SortedSetJedis sortedSetJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			sortedSetJedis=new SortedSetJedis(this.key,this.isCluster);
		}else{
			sortedSetJedis=new SortedSetJedis(isCluster);
		}
		return (SortedSetJedis) new JedisInterceptor().newSortedSetJedisInstance(sortedSetJedis);
	}

	public StringJedis getStringJedis() {
		StringJedis stringJedis=null;
		if(StringUtils.isNotBlank(this.key)){
			stringJedis=new StringJedis(this.key,this.isCluster);
		}else{
			stringJedis=new StringJedis(isCluster);
		}
		return (StringJedis) new JedisInterceptor().newStringJedisInstance(stringJedis);
	}
}

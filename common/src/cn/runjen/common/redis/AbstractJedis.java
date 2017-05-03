package cn.runjen.common.redis;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

/**
 * 
 * @author runjen
 *
 */
public abstract class AbstractJedis {
	
	//是否集群环境
    protected Boolean isCluster;
    
    //对应的key
    protected String key;
    
    public AbstractJedis(){};
    
    public AbstractJedis(Boolean isCluster) {
    	this.isCluster=isCluster;
    }

    public AbstractJedis(String key,Boolean isCluster) {
    	this.isCluster=isCluster;
    	this.key=key;
    }
    
    /**
     * 获得对应key的clusterJedis实例
     * @return
     */
    public JedisCluster getClusterJedis(){
		if(StringUtils.isNotBlank(this.key)){
			return ResourcePool.getJedisCluster(this.key);
		}else{
			return ResourcePool.getDefaultJedisCluster();
		}
    }
    /**
     * 获得对应key的jedis实例
     * @return
     */
    public Jedis getSingleJedis(){
		if(StringUtils.isNotBlank(this.key)){
			return ResourcePool.getResourcePool(key).getResource();
		}else{
			return ResourcePool.getDefaultResourcePool().getResource();
		}
    }
    /**
     * 获得一个jedis连接
     * @return
     */
    public JedisCommands getJedis(){
    	if(this.isCluster){
    		return getClusterJedis();
    	}else{
    		return getSingleJedis();
    	}
    }
    /**
     * 关闭jedis连接
     * @param jedis
     */
    public void close(JedisCommands jedis){
		if(jedis!=null && !this.isCluster && jedis instanceof Jedis){
			((Jedis)jedis).close();
		}
    }
    
}

package cn.runjen.common.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.runjen.common.utils.Common;

public class ResourcePool {
	
	
    /**
     * 客户端连接超时时间
     */
    private final static int TIME_OUT = 30000;
    
    /**
     * 最大重试次数
     */
    private final static int MAX_REDIRECTIONS = 5;

    /**
     * 最大等待时间
     */
    private final static int MAX_WAIT = 1000000;
    
    /**
     * 最大连接数
     */
    private final static int MAX_ACTIVE = 500;

    /**
     * 最大空闲连接数
     */
    private final static int MAX_IDLE = 200;
    
    /**
     * 最小空闲连接数
     */
    private final int MIN_IDLE = 8;
    
    private final static Boolean TEST_ON_BORROW=true;
    
    private static final String REDIS_IP_PORT="redis.ip.port";
    
    private static final String DEFAULT_CLUSTER="cluster";
    
    private static final String DEFAULT_SINGLE="single";
    
    private static final String PASSWORD="redis.pwd";
    
    

    /**
     * 连接池配置对象
     */
    private final static GenericObjectPoolConfig POOL_CONFIG = new GenericObjectPoolConfig();
    {
    	POOL_CONFIG.setMaxTotal(MAX_ACTIVE);
    	POOL_CONFIG.setMaxIdle(MAX_IDLE);
    	POOL_CONFIG.setMinIdle(MIN_IDLE);
    	POOL_CONFIG.setMaxWaitMillis(MAX_WAIT);
    }

	
	private static class Inner {
		private static ResourcePool instance = new ResourcePool();
	}
	

	private ResourcePool() {
	}

	public static ResourcePool getInstance() {
		return Inner.instance;
	}
	
	private ConcurrentMap<String, Object> pool = new ConcurrentHashMap<String, Object>();
	

    private static String[] getIpPortsArr(){
        String ipPorts = Common.getConfig(REDIS_IP_PORT);
        return ipPorts.split(";");
    } 
    private static String getIp(String ipAndPortArr){
    	return ipAndPortArr.split(":")[0];
    }
    private static Integer getPort(String ipAndPortArr){
    	return Integer.valueOf(ipAndPortArr.split(":")[1]);
    }
    /**
     * 获得实例
     * @return
     */
    private static Set<HostAndPort> getHostAndPort(){
        Set<HostAndPort> params = new HashSet<HostAndPort>();
        for (String ipAndPortArr : getIpPortsArr()) {
			HostAndPort hostAndPort = new HostAndPort(getIp(ipAndPortArr),getPort(ipAndPortArr));
			params.add(hostAndPort);
		}
        return params;
    }
    /**
     * 初始化默认JedisCluster
     */
	private static JedisCluster initDefatultCluster(){
		String password = Common.getConfig(PASSWORD);
		Set<HostAndPort> params = getHostAndPort();
		JedisCluster jedisCluster = null;
		if(StringUtils.isNotBlank(password)){
			jedisCluster = new JedisCluster(params, TIME_OUT, TIME_OUT, 1, password, POOL_CONFIG);
		}else{
			jedisCluster = new JedisCluster(params,TIME_OUT,MAX_REDIRECTIONS,POOL_CONFIG);
		}
        ResourcePool put = Inner.instance;
        put.pool.put(DEFAULT_CLUSTER, jedisCluster);
        return jedisCluster;
	}
	
	/**
	 * 初始化默认JedisPool
	 */
	private static JedisPool initDefatultSingle(){
		String password = Common.getConfig(PASSWORD);
		String ipAndPort = getIpPortsArr()[0];
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		JedisPool jedisPool=null;
		if(StringUtils.isBlank(password)){
			jedisPool = new JedisPool(config,getIp(ipAndPort),getPort(ipAndPort),TIME_OUT);
		}else{
			jedisPool = new JedisPool(config,getIp(ipAndPort),getPort(ipAndPort),TIME_OUT,password);
		}
        Inner.instance.pool.put(DEFAULT_SINGLE, jedisPool);
        return jedisPool;
	}

	/**
	 * 获得默认的JedisCluster
	 * 
	 * @return
	 */
	public static JedisCluster getDefaultJedisCluster() {
		JedisCluster jc=(JedisCluster) Inner.instance.pool.get(DEFAULT_CLUSTER);
		if(jc==null){
			initDefatultCluster();
		}
		return (JedisCluster) Inner.instance.pool.get(DEFAULT_CLUSTER);
	}

	/**
	 * 获得默认的ResourcePool
	 * 
	 * @return
	 */
	public static JedisPool getDefaultResourcePool() {
		JedisPool jp=(JedisPool) Inner.instance.pool.get(DEFAULT_SINGLE);
		if(jp==null){
			initDefatultSingle();
		}
		return (JedisPool) Inner.instance.pool.get(DEFAULT_SINGLE);
	}

	/**
	 * 注册新的JedisCluster
	 * 
	 * @return
	 */
	public static void registerJedisCluster(String key,
			JedisCluster cluster) {
		Inner.instance.pool.put(key, cluster);
	}

	/**
	 * 注册新JedisCluster
	 * 
	 * @return
	 */
	public static void registerResourcePool(String key, JedisPool pool) {
		Inner.instance.pool.put(key, pool);
	}

	/**
	 * 根据注册key获得JedisCluster
	 * 
	 * @return
	 */
	public static JedisCluster getJedisCluster(String key) {
		return (JedisCluster) Inner.instance.pool.get(key);
	}

	/**
	 * 根据注册key获得ResourcePool
	 * 
	 * @return
	 */
	public static JedisPool getResourcePool(String key) {
		return (JedisPool) Inner.instance.pool.get(key);
	}
	
}

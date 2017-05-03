package cn.runjen.common.redis;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.runjen.common.utils.ConvertUtil;
/**
 * jedis拦截
 * @author runjen
 *
 */
public class JedisInterceptor implements MethodInterceptor {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(JedisInterceptor.class);

    private Object target;

    public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    	Object obj=null;
    	try {
    		obj = proxy.invoke(target, args);
    		StringBuffer sb = new StringBuffer();
    		if(obj!=null){
    			for (Object object : args) {
    				if(object instanceof Class){
    					continue;
    				}
    				sb.append(ConvertUtil.bean2String(object));
    				break;
    			}
    			LOGGER.debug("######【{}】命中缓存######",sb.toString());
    		}
		} catch (Exception e) {
			LOGGER.error("######执行redis异常######"+e.getMessage());
		} 
        return obj;
    }


	public SortedSetJedis newSortedSetJedisInstance(SortedSetJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SortedSetJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (SortedSetJedis) enhancer.create(); // 创建代理对象
	}


	public StringJedis newStringJedisInstance(StringJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(StringJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (StringJedis) enhancer.create(); // 创建代理对象
	}


	public SetJedis newSetJedisInstance(SetJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SetJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (SetJedis) enhancer.create(); // 创建代理对象
	}


	public ListJedis newListJedisInstance(ListJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ListJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (ListJedis) enhancer.create(); // 创建代理对象
	}


	public KeyJedis newKeyJedisInstance(KeyJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(KeyJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (KeyJedis) enhancer.create(); // 创建代理对象
	}


	public HashJedis newHashJedisInstance(HashJedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HashJedis.class);
        enhancer.setCallback(this);// 回调方法
        return (HashJedis) enhancer.create(); // 创建代理对象
	}

}

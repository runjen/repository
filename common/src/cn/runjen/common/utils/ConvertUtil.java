package cn.runjen.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertUtil {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> string2List(String str, Class<T> beanType) {
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(str, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public static String bean2String(Object obj) {
    	try {
    		if(obj==null){
    			return "";
    		}
			String string = MAPPER.writeValueAsString(obj);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static <T> T string2Bean (String str,Class<T> clazz){
        try {
        	if(StringUtils.isBlank(str)){
        		return null;
        	}
            T t = MAPPER.readValue(str, clazz);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @param <T>
     * @param clazz
     * @param values
     * @return
     */
    public static <T> List<T> string2BeanList(Class<T> clazz, List<String> values) {
        List<T> returnValues = new ArrayList<T>();
        for(String value : values){
            returnValues.add(ConvertUtil.string2Bean(value, clazz));
        }
        return returnValues;
    }
    /**
     * @param <T>
     * @param smembers
     * @return
     */
    public static <T> Set<T> string2ObjSet(Set<String> smembers,Class<T> clazz) {
        Set<T> returnSet = new HashSet<T>();
        for (Iterator<String> iterator = smembers.iterator(); iterator.hasNext();) {
            String vaule = iterator.next();
            returnSet.add(string2Bean(vaule,clazz));
        }
        return returnSet;
    }
    
    
    /**
     * @param values
     * @return
     */
    public static String[] bean2StringArray(Object... values) {
        String[] returnValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            returnValues[i] = bean2String(values[i]);
        }
        return returnValues;
    }

    /**
     * @param <T>
     * @param hash
     * @return
     */
    public static <T> Map<String, String> bean2StringMap(Map<String, T> hash) {
        
        Map<String, String> returnMap = new HashMap<String, String>();
        for (Iterator<String> iterator = hash.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Object value = hash.get(key);
            if(value instanceof String){
                returnMap.put(key, (String) value);
            }else {
                returnMap.put(key, bean2String(value));
            }
        }
        return returnMap;
    }

    /**
     * @param <T>
     * @param values
     * @return
     */
    public static <T> Map<String, T> string2BeanMap(Map<String, String> m ,Class<T> clazz) {
        Map<String, T> returnMap = new HashMap<String, T>();
        for (Iterator<String> iterator = m.keySet().iterator(); iterator.hasNext();) {
            String kValue = iterator.next();
            returnMap.put(kValue, string2Bean(m.get(kValue),clazz));
        }
        return returnMap;
    }

}

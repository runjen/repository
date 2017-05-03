package cn.runjen.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {
	private static final Logger LOGGER = LoggerFactory.getLogger(Common.class);
	
    private static final String CONFIG_LOCATION = "props/config.properties";
    /**
     * 获得配置文件属性对应的值
     * @param key
     * @return
     */
    public static String getConfig(String key) {
    	InputStream is = Common.class.getClassLoader().getResourceAsStream(CONFIG_LOCATION);
    	Properties prop = new Properties();
    	try {
			prop.load(is);
			return prop.getProperty(key);
		} catch (IOException e) {
			LOGGER.error("######读取配置文件失败######,{}",e);
		} finally {
			IOUtils.closeQuietly(is);
		}
    	return null;
    }
    
	/**
	 * 获得md5
	 * @param resource
	 * @return
	 */
    public static String MD5(String resource) {
        return DigestUtils.md5Hex(resource);
    }

	/**
	 * 获取16进制随机数
	 * 
	 * @param len
	 * @return
	 * @throws CoderException
	 */
	public static String randomHexString(int len) {
		try {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < len; i++) {
				result.append(Integer.toHexString(new Random().nextInt(16)));
			}
			return result.toString().toUpperCase();
		} catch (Exception e) {
			LOGGER.error("生成随机数出错", e);
		}
		return null;

	}
	/**
	 * 首字母大写
	 * @param name
	 * @return
	 */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
        
    }
    /**
     * 获得uuid
     * @return
     */
    public static String getUUID(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 排序map
     * @param map
     * @return
     */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
    /**
     * 解码
     * @param str
     * @return
     */
	public static String getEncodingName(String str) {
		if(StringUtils.isBlank(str)){
			LOGGER.info("编码失败【名称为空】");
			return str;
		}
		String encode = "GB2312";
		String resultStr="";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				resultStr = str;
				LOGGER.info("编码的格式是【{}】,名字为【{}】", encode,resultStr);
				return resultStr;
			}
			encode = "ISO-8859-1";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				resultStr = new String(str.getBytes(encode), "UTF-8");
				LOGGER.info("编码的格式是【{}】,名字为【{}】", encode,resultStr);
				return resultStr;
			}
			encode = "UTF-8";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				resultStr =str;
				LOGGER.info("编码的格式是【{}】,名字为【{}】", encode,resultStr);
				return resultStr;
			}
			encode = "GBK";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				resultStr = new String(str.getBytes(encode), "UTF-8");
				LOGGER.info("编码的格式是【{}】,名字为【{}】", encode,resultStr);
				return resultStr;
			}
		} catch (Exception e) {
			LOGGER.info("编码名失败", e);
		}
		LOGGER.info("编码的格式是【编码的格式未知】");
		return resultStr;
	}
	/**
	 * 获得日期型目录
	 * @return
	 */
	public static String getFilePath(){
		String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date()) + "/"
				+ new SimpleDateFormat("MM").format(new Date()) + "/"
				+ new SimpleDateFormat("dd").format(new Date())+"/"+Common.getUUID();
		return filePath;
	}
	/**
	 * 获得名字
	 * @param path 路径
	 * @return
	 */
	public static String findFileName(String path){
		int index = path.lastIndexOf("/");
		return path.substring(index+1);
	}
	
	/**
	 * 获得名字
	 * @param path 路径
	 * @return
	 */
	public static String findFilePath(String path){
		int index = path.lastIndexOf("/");
		return path.substring(0,index);
	}
	
	/**
	 * 获得百分比
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String getPresent(int num1,int num2){
		NumberFormat numberFormat = NumberFormat.getInstance();  
        numberFormat.setMaximumFractionDigits(2);  
        
        if(num2!=0){
        	String result = numberFormat.format((float) num1 / (float) num2 * 100); 
        	result+="%";
        	return result;
        	
        }else{
        	return "";
        }
	}
}

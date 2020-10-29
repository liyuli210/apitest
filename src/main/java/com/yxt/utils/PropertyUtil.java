package com.yxt.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.Set;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年7月26日 上午10:41:03 

*/
public class PropertyUtil {
	private static Logger logger=Logger.getLogger(PropertyUtil.class);
	
	/**
	 * 读取properties配置文件
	 * @param file
	 * @return
	 */
	
	public static Properties readProperties(String file){
		Properties properties = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			properties.load(bf);
			inputStream.close(); // 关闭流
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return properties;
	}
	/**
	 * 获取配置文件中所有的参数
	 * @param file
	 * @return
	 */
	public static Map<String, String> getAllKeyValue(String file) {
		Properties properties = readProperties(file);
		Map<String, String> params=new HashMap<String, String>();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			params.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return params;
	}
	public static void main(String[] args) {
		Map<String, String> allKeyValue = PropertyUtil.getAllKeyValue("src/main/resources/mtxparams/order.properties");
//		System.out.println(properties.getProperty("spec"));
//		Map<String, String> params=new HashMap<String, String>();
//		Set<Entry<Object, Object>> entrySet = properties.entrySet();
//		for (Entry<Object, Object> entry : entrySet) {
//			params.put(entry.getKey().toString(), entry.getValue().toString());
//		}
		System.out.println(allKeyValue);
		
	}

}

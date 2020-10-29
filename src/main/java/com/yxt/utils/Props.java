package com.yxt.utils;

import java.util.HashMap;
import java.util.Map;

public class Props {
	
	//这个类提供的方法是为了全局存储某个数组，以key=value的形式存储
	private static Map<String, Object> propsMap = new HashMap<>();

	public void clear() {
		propsMap.clear();
	}

	public static Object getObject(String key) {
		return propsMap.get(key);
	}

	public static Map<String, Object> putObject(String key, Object value) {
		propsMap.put(key, value);
		return propsMap;
	}

	public static String get(String key) {
		return "" + propsMap.get(key);
	}

	public static Map<String, Object> put(String key, Object value) {
		propsMap.put(key, value);
		return propsMap;
	}

	public static Map<String, Object> remove(String key) {
		propsMap.remove(key);
		return propsMap;
	}
}

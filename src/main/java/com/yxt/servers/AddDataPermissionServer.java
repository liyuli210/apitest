package com.yxt.servers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.util.Set;

import com.yxt.http.HttpParams;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxt.http.YXTHttpClient;

/**
* @author liyuli
* 2020年9月22日上午11:36:12
*/
public class AddDataPermissionServer {
	private static Logger logger=Logger.getLogger(AddDataPermissionServer.class);
	static String uri="/data/permission/info";
	static String paramPath="src/main/resources/dataPermission/addDataPermission.json";
	
	public static String addDataPermission(String url,String token,JSONObject params) throws Exception {
		logger.info("==添加数据权限数据");
		Reporter.log("==添加数据权限数据");
		Map<String, String>headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("token", token);
		headers.put("source", "501");
		String reString=YXTHttpClient.postJson(url+uri, params, headers);
		return reString;
	}
	public static String addDataPermission(String url,String token,Map<String, String> paramMaps) throws Exception {
		logger.info("==添加数据权限数据");
		Reporter.log("==添加数据权限数据");
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("token", token);
		headers.put("source", "501");
		JSONObject params=HttpParams.readJsonFile(paramPath);
		if(paramMaps.size()>0) {
			Set<Entry<String, String>> entrySet = paramMaps.entrySet();
			for (Entry<String, String> entry:entrySet) {
				String jsonPathString=entry.getKey();
				String value=entry.getValue();
				JSONPath.set(params, jsonPathString, value);
			}
		}
		String reString=YXTHttpClient.postJson(url+uri, params, headers);
		return reString;
	}

}

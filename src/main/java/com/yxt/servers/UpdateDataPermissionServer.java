package com.yxt.servers;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxt.http.HttpParams;
import com.yxt.http.YXTHttpClient;

/**
* @author liyuli
* 2020年9月29日下午3:51:39
*/
public class UpdateDataPermissionServer {
	private static Logger logger=Logger.getLogger(UpdateDataPermissionServer.class);
	static String uri="/data/permission/info";
	static String paramPath="src/main/resources/dataPermission/updateDataPermission.json";
	
	public static int updateDataPermission(String url,String token,Map<String, String>paramMaps) throws Exception {
		logger.info("==修改数据权限");
		Reporter.log("==修改数据权限");
		JSONObject params = HttpParams.readJsonFile(paramPath);
		if(paramMaps.size()>0) {
			Set<Entry<String, String>> entrySet = paramMaps.entrySet();
			for(Entry<String, String> entry:entrySet) {
				String jsonPath=entry.getKey();
				String value=entry.getValue();
				JSONPath.set(params, jsonPath, value);
			}
		}
		YXTHttpClient.putJson(url+uri, params, token);
		return YXTHttpClient.getStatusCode();
	}
}

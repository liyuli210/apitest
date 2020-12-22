package com.yxt.servers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxt.http.HttpParams;
import com.yxt.http.YXTHttpClient;

/**
* @author liyuli
* 2020年9月2日下午12:14:18
*/
public class GetDataPermissionListServer {
	private static Logger logger=Logger.getLogger(GetDataPermissionListServer.class);
	static String uri="/data/permission/info/search";
	static String paramPath="src/main/resources/dataPermission/getDataPermission.json";
	
	/**
	 * 列表搜索
	 * @param url
	 * @param token
	 * @param paramMaps
	 * @return
	 * @throws Exception
	 */
	public static String searchDataPermissionList(String url,String token,Map<String, String>... paramMaps) throws Exception {
		logger.info("==查询数据权限列表");
		Reporter.log("==查询数据权限列表");
		Map<String, String>headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("token", token);
		headers.put("source", "501");
		JSONObject params = HttpParams.readJsonFile(paramPath);
		if(paramMaps.length>0) {
			Set<Entry<String, String>> entrySet=paramMaps[0].entrySet();
			for (Entry<String,String> entry:entrySet) {
				String jsonPath=entry.getKey();
				String value=entry.getValue();
				//传过来的参数把配置文件里的替换掉
				JSONPath.set(params, jsonPath, value);
			}
		}
//		System.out.println(params);
		String reString = YXTHttpClient.postSearch(url+uri, params, headers);
		return reString;
	}

	
	
}

package com.yxt.servers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.alibaba.fastjson.JSONObject;
import com.yxt.http.HttpParams;
import com.yxt.http.YXTHttpClient;
import com.yxt.utils.LoginEncryption;


/**
* @author liyuli
* 2020年8月31日下午6:00:35
*/
public class LoginServer {
	private static Logger logger=Logger.getLogger(LoginServer.class);
	static String uri="/auth/password";
	static String paramPath="src/main/resources/login/login.json";
	
	/**
	 * 默认读取配置文件里的用户名、密码、domain
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String login(String url) throws Exception {
		logger.info("==登录");
		Reporter.log("==登录");
		JSONObject params = HttpParams.readJsonFile(paramPath);
		String userName=params.getString("userName");
		String password=params.getString("password");
		//用户名和密码需要加密
		params.put("userName", LoginEncryption.newName(userName));
		params.put("password", LoginEncryption.newPwd(password));
		Map<String, String>headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String reString=YXTHttpClient.postJson(url+uri, params, headers);
		return reString;
	}
	/**
	 * 传入用户名、密码、domain
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String login(String url,String domain,String userName,String password) throws Exception {
		logger.info("==登录");
		Reporter.log("==登录");
		JSONObject params = HttpParams.readJsonFile(paramPath);
		params.put("domain", domain);
		params.put("userName", LoginEncryption.newName(userName));
		params.put("password", LoginEncryption.newPwd(password));
//		System.out.println(params);
		Map<String, String>headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String reString=YXTHttpClient.postJson(url+uri, params, headers);
		return reString;
	}
	
	public static String updateLogin(String url,String key,String value,String type) throws Exception {
		JSONObject params=HttpParams.readJsonFile(paramPath);
		String userName=params.getString("userName");
		String password=params.getString("password");
		if(type.equals("del")) {
			params.remove(key);
		}else {
			if (key.equals("userName")) {
				params.put(key, LoginEncryption.newName(value));
				params.put("password", LoginEncryption.newPwd(password));
			}else if(key.equals("password")) {
				params.put("userName", LoginEncryption.newName(userName));
				params.put(key, LoginEncryption.newPwd(value));
			}else {
				params.put(key, value);
			}
		}
		System.out.println(params);
		Map<String, String>headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String reString=YXTHttpClient.postJson(url+uri, params, headers);
		return reString;
	}
	
}

package com.crazy.api.study;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

/**
* @author liyuli
* 2020年10月12日上午6:18:50
*/


public class Exercise1 {
	String token;
	@Test
	public void test001() throws Exception {
		HttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://api-core-phx.yunxuetang.com.cn/v2/auth/password");
		JSONObject params=new JSONObject();
		params.put("domain", "boss-phx.yunxuetang.com.cn");
		params.put("userName", Login.newName("admin"));
		params.put("password", Login.newPwd("123456"));
		HttpEntity httpEntity=new StringEntity(params.toString());
		httpPost.setEntity(httpEntity);
		httpPost.setHeader("Content-type","application/json");
		HttpResponse response = httpClient.execute(httpPost);
		String reString = EntityUtils.toString(response.getEntity());
		System.out.println(reString);
		token=JSONPath.extract(reString, "$.userInfo.token").toString();
	}
	@Test
	public void test002() throws Exception {
		HttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet("http://api-core-phx.yunxuetang.com.cn/v2/dict?code=data_auth_component");
		httpGet.setHeader("Content-type","application/json");
		httpGet.setHeader("source","501");
		httpGet.setHeader("token",token);
		HttpResponse response = httpClient.execute(httpGet);
		String reString=EntityUtils.toString(response.getEntity());
		System.out.println(reString);
	
	}

}

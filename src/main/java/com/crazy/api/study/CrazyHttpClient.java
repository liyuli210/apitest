package com.crazy.api.study;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
* @author liyuli
* 2020年8月26日下午5:17:38
*/
public class CrazyHttpClient {
	public static HttpClient httpClient;
	public static HttpResponse response;
	static {
		httpClient=HttpClients.createDefault();
	}
	/**
	 * 
	 * @param url
	 * @param params 格式:?参数
	 * @param headers
	 * @return
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	public static String get(String url,String params,Map<String, String> headers) throws ClientProtocolException, Exception {
		HttpGet httpGet=new HttpGet(url+params);
		if(!headers.isEmpty()) {
			for (Entry<String, String> entry:headers.entrySet()) {
				httpGet.setHeader(entry.getKey(),entry.getValue());
			}
		}
		response=httpClient.execute(httpGet);
		String reString=EntityUtils.toString(response.getEntity());
		return reString;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param params post的参数
	 * @param headers
	 * @return
	 * @throws Exception 
	 */
	public static String postJson(String url,String params,Map<String, String> headers) throws Exception {
		HttpPost httpPost=new HttpPost(url);
		HttpEntity httpEntity=new StringEntity(params);
		httpPost.setEntity(httpEntity);
		if(!headers.isEmpty()) {
			for(Entry<String, String> entry:headers.entrySet()) {
				httpPost.setHeader(entry.getKey(),entry.getValue());
			}
		}
		response = httpClient.execute(httpPost);
		String reString=EntityUtils.toString(response.getEntity());
		return reString;
	}
	
	public static String postForm(String url,Map<String, String> params,Map<String, String> headers) throws Exception {
		HttpPost httpPost=new HttpPost(url);
		if(!params.isEmpty()) {
			List<NameValuePair>paramList=new ArrayList<NameValuePair>();
			for (Entry<String, String> entry:params.entrySet()) {
				NameValuePair param=new BasicNameValuePair(entry.getKey(), entry.getValue());
				paramList.add(param);
			}
			HttpEntity entity=new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
		}
		if(!headers.isEmpty()) {
			for(Entry<String, String>entry:headers.entrySet()) {
				httpPost.setHeader(entry.getKey(),entry.getValue());
			}
		}
		response=httpClient.execute(httpPost);
		String reString=EntityUtils.toString(response.getEntity());
		return reString;
	}
	public static void main(String[] args) throws Exception {
		
		
//		Map<String, String>mapList=new HashMap<String, String>();
//		System.out.println(mapList.isEmpty());
//		mapList.put("星期一", "Monday");
//		mapList.put("星期二", "Tuesday");
//		for (Entry<String, String>entry:mapList.entrySet()) {
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
		
//		for (String str:mapList.keySet()) {
//			System.out.println(str);
//		}
//		List<String>list=new ArrayList<String>();
//		list.add("2");
//		list.add("3");
//		list.add("3");
//		System.out.println(list.size());

	}
}

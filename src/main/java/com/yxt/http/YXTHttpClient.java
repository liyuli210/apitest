package com.yxt.http;
/**
* @author liyuli
* 2020年8月31日下午5:27:19
*/

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.alibaba.fastjson.JSONObject;

public class YXTHttpClient {
	private static Logger logger=Logger.getLogger(YXTHttpClient.class);
	public static HttpClient httpClient;
	public static HttpResponse response;
	static {
		httpClient=HttpClients.createDefault();
	}
	
	/**
	 * get请求，一般用于查询详情
	 * @param uri
	 * @param params
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static String getDetailById(String uri,String params,String token) throws Exception {
		HttpGet httpGet=new HttpGet(uri+"/"+params);
		logger.info("url:"+uri+"/"+params);
		Reporter.log("url:"+uri+"/"+params);
		
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("source","501");
		httpGet.setHeader("token",token);
		logger.info("headers:Content-Type,application/json");
		logger.info("headers:token"+token);
		logger.info("headers:source,501");
		Reporter.log("headers:Content-Type,application/json");
		Reporter.log("headers:token"+token);
		Reporter.log("headers:source,501");
		
		response=httpClient.execute(httpGet);
		String reString=EntityUtils.toString(response.getEntity());
		logger.info("response:"+reString);
		Reporter.log("response:"+reString);
		
		return reString;
	}
	
	/**
	 * 有些搜索是post请求，但是把参数跟在了url上
	 * @param uri
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static String postSearch(String uri,JSONObject params,Map<String, String> headers) throws Exception {
		Iterator iter = params.entrySet().iterator();
		String searchKeyStr="";
		while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if(searchKeyStr.equals("")) {
            	searchKeyStr="?"+entry.getKey().toString()+"="+entry.getValue().toString();
            }else {
            	searchKeyStr+="&"+entry.getKey().toString()+"="+entry.getValue().toString();
            }
        }
//		System.out.println(uri+searchKeyStr);
		HttpPost httpPost=new HttpPost(uri+searchKeyStr);
		logger.info("url:"+uri+searchKeyStr);
		Reporter.log("url:"+uri+searchKeyStr);
		if(!headers.isEmpty()) {
			for (Entry<String, String>entry:headers.entrySet()) {
				httpPost.setHeader(entry.getKey(),entry.getValue());
				logger.info("headers:"+entry.getKey()+","+entry.getValue());
				Reporter.log("headers:"+entry.getKey()+","+entry.getValue());
			}
		}
		response=httpClient.execute(httpPost);
		String reString=EntityUtils.toString(response.getEntity());
		logger.info("response:"+reString);
		Reporter.log("response:"+reString);
		return reString;
	}
	
	/**
	 * post请求，参数是json格式
	 * @param uri
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String postJson(String uri,JSONObject params,Map<String, String>headers) throws Exception {
		HttpPost httpPost=new HttpPost(uri);
		logger.info("url:"+uri);
		logger.info("params:"+params.toString());
		Reporter.log("url:"+uri);
		Reporter.log("params:"+params.toString());
		HttpEntity entity=new StringEntity(params.toString(),"utf-8");
		httpPost.setEntity(entity);
		if(!headers.isEmpty()) {
			for (Entry<String, String> entry:headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
				logger.info("headers:"+entry.getKey()+","+entry.getValue());
				Reporter.log("headers:"+entry.getKey()+","+entry.getValue());
			}
		}
		response=httpClient.execute(httpPost);
		String reString=EntityUtils.toString(response.getEntity());
		logger.info("response:"+reString);
		Reporter.log("response:"+reString);
		return reString;
	}
	
	/**
	 * put请求，用作修改接口
	 * @param uri
	 * @param params
	 * @param headers
	 * @throws Exception
	 */
	public static void putJson(String uri,JSONObject params,String token) throws Exception {
		HttpPut httpPut=new HttpPut(uri);
		logger.info("url:"+uri);
		logger.info("params:"+params.toString());
		Reporter.log("url:"+uri);
		Reporter.log("params:"+params.toString());
		HttpEntity entity=new StringEntity(params.toString(),"utf-8");
		httpPut.setEntity(entity);
		
		httpPut.setHeader("Content-Type", "application/json");
		httpPut.setHeader("token", token);
		httpPut.setHeader("source", "501");
		logger.info("headers:Content-Type,application/json");
		logger.info("headers:token"+token);
		logger.info("headers:source,501");
		Reporter.log("headers:Content-Type,application/json");
		Reporter.log("headers:token"+token);
		Reporter.log("headers:source,501");
		
		response=httpClient.execute(httpPut);
	}
	/**
	 * 
	 * @param uri
	 * @param params，一般来说是要删除的id
	 * @param token
	 * @throws Exception 
	 * @throws  
	 */
	public static void deleteById(String uri,String params,String token) throws  Exception {
		HttpDelete httpDelete=new HttpDelete(uri+"/"+params);
		logger.info("url:"+uri+"/"+params);
		Reporter.log("url:"+uri+"/"+params);
		
		httpDelete.setHeader("Content-Type", "application/json");
		httpDelete.setHeader("token", token);
		httpDelete.setHeader("source", "501");
		logger.info("headers:Content-Type,application/json");
		logger.info("headers:token"+token);
		logger.info("headers:source,501");
		Reporter.log("headers:Content-Type,application/json");
		Reporter.log("headers:token"+token);
		Reporter.log("headers:source,501");
		
		response=httpClient.execute(httpDelete);
		
	}
	
	
	
	//获取响应状态码
	public static int getStatusCode() {
		return response.getStatusLine().getStatusCode();
	}
	public static void main(String[] args) throws Exception {
		String aaString="{\"offset\":0,\"limit\":10,\"orderby\":\"orderIndex\",\"keyword\":\"test1\",\"direction\":\"ASC\"}";
		JSONObject aa=JSONObject.parseObject(aaString);
		Map<String, String>headers=new HashMap<String, String>();
		postSearch("", aa, headers);
	}
}

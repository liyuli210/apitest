package com.crazy.api.study;
/**
* @author liyuli
* 2020年8月19日下午5:44:27
*/

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpClientStudy {
//	@Test
	public void test001_get() throws Exception {
		//创建一个客户端
		HttpClient httpClient=HttpClients.createDefault();
		//创建一个get请求
		HttpGet httpGet=new HttpGet("http://172.17.123.70:8080/pinter/com/getSku?id=1");
		//发送请求
		HttpResponse response = httpClient.execute(httpGet);
		//拿到返回值转为string
		String reString=EntityUtils.toString(response.getEntity());
		System.out.println(reString);
	}
	
	//参数为k=v的POST接口，form表单形式
//	@Test
	public void test002_postForm() throws Exception {
		HttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://172.17.123.70:8080/pinter/com/login");
		//添加参数
		NameValuePair userName=new BasicNameValuePair("userName", "admin");
		NameValuePair passWord=new BasicNameValuePair("password", "123456");
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(userName);
		params.add(passWord);
		//只有转化为HttpEntity才能加入到参数里
		HttpEntity httpEntity=new UrlEncodedFormEntity(params);
		httpPost.setEntity(httpEntity);
		//发送请求
		HttpResponse response = httpClient.execute(httpPost);
		//获取响应的状态码
		System.out.println(response.getStatusLine().getStatusCode());
		String reString=EntityUtils.toString(response.getEntity());
		System.out.println(reString);
	}
	
	//参数为json的POST接口
//	@Test
	public void test003_postJson() throws Exception {
		HttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://172.17.123.70:8080/pinter/com/register");
		//添加json格式的参数
		String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		HttpEntity entity=new StringEntity(json,"utf-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "application/json");
		//客户端发起请求
		HttpResponse response=httpClient.execute(httpPost);
		//得到响应状态码
		System.out.println(response.getStatusLine().getStatusCode());
		String reString=EntityUtils.toString(response.getEntity());
		System.out.println(reString);
		Assert.assertTrue(reString.contains("注册成功"));
		
	}
	
	//参数为k=json的POST接口
//	@Test
	public void test004_postKV() throws Exception {
		HttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://172.17.123.70:8080/pinter/com/buy");
		NameValuePair param=new BasicNameValuePair("param", "{\"skuId\":123,\"num\":10}");
		List<NameValuePair>listParam=new ArrayList<NameValuePair>();
		listParam.add(param);
		//只有转化为HttpEntity才能加入到参数里
		HttpEntity httpEntity=new UrlEncodedFormEntity(listParam);
		httpPost.setEntity(httpEntity);
		//发起请求
		HttpResponse response=httpClient.execute(httpPost);
		//获取响应结果
		System.out.println(response.getStatusLine().getStatusCode());
		String reString=EntityUtils.toString(response.getEntity());
		System.out.println(reString);
		Assert.assertTrue(reString.contains("\"message\":\"success\""));
		
		
	}
	
	//XML形式的参数
//	@Test
	public void test005_postXML() throws Exception {
		//1. 创建发起请求的客户端对象,相当于浏览器
		HttpClient httpClient=HttpClients.createDefault();
		//2. 创建一个请求对象，HttpPost
		HttpPost httpPost=new HttpPost("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
				"  <soap:Body>\r\n" + 
				"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
				"      <mobileCode>18729399607</mobileCode>\r\n" + 
				"      <userID></userID>\r\n" + 
				"    </getMobileCodeInfo>\r\n" + 
				"  </soap:Body>\r\n" + 
				"</soap:Envelope>";
		HttpEntity entity=new StringEntity(xml, "utf-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "text/xml");
		//4. 使用客户端对象发起请求，并得到响应结果
		HttpResponse response = httpClient.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode());
		//5. 将响应body转换成字符串
		String reString = EntityUtils.toString(response.getEntity());
		System.out.println(reString);
	}
	
	//上传图片的接口,MultipartEntityBuilder
	@Test
	public void test006_upload() throws Exception {
		//1.创建发起请求的客户端对象，相当于浏览器
		//在同一个HttpClient上，cookie会自动带过去
		HttpClient httpClient=HttpClients.createDefault();
		//2.创建登录请求
		HttpPost httpPostLogin=new HttpPost("http://172.17.123.70/mtx/index.php?s=/index/user/login.html");
		//添加参数
		NameValuePair userName=new BasicNameValuePair("accounts", "liyuli");
		NameValuePair passWord=new BasicNameValuePair("pwd", "123456");
		List<NameValuePair>listParam=new ArrayList<NameValuePair>();
		listParam.add(userName);
		listParam.add(passWord);
		//只有转化为HttpEntity才能放到参数里
		HttpEntity entityLogin=new UrlEncodedFormEntity(listParam);
		httpPostLogin.setEntity(entityLogin);
		httpPostLogin.setHeader("X-Requested-With","XMLHttpRequest");
		HttpResponse responseLogin = httpClient.execute(httpPostLogin);
		System.out.println(responseLogin.getStatusLine().getStatusCode());
		String reString=EntityUtils.toString(responseLogin.getEntity());
		System.out.println(reString);
//		Assert.assertTrue(reString.contains("登录成功"));
		
		//创建上传头像请求
		HttpPost httpPost=new HttpPost("http://172.17.123.70/mtx/index.php?s=/index/user/useravatarupload.html");
		//添加参数
		MultipartEntityBuilder builder=MultipartEntityBuilder.create();
		HttpEntity entity = builder.addTextBody("img_x", "70")
				   .addTextBody("img_y", "70")
				   .addTextBody("img_width", "560")
				   .addTextBody("img_height", "560")
				   .addTextBody("img_rotate", "0")
				   .addBinaryBody("file", new File("C:\\Users\\liyl\\Pictures\\1.jpg"), ContentType.IMAGE_JPEG, "1.jpg")
				   .build();
		httpPost.setEntity(entity);
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		HttpResponse responseUpload = httpClient.execute(httpPost);
		System.out.println(responseUpload.getStatusLine().getStatusCode());
		String reStringUpload = EntityUtils.toString(responseUpload.getEntity());
		System.out.println(reStringUpload);
		
	}
	
	

}

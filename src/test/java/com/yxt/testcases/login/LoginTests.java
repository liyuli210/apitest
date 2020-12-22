package com.yxt.testcases.login;
/**
* @author liyuli
* 2020年9月1日下午5:13:07
*/

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONPath;
import com.yxt.servers.LoginServer;
import com.yxt.testcases.TestBase;

public class LoginTests extends TestBase {
//	String url;
//	@BeforeClass
//	public void init() {
//		url="http://api-core-phx.yunxuetang.com.cn/v2";
//	}
	@Test(description = "默认boss平台正确登录")
	public void test001_login() throws Exception {
		String login = LoginServer.login(url);
		String username=JSONPath.extract(login, "$.userInfo.username").toString();
		Assert.assertEquals(username, "admin");
		
	}
	@Test(description = "更换域名登录")
	public void test002_login() throws Exception {
		String login = LoginServer.login(url,"xx-phx.yunxuetang.com.cn","admin","123456");
		String username=JSONPath.extract(login, "$.userInfo.username").toString();
		Assert.assertEquals(username, "admin");
	}
	//继续
}

package com.yxt.testcases;

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.alibaba.fastjson.JSONPath;
import com.yxt.servers.LoginServer;
import com.yxt.utils.DBUtil;
import com.yxt.utils.PropertyUtil;

/**
* @author liyuli
* 2020年9月8日下午8:33:19
*/
public class TestBase {
	public String url;
	public String token;
	@BeforeClass
	public void init() throws Exception {
		url="http://api-core-phx.yunxuetang.com.cn/v2";
		String login = LoginServer.login(url);
		token=JSONPath.extract(login, "$.userInfo.token").toString();
		Properties readProperties=PropertyUtil.readProperties("src/main/resources/db.properties");
		String dburl=readProperties.getProperty("core.url");
		String dbusername=readProperties.getProperty("core.username");
		String dbpassword=readProperties.getProperty("core.password");
		DBUtil.getConnection(dburl, dbusername, dbpassword);
	}
	@AfterClass
	public void close() {
		DBUtil.close();
	}
}

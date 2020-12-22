package com.yxt.servers;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.yxt.http.YXTHttpClient;

/**
* @author liyuli
* 2020年10月9日上午7:20:12
*/


public class DetailDataPermissionServer {
	private static Logger logger=Logger.getLogger(DetailDataPermissionServer.class);
	static String uri="/data/permission/info";
	public static String detailById(String url,String token,String params) throws Exception {
		logger.info("==查询数据权限详情");
		Reporter.log("==查询数据权限详情");
		String detail = YXTHttpClient.getDetailById(url+uri, params, token);
		return detail;
	}
	
}

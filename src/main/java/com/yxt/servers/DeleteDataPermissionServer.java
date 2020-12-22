package com.yxt.servers;

import com.yxt.http.YXTHttpClient;

/**
* @author liyuli
* 2020年10月5日上午8:58:20
*/
public class DeleteDataPermissionServer {
	static String uri="/data/permission/info";
	public static int deleteDataPermission(String url,String token,String deleteId) throws Exception {
		YXTHttpClient.deleteById(url+uri, deleteId, token);
		return YXTHttpClient.getStatusCode();
	}
}

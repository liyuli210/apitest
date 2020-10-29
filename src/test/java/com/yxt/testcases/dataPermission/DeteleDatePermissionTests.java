package com.yxt.testcases.dataPermission;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.yxt.servers.DeleteDataPermissionServer;
import com.yxt.testcases.TestBase;

/**
* @author liyuli
* 2020年10月5日上午9:15:07
*/
public class DeteleDatePermissionTests extends TestBase {
	@Test
	public void test001_deleteById() throws Exception {
		int status = DeleteDataPermissionServer.deleteDataPermission(url, token, "cb775525-6e38-4ae0-aef1-9486e57ee262");
		Assert.assertEquals(status, 204);
	}
}

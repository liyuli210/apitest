package com.yxt.testcases.dataPermission;
/**
* @author liyuli
* 2020年9月2日下午12:36:07
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.yxt.servers.GetDataPermissionListServer;
import com.yxt.servers.LoginServer;
import com.yxt.testcases.TestBase;
import com.yxt.utils.DBUtil;
import com.yxt.utils.PropertyUtil;

public class GetDataPermissionListTests extends TestBase {
//	@Test(description = "查看列表")
	public void test001_getDataPermissionList() throws Exception {
		Map<String, String>params=new HashMap<String, String>();
		String dataPermissionList = GetDataPermissionListServer.searchDataPermissionList(url,token,params);
		System.out.println("这里"+dataPermissionList);
		//数据库校验
		List<Map> data = DBUtil.getData("select name,code from core_data_permission_info order by order_index,create_time DESC LIMIT 10");
		JSONArray namesArray=(JSONArray)JSONPath.extract(dataPermissionList, "$datas.name");
		JSONArray codeArray=(JSONArray)JSONPath.extract(dataPermissionList, "$datas.code");
		for (int i = 0; i < namesArray.size(); i++) {
			Map map=data.get(i);
			Assert.assertEquals(map.get("name"), namesArray.get(i));
			Assert.assertEquals(map.get("code"), codeArray.get(i));
		}
	}
	@Test(description = "列表搜索")
	public void test002_getDataPermissionList() throws Exception {
		Map<String, String>params=new HashMap<String, String>();
		params.put("keyword", "选择试卷批阅人");
		String dataPermissionList = GetDataPermissionListServer.searchDataPermissionList(url, token, params);
		//数据库校验
		List<Map> data = DBUtil.getData("select name,code from core_data_permission_info where name like '%选择试卷批阅人%' or catalog_name like '%选择试卷批阅人%' order by order_index,create_time DESC LIMIT 10");
		JSONArray namesArray=(JSONArray)JSONPath.extract(dataPermissionList, "$datas.name");
		JSONArray codeArray=(JSONArray)JSONPath.extract(dataPermissionList, "$datas.code");
		for (int i = 0; i < namesArray.size(); i++) {
			Map map=data.get(i);
			Assert.assertEquals(map.get("name"), namesArray.get(i));
			Assert.assertEquals(map.get("code"), codeArray.get(i));
		}
	}
	@Test(description = "检查列表总条数")
	public void test003_getDataPermissionCount() throws Exception {
		Map<String, String>params=new HashMap<String, String>();
		String dataPermissionList = GetDataPermissionListServer.searchDataPermissionList(url,token,params);
		int count=(int)JSONPath.extract(dataPermissionList, "$paging.count");
	
		List<Map> data = DBUtil.getData("select COUNT(*)countx from core_data_permission_info");
		int sqlCount=Integer.parseInt(data.get(0).get("countx").toString());
//		System.out.println(sqlCount);
		Assert.assertEquals(count, sqlCount);
	}

}

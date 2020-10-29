package com.yxt.testcases.dataPermission;
/**
* @author liyuli
* 2020年9月22日下午1:24:48
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxt.servers.AddDataPermissionServer;
import com.yxt.testcases.TestBase;
import com.yxt.utils.DBUtil;
import com.yxt.utils.ExcelUtil;
import com.yxt.utils.RandomUtil;

public class AddDataPermissionTests extends TestBase {
	@DataProvider
	public Object[][] getData() throws Exception{
		ExcelUtil excelUtil=new ExcelUtil("src/main/resources/dataPermission/addDataPermissiondata.xlsx");
		Object[][]sheetData=excelUtil.getSheetData("新增数据权限");
		excelUtil.close();
		return sheetData;
	}
	
	@Test(dataProvider = "getData",description = "一些字段的校验")
	public void test001_addDataPermission(String caseName,String params,String assertValue) throws Exception {
		Reporter.log(caseName+"开始执行");
		JSONObject jsonParams=JSONObject.parseObject(params);
		if(caseName.contains("21")) {
			String randomName = RandomUtil.getRndStrAndNumberByLen(20);
			JSONPath.set(jsonParams, "$.name", randomName);
		}
		String reString = AddDataPermissionServer.addDataPermission(url, token, jsonParams);
		Assert.assertTrue(reString.contains(assertValue));
	}
	
	@Test(description = "添加重复的名称")
	public void test002_addRepeatName() throws Exception {
		//查询一个已经存在的name
		List<Map> data = DBUtil.getData("select name from core_data_permission_info order by order_index ,create_time DESC LIMIT 1");
		String repeatName=data.get(0).get("name").toString();
//		System.out.println(repeatName);
		Map<String, String> paramMaps=new HashMap<String, String>();
		paramMaps.put("name", repeatName);
		String reString = AddDataPermissionServer.addDataPermission(url, token, paramMaps);
		Assert.assertTrue(reString.contains("apis.core.data.permission.info.name.exist"));
	}
	
	@Test(description = "正确添加")
	public void test003_addNew() throws Exception {
		Map<String, String> paramMaps=new HashMap<String, String>();
		String name=RandomUtil.getRndStrAndNumberByLen(5);
		String code=RandomUtil.getRndStrAndNumberByLen(5);
		paramMaps.put("name", name);
		paramMaps.put("code", code);
		String reString = AddDataPermissionServer.addDataPermission(url, token, paramMaps);
		//接口返回校验
		String checkName = JSONPath.extract(reString, "$.name").toString();
		String checkCode=JSONPath.extract(reString, "$.code").toString();
		Assert.assertEquals(name, checkName);
		Assert.assertEquals(code, checkCode);
		//数据库返回校验
		List<Map> data = DBUtil.getData("select * from core_data_permission_info where name='"+name+"'");
		String sqlName=data.get(0).get("name").toString();
		String sqlCode=data.get(0).get("code").toString();
		Assert.assertEquals(name, sqlName);
		Assert.assertEquals(code, sqlCode); 
	}

}

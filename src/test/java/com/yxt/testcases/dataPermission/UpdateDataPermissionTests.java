package com.yxt.testcases.dataPermission;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxt.servers.AddDataPermissionServer;
import com.yxt.servers.DeleteDataPermissionServer;
import com.yxt.servers.DetailDataPermissionServer;
import com.yxt.servers.GetDataPermissionListServer;
import com.yxt.servers.UpdateDataPermissionServer;
import com.yxt.testcases.TestBase;
import com.yxt.utils.DBUtil;
import com.yxt.utils.RandomUtil;

/**
* @author liyuli
* 2020年9月29日下午2:26:53
*/
public class UpdateDataPermissionTests extends TestBase{
	static String name=RandomUtil.getRndStrAndNumberByLen(5);
	static String code=RandomUtil.getRndStrAndNumberByLen(5);
	
	static String newName=RandomUtil.getRndStrAndNumberByLen(5);
	


	/**
	 * 添加测试数据用
	 * @return
	 * @throws Exception
	 */
	public String addTestData() throws Exception {
		Map<String, String> paramMaps=new HashMap<String, String>();
		paramMaps.put("name", name);
		paramMaps.put("code", code);
		String reString = AddDataPermissionServer.addDataPermission(url, token, paramMaps);
		String dataId = JSONPath.extract(reString, "$.id").toString();
		return dataId;
	}
	
	/**
	 * 各个地方的校验
	 * @param dataId
	 * @throws Exception
	 */
	public void assertFunction(String dataId) throws Exception {
		//数据库校验
		List<Map> dataSql = DBUtil.getData("select * from core_data_permission_info where id='"+dataId+"'");
		Map map=dataSql.get(0);
		String nameSql=map.get("name").toString();
		Assert.assertEquals(nameSql, newName);
		
		//列表接口校验
		Map<String, String>params=new HashMap<String, String>();
		String dataPermissionList = GetDataPermissionListServer.searchDataPermissionList(url,token,params);
		JSONObject dataList=(JSONObject)JSONPath.extract(dataPermissionList, "$.datas[id='"+dataId+"'][0]");
		String nameList=dataList.getString("name");
		Assert.assertEquals(nameList, newName);
		
		//详情接口校验
		JSONObject detailList = JSONObject.parseObject(DetailDataPermissionServer.detailById(url, token, dataId));
		assertEquals(detailList.getString("name"), newName);
	}
	
	
	@Test(description = "正确修改标题")
	public void test001_updateTitle() throws Exception {
		String dataId = addTestData();
		Map<String, String>paramMaps=new HashMap<String, String>();
		paramMaps.put("id", dataId);
		paramMaps.put("name", newName);
		paramMaps.put("code", code);
		int status = UpdateDataPermissionServer.updateDataPermission(url, token, paramMaps);
		Assert.assertEquals(status, 204);
		assertFunction(dataId);
	}
//	@Test(description = "正确修改分类")
//	public void test002_updateCatalogName() {
//		String newCatalogName="岗位维度";
//		Map<String, String>paramMaps=new HashMap<String, String>();
//		paramMaps.put("id", dataId);
//		paramMaps.put("name", newName);
//		paramMaps.put("code", code);
//	}
	
	@AfterClass(description = "用于清除测试数据")
	public void delete() throws Exception {
		Map<String, String>params=new HashMap<String, String>();
		String dataPermissionList = GetDataPermissionListServer.searchDataPermissionList(url,token,params);
		JSONArray arrayList=(JSONArray)JSONPath.extract(dataPermissionList, "$.datas[remark='李玉立测试']");
		System.out.println(arrayList.size());
		for (int i = 0; i < arrayList.size(); i++) {
			JSONObject detail=(JSONObject)arrayList.get(i);
			DeleteDataPermissionServer.deleteDataPermission(url, token, detail.getString("id"));
		}
	}
	
	
	
}

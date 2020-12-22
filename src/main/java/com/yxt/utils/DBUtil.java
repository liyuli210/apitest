package com.yxt.utils;
/**
* @author liyuli
* 2020年9月7日下午2:46:27
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Reporter;



public class DBUtil {
	private static Logger logger=Logger.getLogger(DBUtil.class);
	private static Connection connection;
	public static void getConnection(String url, String user, String password) throws Exception {
		connection=DriverManager.getConnection(url, user, password);
		logger.info("连接数据库");
		Reporter.log("连接数据库");
	}
	public static void close() {
		try {
			connection.close();
			logger.info("关闭数据库");
			Reporter.log("关闭数据库");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 查询
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Map> getData(String sql) throws Exception {
		PreparedStatement preparedStatement=connection.prepareStatement(sql);//创建一个预处理对象
		ResultSet resultSet=preparedStatement.executeQuery();//执行查询条件
		ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
		int col=resultSetMetaData.getColumnCount();//获取结果的总列数
		List<Map>list=new ArrayList<Map>();
		logger.info("查询："+sql);
		Reporter.log("查询："+sql);
		while (resultSet.next()) {
			Map<String, Object>line=new HashMap<String, Object>();
			for (int i = 1; i <= col; i++) {
				Object object=resultSet.getObject(i);//获取第几列数据值
				String columnLabel=resultSetMetaData.getColumnLabel(i);//获取当前列的名称
				line.put(columnLabel,object);
//				System.out.println(columnLabel);
			}
			list.add(line);
		}
		return list;
	}
	/**
	 * executeUpdate方法可以执行新增、更新、删除三种sql语句
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(String sql) {
		Statement stmt=null;
		try {
			stmt=connection.createStatement();
			stmt.executeUpdate(sql);
			int updateCount=stmt.getUpdateCount();
			return updateCount;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		getConnection("jdbc:mysql://172.17.126.158:3306/core?useUnicode=true&useSSL=false&characterEncoding=UTF-8&characterSetResults=UTF-8&autoReconnect=true&serverTimezone=GMT%2B8", "yxt", "afg)gppOs22k");
		List<Map> data = getData("select * from core_permission_source");
		Map map=data.get(0);
		System.out.println(data.size());
		System.out.println(map.get("id"));
		System.out.println(map.get("name"));
		close();

	}
	
}

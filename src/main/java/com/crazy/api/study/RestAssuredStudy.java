package com.crazy.api.study;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
/**
* @author liyuli
* 2020年8月24日下午3:53:07
*/
public class RestAssuredStudy {
	String token;
	@Test
	public void test001_get() {
		given()
			.queryParam("id", 1)
		.when()
			.log().all()
			.get("http://172.17.123.70:8080/pinter/com/getSku")
		.then()
			.log().all()
			.statusCode(200)
			.body("data.skuName", equalTo("ptest-1"))
			.body("data.stock", lessThanOrEqualTo(1000))
			.body("message", equalTo("success"));
	}
	@Test
	public void test002_postForm() {
		token = given()
			.formParam("username", "admin")
			.formParam("password", "123456")
		.when()
			.log().all()
			.post("http://172.17.123.70:8090/login")
		.then()
			.log().all()
			.statusCode(200)
			.body("code", equalTo(0))
			.extract().jsonPath().getString("Admin-Token");
	}
	@Test
	public void test003_postJson() {
		String json="{\r\n" + 
				"	\"entity\": {\r\n" + 
				"		\"customer_name\": \"客户123\",\r\n" + 
				"		\"mobile\": \"18991112345\",\r\n" + 
				"		\"telephone\": \"01028375678\",\r\n" + 
				"		\"website\": \"http://testfan.cn\",\r\n" + 
				"		\"next_time\": \"2020-04-02 00:00:00\",\r\n" + 
				"		\"remark\": \"这是备注\",\r\n" + 
				"		\"address\": \"北京市,北京城区,昌平区\",\r\n" + 
				"		\"detailAddress\": \"霍营地铁\",\r\n" + 
				"		\"location\": \"\",\r\n" + 
				"		\"lng\": \"\",\r\n" + 
				"		\"lat\": \"\"\r\n" + 
				"	}\r\n" + 
				"}";
		given()
			.body(json)
			.contentType(ContentType.JSON)
			.header("Admin-Token",token)
		.when()
			.log().all()
			.post("http://172.17.123.70:8090/CrmCustomer/addOrUpdate")
		.then()
			.log().all()
			.statusCode(200)
			.body("code", equalTo(0));
		
		
	}
	
}

package com.yxt.http;
/**
* @author liyuli
* 2020年8月31日下午7:08:21
*/

import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class HttpParams {
	public static JSONObject readJsonFile(String file) throws Exception {
		JSONReader jsonReader=new JSONReader(new FileReader(file));
		String readString = jsonReader.readString();
		JSONObject jsonObject=JSONObject.parseObject(readString);
		return jsonObject;
	}
	public static String readStringFile(String file) throws Exception {
		String readFileToString = FileUtils.readFileToString(new File(file), "utf-8");
		return readFileToString;
	}
}

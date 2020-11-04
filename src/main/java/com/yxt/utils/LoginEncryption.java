package com.yxt.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * @author liyuli 2020年8月31日下午5:38:57 登录前用户和密码加密
 */

public class LoginEncryption {
	public static synchronized byte[] encrypt(byte[] plainText, int offset, int len, SecretKey skey, byte[] iv)throws Exception {
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aes.init(1, skey, ivSpec);
			byte[] cipherText = aes.doFinal(plainText, offset, len);
			return cipherText;
		} catch (Exception var8) {
			throw new Exception("Error in encryption:", var8);
		}
	}
	/**
	 * 
	 * @param passwordVal
	 * @return
	 * @throws Exception
	 */
	public static String newPwd(String passwordVal) throws Exception {
		String suffix="-phx.yunxuetang.com.cn/v2";
		String passWordKey="L2wzTPcueOVkQr2IBVAoLFQxK1IL5uxm";
		String passWordVersion="LQsG8QY5CXKVTzpV";
		String time = String.valueOf(System.currentTimeMillis());
		String plainText=passwordVal+ "/"+ time;
		//String plainText="123456/1586742252112";
		byte[]  plainTextByte= plainText.getBytes();
		if(suffix.contains("ucstable")){
			passWordKey="GOhilvqs0xfmP16usn2WcIVilUC7Ete1";
			passWordVersion="D02Q9VlB9j8he5v4";
		}else if(suffix.contains(".yunxuetang.cn")){
			passWordKey="XPJZBXAQVNK1DEGwrzDqP0jrDR9Rfqah";
			passWordVersion="BWD2hLEAJQogJKB2";
		}
		String pwd = Base64.encode(encrypt(plainTextByte, 0,plainTextByte.length,new SecretKeySpec(passWordKey.getBytes(), "AES"),passWordVersion.getBytes()));
		return pwd;
	}
	public static String newName(String userName) {
		return Base64.encode(userName.getBytes());
	}
}

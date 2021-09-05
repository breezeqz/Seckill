package com.site.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static final String salt = "9d5b364d";
	//输入密码转换成表格密码
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	//表格密码转换成数据库密码
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	//输入密码转换成数据库密码（双重加密，前端加密后端加密）
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);//先把输入密码转换成表格密码
		String dbPass = formPassToDBPass(formPass, saltDB);//再把表格密码转换成数据库密码
		return dbPass;
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToDbPass("123456", "9d5b364d"));//ae2fe40a6242ef07a35a30da2232e10a
		System.out.println(inputPassToFormPass("123456"));//3457ceaeba3426466887369f1a1f7a88

	} 
	
}

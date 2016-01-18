package com.agitation.sport.util;

import com.taobao.wsg.signcheck.CheckWithConfig;


public class SecurityAliUtils {
	
	private static final String configFilePath = "conf/conf.properties";
	private static CheckWithConfig checkWithConfig = new CheckWithConfig(configFilePath);
	private static String type = "hmacsha1";
	private static final String APP_KEY = "9983fa3c-dfcd-435f-a63c-18db5e17eae3";
	
	/**
	 * 校验签名
	 * @param input
	 * @param appKey
	 * @param sign
	 * @return
	 */
	public static boolean checkSign(String input, String sign){
		String result = "false";
		try {
			result = checkWithConfig.check(type, input , APP_KEY, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.parseBoolean(result);
	}
	
	/**
	 * 对加密字符串解密
	 * @param input 待解密字符串
	 * @return 解密结果
	 */
	public static String decryptStr(String input){
		try {
			String decryptResult = checkWithConfig.decrypt(input, APP_KEY);
			return decryptResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对字符串加密
	 * @param input 待加密字符串
	 * @return 加密结果
	 */
	public static String encryptStr(String input){
		try {
			String encryptResult = checkWithConfig.encrypt(input, APP_KEY);
			return encryptResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

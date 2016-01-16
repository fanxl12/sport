package com.agitation.sport.util;

import com.taobao.wsg.signcheck.CheckWithConfig;


public class SecurityUtils {
	
	private static final String configFilePath = "conf/conf.properties";
	private static CheckWithConfig checkWithConfig = new CheckWithConfig(configFilePath);
	private static String type = "hmacsha1";
	
//	public static boolean checkSign(String input, String appKey){
//		
//		checkWithConfig.check(type, input , appKey, sign);
//	}

}

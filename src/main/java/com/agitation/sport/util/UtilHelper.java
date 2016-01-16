package com.agitation.sport.util;

import eu.bitwalker.useragentutils.UserAgent;

public class UtilHelper {

	/**
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点维度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点维度
	 * @return 距离
	 */
	public static double getDistance(double long1, double lat1, double long2, double lat2) {
		double a, b, r;
		r = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* r
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	/**
	 * 判断是否是android设备
	 * @param userAgentStr
	 * @return
	 */
	public static boolean isAndroidDevice(String userAgentStr){
		UserAgent userAgent = new UserAgent(userAgentStr);
		boolean isAndroid = false;
		if(userAgent.getOperatingSystem()!=null){
			String os = userAgent.getOperatingSystem().getName();
			if(os.contains("android") || os.contains("ANDROID")){
				isAndroid = true;
			}
		}
		return isAndroid;
	}

}

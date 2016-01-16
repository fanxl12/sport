package com.agitation.sport.util;

import java.util.Comparator;
import java.util.Map;

public class CompareDis implements Comparator<Map<String, Object>>{

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		
		double dis1 = Double.parseDouble(o1.get("distance")+"");
		double dis2 = Double.parseDouble(o2.get("distance")+"");
		
		if(dis1>dis2){
			return 1;
		}else if(dis1<dis2){
			return -1;
		}else{
			return 0;
		}
	}

}

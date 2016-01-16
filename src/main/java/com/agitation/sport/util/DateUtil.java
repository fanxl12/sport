package com.agitation.sport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt, int day) {
		
		String date = "";
		
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.roll(Calendar.DAY_OF_YEAR, day);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		
		if(day==0){
			date = "今天";
		}else if(day==1){
			date = "明天";
		}else{
			date = (cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日";
		}
		
		return date +" "+ weekDays[w];
	}
	
	public static Date getPointDate(int day, Date date){
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		calendar.roll(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}
	
	public static String getDateOfDay(Date date, int day){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		calendar.roll(Calendar.DAY_OF_YEAR, day);
		Date result = calendar.getTime();
		return dateFormat.format(result);
	}
	
	public static String strDateToStr(String time){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		SimpleDateFormat strFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = strFormat.parse(time);
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

}

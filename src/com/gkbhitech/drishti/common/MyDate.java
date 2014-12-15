package com.gkbhitech.drishti.common;

import java.util.Calendar;

public class MyDate {

	/*public static String convertIntegerToStringDate(Integer date) {
		String stringDate = date.toString();
		return stringDate.substring(6, 8) + "/" + stringDate.substring(4, 6)
				+ "/" + stringDate.substring(0, 4);
	}*/
	
	public static String convertIntegerToStringDate1(Integer date) {
		String stringDate = date.toString();
		return stringDate.substring(4, 6) + "/" + stringDate.substring(6, 8)
				+ "/" + stringDate.substring(0, 4);
	}

	public static String convertIntegerToStringTime(Integer time) {
		String stringTime = time.toString();
		// if(stringTime.length() == 6){
		// return
		// stringTime.substring(0,2)+":"+stringTime.substring(2,4)+":"+stringTime.substring(4,6);
		// }else{
		int length = stringTime.length();
		while (length < 6) {
			stringTime = "0" + stringTime;
			length = stringTime.length();
		}
		return stringTime.substring(0, 2) + ":" + stringTime.substring(2, 4)
				+ ":" + stringTime.substring(4, 6);
		// }
	}

	public static Long createIntegerDate(int year, int monthOfYear, int dayOfMonth) {

		String month = "";
		String day = "";

		if (monthOfYear < 10) {
			month = "0" + monthOfYear;
		} else {
			month = monthOfYear + "";
		}

		if (dayOfMonth < 10) {
			day = "0" + dayOfMonth;
		} else {
			day = dayOfMonth + "";
		}

		return new Long(year + month + day);
	}
	
	public static String createStringDate(Calendar calendar){
		return calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
	}

}

package com.avekshaa.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConvertion {
	public static Date  utilToSqlDate(String str) {
		Date sqlDate=null;
		try {
			java.util.Date date = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(str);
            sqlDate=new Date(date.getTime());
    		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	public static String  changeDateFormate(String str) throws ParseException  {
		return new SimpleDateFormat("E MMM dd yyyy").format(new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(str));
	}
	
	public static void main(String[] args) {
		String str="Mon Mar 04 2019 00:00:00 GMT+0000";
		try {
			java.util.Date date = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(str);
            Date sqlDate=new Date(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
            String formattedDate = formatter.format(sqlDate);
   	} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
}

package com.abchospital.billing;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;    
public class DateProcessor {    
	public String getDate() {    
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}    
	public long getDateDifference(String date1,String date2) {

		try{
			String startDate = date1;
			String endDate = date2;
			Date d1 = null;
			Date d2 = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			d1 = format.parse(startDate);
			d2 = format.parse(endDate);
			long duration  = d2.getTime() - d1.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			System.out.println(diffInDays);
			return diffInDays;
		}catch(Exception e) {
			System.out.println(e);
		}
		return 0;
	}
}    
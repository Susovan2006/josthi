package com.josthi.web;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeStampCheck {

	public static void main(String[] args) throws ParseException {
		System.out.println(getDateAdditionValue(30)+"");
		
		LocalDateTime newDate = LocalDateTime.now().plusDays(30);
		System.out.println(newDate);
		Timestamp timestamp = Timestamp.valueOf(newDate);
		System.out.println(getDateAdditionValue(30));
		//System.out.println(new Timestamp());

	}
	
	public static Timestamp getDateAdditionValue(int daysToAdd) throws ParseException {		
		LocalDateTime newDate = LocalDateTime.now().plusDays(daysToAdd);
		Timestamp newTimestamp = Timestamp.valueOf(newDate);
		return newTimestamp;
	}

}

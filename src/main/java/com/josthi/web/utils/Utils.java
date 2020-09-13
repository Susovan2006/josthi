package com.josthi.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	
	/**
	 * 
	 * @param type
	 * @param nextCount
	 * @return
	 */
	public static String getNextCustomerID(String type,int nextCount) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
		StringBuffer custId = new StringBuffer();
		if(type.equalsIgnoreCase("Admin")) {
			custId.append("AD");
		}else if(type.equalsIgnoreCase("Agent")) {
			custId.append("AG");
		}else if(type.equalsIgnoreCase("SubAgent")) {
			custId.append("SA");
		}else if(type.equalsIgnoreCase("RegUser")) {
			custId.append("RU");
		}else if(type.equalsIgnoreCase("Beneficiary")) {
			custId.append("BE");
		}	
		custId.append(formatter.format(new Date()));		
		custId.append(String.format("%03d", nextCount));
		
		return custId.toString();
		
	}

}

package com.josthi.web;

import java.text.DecimalFormat;

public class StringToDouble {

	public static void main(String[] args) {
		/*
		 * String stringLitersOfPetrol = "123.000000";
		 * System.out.println("string liters of petrol putting in preferences is "
		 * +stringLitersOfPetrol); Float
		 * litersOfPetrol=Float.parseFloat(stringLitersOfPetrol); DecimalFormat df = new
		 * DecimalFormat("0.00"); df.setMaximumFractionDigits(2); stringLitersOfPetrol =
		 * df.format(litersOfPetrol);
		 * System.out.println("liters of petrol before putting in editor : "
		 * +stringLitersOfPetrol);
		 */
		
		System.out.println(formattedCurrency("10023.000091"));

	}
	
	
	public static String formattedCurrency(String value) {		
		Float returnValue=Float.parseFloat(value);
		DecimalFormat df = new DecimalFormat("#,###.00");
		df.setMaximumFractionDigits(2);
		return df.format(returnValue);
		
	}

}

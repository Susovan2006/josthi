package com.josthi.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.josthi.web.utils.Utils;



public class CoreTest {

	public static void main(String[] args) {
		//String val = StringUtils.capitalize("sUSovaN".toLowerCase());
		System.out.println(Utils.convertToCamelCase("suSOvan SankAR gumTYa"));
		//getBeneficiaryCountFromDropdownVal
		System.out.println(Utils.getBeneficiaryCountFromDropdownVal("2BEN"));
		
		String timeStamp = new SimpleDateFormat("MMMMM d, yyyy").format(new Date());
		
		System.out.println(timeStamp);
		
		String TEST_STRING = "Susovan/Gumtya/";
		
		TEST_STRING = StringUtils.substring(TEST_STRING, 0, TEST_STRING.length() - 1);
		
		System.out.println(TEST_STRING);
		
		
		String dennis = "1500.00";
		double f = Double.parseDouble(dennis.replace(",",""));
		System.out.println(f);

		double priceDiscountForLongTerm = 0.00;
		double totalPriceAfterDiscount = 30000.00;
		int percentage = 20;
		System.out.println(percentage/100);
		if(percentage == 0) {
			priceDiscountForLongTerm = 0.00;
		}else {
			priceDiscountForLongTerm = ((double) ((percentage * totalPriceAfterDiscount)/100));
		}
		
		System.out.println(priceDiscountForLongTerm);
	}

}

package com.josthi.web;

public class PercentageCalculation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hi");
		double unitPriceBasePlan = 1000.00;
		int discountPercentage = 20;
		
		double discount = ((double) discountPercentage/100) * unitPriceBasePlan;
		System.out.println(discount+unitPriceBasePlan);
	}

}

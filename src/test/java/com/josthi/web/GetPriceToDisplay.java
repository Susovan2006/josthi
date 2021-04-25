package com.josthi.web;

import com.josthi.web.constants.Constant;
import com.josthi.web.utils.Utils;

public class GetPriceToDisplay {

	public static void main(String[] args) {
		double unitPriceBasePlan = 1000.00;//planDetailsDao.getBasePrice(Constant.PLAN_BASIC);
		double unitPriceSilverPlan = 1500.00; //planDetailsDao.getBasePrice(Constant.PLAN_SILVER);
		double unitPriceGoldPlan = 2500.00; //planDetailsDao.getBasePrice(Constant.PLAN_GOLD);
		
		int discountPercentage = 20;//planDetailsDao.getDiscountInPercentage(Constant.ONE_YEAR);
		
		float percentageFactor = ((float) discountPercentage )/100;
		
		double oneYearPriceForBasePlan = ((unitPriceBasePlan * 12) - (unitPriceBasePlan * 12 * percentageFactor))/12;
		double oneYearPriceForSilverPlan = ((unitPriceSilverPlan * 12) - (unitPriceSilverPlan * 12 * percentageFactor))/12;
		double oneYearPriceForGoldPlan = ((unitPriceGoldPlan * 12) - (unitPriceGoldPlan * 12 * percentageFactor))/ 12;
		
		
		String price = Utils.formattedCurrency(oneYearPriceForBasePlan+"");
		
		System.out.println(Utils.formattedCurrency(unitPriceBasePlan+"")+"/"+unitPriceSilverPlan+"/"+unitPriceGoldPlan);
		
		System.out.println(oneYearPriceForBasePlan+"/"+oneYearPriceForSilverPlan+"/"+oneYearPriceForGoldPlan);
		
		
		
		System.out.println(price);
		
		

	}

}

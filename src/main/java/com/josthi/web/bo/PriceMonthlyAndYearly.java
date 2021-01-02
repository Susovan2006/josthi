package com.josthi.web.bo;

public class PriceMonthlyAndYearly {
	
	private String basePlanMonthlyPrice;
	private String basePlanDiscountedPrice;
	
	private String silverPlanMonthlyPrice;
	private String silverPlanDiscountedPrice;
	
	private String goldPlanMonthlyPrice;
	private String goldPlanDiscountedPrice;
	
	private String baseAttributeForThymeleaf;
	private String silverAttributeForThymeleaf;
	private String goldAttributeForThymeleaf;
	
	private String yearlyDiscount;

	public String getBasePlanMonthlyPrice() {
		return basePlanMonthlyPrice;
	}

	public void setBasePlanMonthlyPrice(String basePlanMonthlyPrice) {
		this.basePlanMonthlyPrice = basePlanMonthlyPrice;
	}

	public String getBasePlanDiscountedPrice() {
		return basePlanDiscountedPrice;
	}

	public void setBasePlanDiscountedPrice(String basePlanDiscountedPrice) {
		this.basePlanDiscountedPrice = basePlanDiscountedPrice;
	}

	public String getSilverPlanMonthlyPrice() {
		return silverPlanMonthlyPrice;
	}

	public void setSilverPlanMonthlyPrice(String silverPlanMonthlyPrice) {
		this.silverPlanMonthlyPrice = silverPlanMonthlyPrice;
	}

	public String getSilverPlanDiscountedPrice() {
		return silverPlanDiscountedPrice;
	}

	public void setSilverPlanDiscountedPrice(String silverPlanDiscountedPrice) {
		this.silverPlanDiscountedPrice = silverPlanDiscountedPrice;
	}

	public String getGoldPlanMonthlyPrice() {
		return goldPlanMonthlyPrice;
	}

	public void setGoldPlanMonthlyPrice(String goldPlanMonthlyPrice) {
		this.goldPlanMonthlyPrice = goldPlanMonthlyPrice;
	}

	public String getGoldPlanDiscountedPrice() {
		return goldPlanDiscountedPrice;
	}

	public void setGoldPlanDiscountedPrice(String goldPlanDiscountedPrice) {
		this.goldPlanDiscountedPrice = goldPlanDiscountedPrice;
	}

	public String getYearlyDiscount() {
		return yearlyDiscount;
	}

	public void setYearlyDiscount(String yearlyDiscount) {
		this.yearlyDiscount = yearlyDiscount;
	}
	
	

	public String getBaseAttributeForThymeleaf() {
		return baseAttributeForThymeleaf;
	}

	public void setBaseAttributeForThymeleaf(String baseAttributeForThymeleaf) {
		this.baseAttributeForThymeleaf = baseAttributeForThymeleaf;
	}

	public String getSilverAttributeForThymeleaf() {
		return silverAttributeForThymeleaf;
	}

	public void setSilverAttributeForThymeleaf(String silverAttributeForThymeleaf) {
		this.silverAttributeForThymeleaf = silverAttributeForThymeleaf;
	}

	public String getGoldAttributeForThymeleaf() {
		return goldAttributeForThymeleaf;
	}

	public void setGoldAttributeForThymeleaf(String goldAttributeForThymeleaf) {
		this.goldAttributeForThymeleaf = goldAttributeForThymeleaf;
	}

	@Override
	public String toString() {
		return "PriceMonthlyAndYearly [basePlanMonthlyPrice=" + basePlanMonthlyPrice + ", basePlanDiscountedPrice="
				+ basePlanDiscountedPrice + ", silverPlanMonthlyPrice=" + silverPlanMonthlyPrice
				+ ", silverPlanDiscountedPrice=" + silverPlanDiscountedPrice + ", goldPlanMonthlyPrice="
				+ goldPlanMonthlyPrice + ", goldPlanDiscountedPrice=" + goldPlanDiscountedPrice
				+ ", baseAttributeForThymeleaf=" + baseAttributeForThymeleaf + ", silverAttributeForThymeleaf="
				+ silverAttributeForThymeleaf + ", goldAttributeForThymeleaf=" + goldAttributeForThymeleaf
				+ ", yearlyDiscount=" + yearlyDiscount + "]";
	}

	
	
	

}

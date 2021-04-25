package com.josthi.web.bo;

public class UnitFamilyPlanPrice {
	
	double priceForOneBeneficiary;
	double priceForTwoBeneficiary;
	double priceForThreeBeneficiary;
	
	
	public double getPriceForOneBeneficiary() {
		return priceForOneBeneficiary;
	}
	public void setPriceForOneBeneficiary(double priceForOneBeneficiary) {
		this.priceForOneBeneficiary = priceForOneBeneficiary;
	}
	public double getPriceForTwoBeneficiary() {
		return priceForTwoBeneficiary;
	}
	public void setPriceForTwoBeneficiary(double priceForTwoBeneficiary) {
		this.priceForTwoBeneficiary = priceForTwoBeneficiary;
	}
	public double getPriceForThreeBeneficiary() {
		return priceForThreeBeneficiary;
	}
	public void setPriceForThreeBeneficiary(double priceForThreeBeneficiary) {
		this.priceForThreeBeneficiary = priceForThreeBeneficiary;
	}
	@Override
	public String toString() {
		return "UnitFamilyPlanPrice [priceForOneBeneficiary=" + priceForOneBeneficiary + ", priceForTwoBeneficiary="
				+ priceForTwoBeneficiary + ", priceForThreeBeneficiary=" + priceForThreeBeneficiary + "]";
	}
	
	

}

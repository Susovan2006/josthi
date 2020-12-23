package com.josthi.web.bo;

import java.sql.Timestamp;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AjaxRestResponseForPriceCalculation {
	  private String status;
	  private String message;
	  private Timestamp planStartDate;
	  private Timestamp planEndDate;
	  private String planStartDateStr;
	  private String planEndDateStr;
	  private String planFinalPrice;
	  
	  private String planName;   				//Silver Plan.
	  private String planPricePerPersonPerMonth; //1000/-
	  
	  private String beneficiaryCount;			//2 Beneficiary
	  private String basePriceForTotalBeneficiary;  //1000 X 2/-
	  private double basePriceForTotalBeneficiaryDouble;
	  private String discountedPriceForTotalBeneficiary; //1500 /-
	  private double discountedPriceForTotalBeneficiaryDouble;
	  private String discountPercentageForFamilyPlan;
	  
	  private String planDuration;
	  private String basePriceForSelectedDuration;
	  private String discountedPriceForSelectedDuration;
	  
	  private String actualPlanPrice;
	  private String finalDiscountedPrice;	  
	  private String totalGain;
	  
	  private int offerId;
	  
	  
	  
	  
	  
	public AjaxRestResponseForPriceCalculation() {
		super();
	}


	public AjaxRestResponseForPriceCalculation(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public String getMessage() {
		return message;
	}





	public void setMessage(String message) {
		this.message = message;
	}




	public Timestamp getPlanStartDate() {
		return planStartDate;
	}


	public void setPlanStartDate(Timestamp planStartDate) {
		this.planStartDate = planStartDate;
	}


	public Timestamp getPlanEndDate() {
		return planEndDate;
	}


	public void setPlanEndDate(Timestamp planEndDate) {
		this.planEndDate = planEndDate;
	}


	


	public String getPlanStartDateStr() {
		return planStartDateStr;
	}


	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}


	public String getPlanEndDateStr() {
		return planEndDateStr;
	}


	public void setPlanEndDateStr(String planEndDateStr) {
		this.planEndDateStr = planEndDateStr;
	}


	public String getPlanFinalPrice() {
		return planFinalPrice;
	}





	public void setPlanFinalPrice(String planFinalPrice) {
		this.planFinalPrice = planFinalPrice;
	}





	public String getPlanName() {
		return planName;
	}





	public void setPlanName(String planName) {
		this.planName = planName;
	}





	public String getPlanPricePerPersonPerMonth() {
		return planPricePerPersonPerMonth;
	}





	public void setPlanPricePerPersonPerMonth(String planPricePerPersonPerMonth) {
		this.planPricePerPersonPerMonth = planPricePerPersonPerMonth;
	}





	public String getBeneficiaryCount() {
		return beneficiaryCount;
	}





	public void setBeneficiaryCount(String beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
	}





	public String getBasePriceForTotalBeneficiary() {
		return basePriceForTotalBeneficiary;
	}





	public void setBasePriceForTotalBeneficiary(String basePriceForTotalBeneficiary) {
		this.basePriceForTotalBeneficiary = basePriceForTotalBeneficiary;
	}


	



	public double getBasePriceForTotalBeneficiaryDouble() {
		return basePriceForTotalBeneficiaryDouble;
	}


	public void setBasePriceForTotalBeneficiaryDouble(double basePriceForTotalBeneficiaryDouble) {
		this.basePriceForTotalBeneficiaryDouble = basePriceForTotalBeneficiaryDouble;
	}


	public String getDiscountedPriceForTotalBeneficiary() {
		return discountedPriceForTotalBeneficiary;
	}





	public void setDiscountedPriceForTotalBeneficiary(String discountedPriceForTotalBeneficiary) {
		this.discountedPriceForTotalBeneficiary = discountedPriceForTotalBeneficiary;
	}





	public double getDiscountedPriceForTotalBeneficiaryDouble() {
		return discountedPriceForTotalBeneficiaryDouble;
	}


	public void setDiscountedPriceForTotalBeneficiaryDouble(double discountedPriceForTotalBeneficiaryDouble) {
		this.discountedPriceForTotalBeneficiaryDouble = discountedPriceForTotalBeneficiaryDouble;
	}


	public String getDiscountPercentageForFamilyPlan() {
		return discountPercentageForFamilyPlan;
	}





	public void setDiscountPercentageForFamilyPlan(String discountPercentageForFamilyPlan) {
		this.discountPercentageForFamilyPlan = discountPercentageForFamilyPlan;
	}





	public String getPlanDuration() {
		return planDuration;
	}





	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}





	public String getBasePriceForSelectedDuration() {
		return basePriceForSelectedDuration;
	}





	public void setBasePriceForSelectedDuration(String basePriceForSelectedDuration) {
		this.basePriceForSelectedDuration = basePriceForSelectedDuration;
	}





	public String getDiscountedPriceForSelectedDuration() {
		return discountedPriceForSelectedDuration;
	}





	public void setDiscountedPriceForSelectedDuration(String discountedPriceForSelectedDuration) {
		this.discountedPriceForSelectedDuration = discountedPriceForSelectedDuration;
	}





	public String getActualPlanPrice() {
		return actualPlanPrice;
	}





	public void setActualPlanPrice(String actualPlanPrice) {
		this.actualPlanPrice = actualPlanPrice;
	}





	public String getFinalDiscountedPrice() {
		return finalDiscountedPrice;
	}





	public void setFinalDiscountedPrice(String finalDiscountedPrice) {
		this.finalDiscountedPrice = finalDiscountedPrice;
	}





	public String getTotalGain() {
		return totalGain;
	}





	public void setTotalGain(String totalGain) {
		this.totalGain = totalGain;
	}


	public int getOfferId() {
		return offerId;
	}


	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}


	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	  
	}

package com.josthi.web.bo;

import java.util.Arrays;
import java.util.List;

public class PlanSubscriptionForUserBean {

	private String planID;
	private String beneficiaryCount;
	private String planDuration;
	private String[] beneficiaryIdArr;
	//private String beneficiaryDetails1;
	//private List<String> beneficiaryNameList;
	//private String beneficiaryDetails2;
	//private String beneficiaryDetails3;
	private String calculatedPrice;
	private String offerId;
	
	
	public String getPlanID() {
		return planID;
	}
	public void setPlanID(String planID) {
		this.planID = planID;
	}
	public String getBeneficiaryCount() {
		return beneficiaryCount;
	}
	public void setBeneficiaryCount(String beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
	}
	public String getPlanDuration() {
		return planDuration;
	}
	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}
	

	
	public String[] getBeneficiaryIdArr() {
		return beneficiaryIdArr;
	}
	public void setBeneficiaryIdArr(String[] beneficiaryIdArr) {
		this.beneficiaryIdArr = beneficiaryIdArr;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getCalculatedPrice() {
		return calculatedPrice;
	}
	public void setCalculatedPrice(String calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
	@Override
	public String toString() {
		return "PlanSubscriptionForUserBean [planID=" + planID + ", beneficiaryCount=" + beneficiaryCount
				+ ", planDuration=" + planDuration + ", beneficiaryIdArr=" + Arrays.toString(beneficiaryIdArr)
				+ ", calculatedPrice=" + calculatedPrice + ", offerId=" + offerId + "]";
	}
	
	
	
	
}

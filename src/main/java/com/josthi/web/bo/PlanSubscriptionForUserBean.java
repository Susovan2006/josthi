package com.josthi.web.bo;

import java.util.Arrays;

public class PlanSubscriptionForUserBean {

	private String planID;
	private String beneficiaryCount;
	private String planDuration;
	private String[] beneficiaryName;
	private String beneficiaryDetails1;
	private String beneficiaryDetails2;
	private String beneficiaryDetails3;
	private String calculatedPrice;
	
	
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
	public String[] getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String[] beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryDetails1() {
		return beneficiaryDetails1;
	}
	public void setBeneficiaryDetails1(String beneficiaryDetails1) {
		this.beneficiaryDetails1 = beneficiaryDetails1;
	}
	public String getBeneficiaryDetails2() {
		return beneficiaryDetails2;
	}
	public void setBeneficiaryDetails2(String beneficiaryDetails2) {
		this.beneficiaryDetails2 = beneficiaryDetails2;
	}
	public String getBeneficiaryDetails3() {
		return beneficiaryDetails3;
	}
	public void setBeneficiaryDetails3(String beneficiaryDetails3) {
		this.beneficiaryDetails3 = beneficiaryDetails3;
	}
	public String getCalculatedPrice() {
		return calculatedPrice;
	}
	public void setCalculatedPrice(String calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
	@Override
	public String toString() {
		return "PlansubscriptionForUserBean [planID=" + planID + ", beneficiaryCount=" + beneficiaryCount
				+ ", planDuration=" + planDuration + ", beneficiaryName=" + Arrays.toString(beneficiaryName)
				+ ", beneficiaryDetails1=" + beneficiaryDetails1 + ", beneficiaryDetails2=" + beneficiaryDetails2
				+ ", beneficiaryDetails3=" + beneficiaryDetails3 + ", calculatedPrice=" + calculatedPrice + "]";
	}
	
	
}

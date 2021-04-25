package com.josthi.web.bo;

import java.sql.Timestamp;

public class PurchasedPlanToDisplay {
	
	private Timestamp planStartDate ;
	private Timestamp planEndDate ;
	private String planStartDateStr ;
	private String planEndDateStr ;
	private String planName ;
	private String planDurationCode ;
	private String breakupRequestedFor ;
	private String finalDiscountedPrice ;
	private String planActive ;
	private String planComments;
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
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDurationCode() {
		return planDurationCode;
	}
	public void setPlanDurationCode(String planDurationCode) {
		this.planDurationCode = planDurationCode;
	}
	public String getBreakupRequestedFor() {
		return breakupRequestedFor;
	}
	public void setBreakupRequestedFor(String breakupRequestedFor) {
		this.breakupRequestedFor = breakupRequestedFor;
	}
	public String getFinalDiscountedPrice() {
		return finalDiscountedPrice;
	}
	public void setFinalDiscountedPrice(String finalDiscountedPrice) {
		this.finalDiscountedPrice = finalDiscountedPrice;
	}
	public String getPlanActive() {
		return planActive;
	}
	public void setPlanActive(String planActive) {
		this.planActive = planActive;
	}
	public String getPlanComments() {
		return planComments;
	}
	public void setPlanComments(String planComments) {
		this.planComments = planComments;
	}
	@Override
	public String toString() {
		return "PurchasedPlanToDisplay [planStartDate=" + planStartDate + ", planEndDate=" + planEndDate
				+ ", planStartDateStr=" + planStartDateStr + ", planEndDateStr=" + planEndDateStr + ", planName="
				+ planName + ", planDurationCode=" + planDurationCode + ", breakupRequestedFor=" + breakupRequestedFor
				+ ", finalDiscountedPrice=" + finalDiscountedPrice + ", planActive=" + planActive + ", planComments="
				+ planComments + "]";
	}
	
	


}

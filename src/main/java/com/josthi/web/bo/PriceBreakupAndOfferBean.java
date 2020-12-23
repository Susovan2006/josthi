package com.josthi.web.bo ;
import java.sql.Timestamp ;
import java.util.List;


public class PriceBreakupAndOfferBean {
	private Integer offerId ;
	private Timestamp planStartDate ;
	private Timestamp planEndDate ;
	private String planName ;
	private String planDurationCode ;
	private String planBeneficiaryCountCode ;
	private String breakupRequestedBy ;
	private String breakupRequestedFor ;
	private String planPricePerPersonPerMonth ;
	private String benefeciciaryCountToDisplay ;
	private String basePriceForTotalBenefeciary ;
	private String discountedPriceForTotalBenefeciary ;
	private String planDurationToDisplay ;
	private String discountPercentageForFamilyPlan ;
	private String basePriceForSelectedDuration ;
	private String discountedPriceForSelectedDuration ;
	private String finalDiscountedPrice ;
	private String totalGain ;
	private Timestamp breakupCreatedOn ;


	public Integer getOfferId(){
		return offerId ;
	}
	public void setOfferId(Integer offerId){
		this.offerId = offerId ;
	}
	public Timestamp getPlanStartDate(){
		return planStartDate ;
	}
	public void setPlanStartDate(Timestamp planStartDate){
		this.planStartDate = planStartDate ;
	}
	public Timestamp getPlanEndDate(){
		return planEndDate ;
	}
	public void setPlanEndDate(Timestamp planEndDate){
		this.planEndDate = planEndDate ;
	}
	public String getPlanName(){
		return planName ;
	}
	public void setPlanName(String planName){
		this.planName = planName ;
	}
	public String getPlanDurationCode(){
		return planDurationCode ;
	}
	public void setPlanDurationCode(String planDurationCode){
		this.planDurationCode = planDurationCode ;
	}
	public String getPlanBeneficiaryCountCode(){
		return planBeneficiaryCountCode ;
	}
	public void setPlanBeneficiaryCountCode(String planBeneficiaryCountCode){
		this.planBeneficiaryCountCode = planBeneficiaryCountCode ;
	}
	public String getBreakupRequestedBy(){
		return breakupRequestedBy ;
	}
	public void setBreakupRequestedBy(String breakupRequestedBy){
		this.breakupRequestedBy = breakupRequestedBy ;
	}
	public String getBreakupRequestedFor(){
		return breakupRequestedFor ;
	}
	public void setBreakupRequestedFor(String breakupRequestedFor){
		this.breakupRequestedFor = breakupRequestedFor ;
	}
	public String getPlanPricePerPersonPerMonth(){
		return planPricePerPersonPerMonth ;
	}
	public void setPlanPricePerPersonPerMonth(String planPricePerPersonPerMonth){
		this.planPricePerPersonPerMonth = planPricePerPersonPerMonth ;
	}
	public String getBenefeciciaryCountToDisplay(){
		return benefeciciaryCountToDisplay ;
	}
	public void setBenefeciciaryCountToDisplay(String benefeciciaryCountToDisplay){
		this.benefeciciaryCountToDisplay = benefeciciaryCountToDisplay ;
	}
	public String getBasePriceForTotalBenefeciary(){
		return basePriceForTotalBenefeciary ;
	}
	public void setBasePriceForTotalBenefeciary(String basePriceForTotalBenefeciary){
		this.basePriceForTotalBenefeciary = basePriceForTotalBenefeciary ;
	}
	public String getDiscountedPriceForTotalBenefeciary(){
		return discountedPriceForTotalBenefeciary ;
	}
	public void setDiscountedPriceForTotalBenefeciary(String discountedPriceForTotalBenefeciary){
		this.discountedPriceForTotalBenefeciary = discountedPriceForTotalBenefeciary ;
	}
	public String getPlanDurationToDisplay(){
		return planDurationToDisplay ;
	}
	public void setPlanDurationToDisplay(String planDurationToDisplay){
		this.planDurationToDisplay = planDurationToDisplay ;
	}
	public String getDiscountPercentageForFamilyPlan(){
		return discountPercentageForFamilyPlan ;
	}
	public void setDiscountPercentageForFamilyPlan(String discountPercentageForFamilyPlan){
		this.discountPercentageForFamilyPlan = discountPercentageForFamilyPlan ;
	}
	public String getBasePriceForSelectedDuration(){
		return basePriceForSelectedDuration ;
	}
	public void setBasePriceForSelectedDuration(String basePriceForSelectedDuration){
		this.basePriceForSelectedDuration = basePriceForSelectedDuration ;
	}
	public String getDiscountedPriceForSelectedDuration(){
		return discountedPriceForSelectedDuration ;
	}
	public void setDiscountedPriceForSelectedDuration(String discountedPriceForSelectedDuration){
		this.discountedPriceForSelectedDuration = discountedPriceForSelectedDuration ;
	}
	public String getFinalDiscountedPrice(){
		return finalDiscountedPrice ;
	}
	public void setFinalDiscountedPrice(String finalDiscountedPrice){
		this.finalDiscountedPrice = finalDiscountedPrice ;
	}
	public String getTotalGain(){
		return totalGain ;
	}
	public void setTotalGain(String totalGain){
		this.totalGain = totalGain ;
	}
	public Timestamp getBreakupCreatedOn(){
		return breakupCreatedOn ;
	}
	public void setBreakupCreatedOn(Timestamp breakupCreatedOn){
		this.breakupCreatedOn = breakupCreatedOn ;
	}
	@Override
	public String toString() {
		return "PriceBreakupAndOfferBean [offerId=" + offerId + ", planStartDate=" + planStartDate + ", planEndDate="
				+ planEndDate + ", planName=" + planName + ", planDurationCode=" + planDurationCode
				+ ", planBeneficiaryCountCode=" + planBeneficiaryCountCode + ", breakupRequestedBy="
				+ breakupRequestedBy + ", breakupRequestedFor=" + breakupRequestedFor + ", planPricePerPersonPerMonth="
				+ planPricePerPersonPerMonth + ", benefeciciaryCountToDisplay=" + benefeciciaryCountToDisplay
				+ ", basePriceForTotalBenefeciary=" + basePriceForTotalBenefeciary
				+ ", discountedPriceForTotalBenefeciary=" + discountedPriceForTotalBenefeciary
				+ ", planDurationToDisplay=" + planDurationToDisplay + ", discountPercentageForFamilyPlan="
				+ discountPercentageForFamilyPlan + ", basePriceForSelectedDuration=" + basePriceForSelectedDuration
				+ ", discountedPriceForSelectedDuration=" + discountedPriceForSelectedDuration
				+ ", finalDiscountedPrice=" + finalDiscountedPrice + ", totalGain=" + totalGain + ", breakupCreatedOn="
				+ breakupCreatedOn + "]";
	}
	
	
	
	
}

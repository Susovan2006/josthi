package com.josthi.web.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlanInvoiceEmailBean {
	
	private String invoiceId ;
	private String invoiceCreationDate ;
	private String planStartDate;
	private String planEndDate ;
	private String planName ;
	private String planDuration ;
	private String beneficiaryCount ;
	private String assignedAgentId;
	private String assignedAgentName;
	
	// Josthi Address
	private String  josthiAddressLine1;
	private String  josthiAddressLine2;
	private String  josthiCity;
	private String  josthiPin;
	private String  josthiState;
	private String  josthiEmail;
	private String  josthiContactNumber;
	//Customer Address
	private String  hostCustomerName;
	private String  hostCustomerAddressLine1;
	private String  hostCustomerAddressLine2;
	private String  hostCustomerCityTown;
	private String  hostCustomerState;
	private String  hostCustomerZipPin;
	private String  hostCustomerCountry;
	private String  hostCustomerEmail;
	
	//Payment Details
	private String  paymentMethod;
	private String  paymentId;
	private String  paymentStatus;
	
	//Benificiary Details
	private String  beneficiaryNames;
	private String  beneficiaryIds;
	
	//Plan Details eg: Gold Plan/3 Beneficiary/ 3 Months
	private String planDetails;
	private String actualPlanPrice;
	private String familyDiscountfor;
	private String familyDiscountPrice;
	private String longTermDiscountFor;
	private String longTermDiscountPrice;
	
	private String finalPrice;
	private String finalDiscount;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceCreationDate() {
		return invoiceCreationDate;
	}
	public void setInvoiceCreationDate(String invoiceCreationDate) {
		this.invoiceCreationDate = invoiceCreationDate;
	}
	public String getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDuration() {
		return planDuration;
	}
	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}
	public String getBeneficiaryCount() {
		return beneficiaryCount;
	}
	public void setBeneficiaryCount(String beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
	}
	public String getAssignedAgentId() {
		return assignedAgentId;
	}
	public void setAssignedAgentId(String assignedAgentId) {
		this.assignedAgentId = assignedAgentId;
	}
	public String getAssignedAgentName() {
		return assignedAgentName;
	}
	public void setAssignedAgentName(String assignedAgentName) {
		this.assignedAgentName = assignedAgentName;
	}
	public String getJosthiAddressLine1() {
		return josthiAddressLine1;
	}
	public void setJosthiAddressLine1(String josthiAddressLine1) {
		this.josthiAddressLine1 = josthiAddressLine1;
	}
	public String getJosthiAddressLine2() {
		return josthiAddressLine2;
	}
	public void setJosthiAddressLine2(String josthiAddressLine2) {
		this.josthiAddressLine2 = josthiAddressLine2;
	}
	public String getJosthiCity() {
		return josthiCity;
	}
	public void setJosthiCity(String josthiCity) {
		this.josthiCity = josthiCity;
	}
	public String getJosthiPin() {
		return josthiPin;
	}
	public void setJosthiPin(String josthiPin) {
		this.josthiPin = josthiPin;
	}
	public String getJosthiState() {
		return josthiState;
	}
	public void setJosthiState(String josthiState) {
		this.josthiState = josthiState;
	}
	public String getJosthiEmail() {
		return josthiEmail;
	}
	public void setJosthiEmail(String josthiEmail) {
		this.josthiEmail = josthiEmail;
	}
	public String getJosthiContactNumber() {
		return josthiContactNumber;
	}
	public void setJosthiContactNumber(String josthiContactNumber) {
		this.josthiContactNumber = josthiContactNumber;
	}
	public String getHostCustomerAddressLine1() {
		return hostCustomerAddressLine1;
	}
	public void setHostCustomerAddressLine1(String hostCustomerAddressLine1) {
		this.hostCustomerAddressLine1 = hostCustomerAddressLine1;
	}
	public String getHostCustomerAddressLine2() {
		return hostCustomerAddressLine2;
	}
	public void setHostCustomerAddressLine2(String hostCustomerAddressLine2) {
		this.hostCustomerAddressLine2 = hostCustomerAddressLine2;
	}
	public String getHostCustomerState() {
		return hostCustomerState;
	}
	public void setHostCustomerState(String hostCustomerState) {
		this.hostCustomerState = hostCustomerState;
	}
	public String getHostCustomerZipPin() {
		return hostCustomerZipPin;
	}
	public void setHostCustomerZipPin(String hostCustomerZipPin) {
		this.hostCustomerZipPin = hostCustomerZipPin;
	}
	public String getHostCustomerCountry() {
		return hostCustomerCountry;
	}
	public void setHostCustomerCountry(String hostCustomerCountry) {
		this.hostCustomerCountry = hostCustomerCountry;
	}
	public String getHostCustomerEmail() {
		return hostCustomerEmail;
	}
	public void setHostCustomerEmail(String hostCustomerEmail) {
		this.hostCustomerEmail = hostCustomerEmail;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	
	public String getBeneficiaryNames() {
		return beneficiaryNames;
	}
	public void setBeneficiaryNames(String beneficiaryNames) {
		this.beneficiaryNames = beneficiaryNames;
	}
	public String getBeneficiaryIds() {
		return beneficiaryIds;
	}
	public void setBeneficiaryIds(String beneficiaryIds) {
		this.beneficiaryIds = beneficiaryIds;
	}
	public String getPlanDetails() {
		return planDetails;
	}
	public void setPlanDetails(String planDetails) {
		this.planDetails = planDetails;
	}
	public String getActualPlanPrice() {
		return actualPlanPrice;
	}
	public void setActualPlanPrice(String actualPlanPrice) {
		this.actualPlanPrice = actualPlanPrice;
	}
	public String getFamilyDiscountfor() {
		return familyDiscountfor;
	}
	public void setFamilyDiscountfor(String familyDiscountfor) {
		this.familyDiscountfor = familyDiscountfor;
	}
	public String getFamilyDiscountPrice() {
		return familyDiscountPrice;
	}
	public void setFamilyDiscountPrice(String familyDiscountPrice) {
		this.familyDiscountPrice = familyDiscountPrice;
	}
	public String getLongTermDiscountFor() {
		return longTermDiscountFor;
	}
	public void setLongTermDiscountFor(String longTermDiscountFor) {
		this.longTermDiscountFor = longTermDiscountFor;
	}
	public String getLongTermDiscountPrice() {
		return longTermDiscountPrice;
	}
	public void setLongTermDiscountPrice(String longTermDiscountPrice) {
		this.longTermDiscountPrice = longTermDiscountPrice;
	}
	public String getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getFinalDiscount() {
		return finalDiscount;
	}
	public void setFinalDiscount(String finalDiscount) {
		this.finalDiscount = finalDiscount;
	}
	
	
	
	
	public String getHostCustomerCityTown() {
		return hostCustomerCityTown;
	}
	public void setHostCustomerCityTown(String hostCustomerCityTown) {
		this.hostCustomerCityTown = hostCustomerCityTown;
	}
	public String getHostCustomerName() {
		return hostCustomerName;
	}
	public void setHostCustomerName(String hostCustomerName) {
		this.hostCustomerName = hostCustomerName;
	}
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	
	

}

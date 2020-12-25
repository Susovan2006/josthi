package com.josthi.web.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceInvoiceBean {
	
	private String invoiceId ;
	private String invoiceCreationDate ;
	private String servicePurchaseDate;
	
	private String serviceRequestedBy ;
	private String serviceRequestedFor ;
	private String serviceAssignedTo ; //need to get the Name.
	private String serviceRequestedOn;
	private String serviceType;
	private String serviceCode;
	private String serviceDescription;
	
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




	public String getServicePurchaseDate() {
		return servicePurchaseDate;
	}




	public void setServicePurchaseDate(String servicePurchaseDate) {
		this.servicePurchaseDate = servicePurchaseDate;
	}




	public String getServiceRequestedBy() {
		return serviceRequestedBy;
	}




	public void setServiceRequestedBy(String serviceRequestedBy) {
		this.serviceRequestedBy = serviceRequestedBy;
	}




	public String getServiceRequestedFor() {
		return serviceRequestedFor;
	}




	public void setServiceRequestedFor(String serviceRequestedFor) {
		this.serviceRequestedFor = serviceRequestedFor;
	}




	public String getServiceAssignedTo() {
		return serviceAssignedTo;
	}




	public void setServiceAssignedTo(String serviceAssignedTo) {
		this.serviceAssignedTo = serviceAssignedTo;
	}




	public String getServiceRequestedOn() {
		return serviceRequestedOn;
	}




	public void setServiceRequestedOn(String serviceRequestedOn) {
		this.serviceRequestedOn = serviceRequestedOn;
	}




	public String getServiceType() {
		return serviceType;
	}




	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}




	public String getServiceCode() {
		return serviceCode;
	}




	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}




	public String getServiceDescription() {
		return serviceDescription;
	}




	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
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




	public String getHostCustomerName() {
		return hostCustomerName;
	}




	public void setHostCustomerName(String hostCustomerName) {
		this.hostCustomerName = hostCustomerName;
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




	public String getHostCustomerCityTown() {
		return hostCustomerCityTown;
	}




	public void setHostCustomerCityTown(String hostCustomerCityTown) {
		this.hostCustomerCityTown = hostCustomerCityTown;
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




	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	
	

}

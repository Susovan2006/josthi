package com.josthi.web.bo;

public class PurchaseHistoryBean {
	
	private String purchaseId;
	private String purchaseItem;
	private String purchaseDetails;
	private String purchaseDate;
	private String paymentStatus;
	private String paymentId;
	private String priceInInr;
	private String requestedBy;
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getPurchaseItem() {
		return purchaseItem;
	}
	public void setPurchaseItem(String purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
	public String getPurchaseDetails() {
		return purchaseDetails;
	}
	public void setPurchaseDetails(String purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPriceInInr() {
		return priceInInr;
	}
	public void setPriceInInr(String priceInInr) {
		this.priceInInr = priceInInr;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	@Override
	public String toString() {
		return "PurchaseHistoryBean [purchaseId=" + purchaseId + ", purchaseItem=" + purchaseItem + ", purchaseDetails="
				+ purchaseDetails + ", purchaseDate=" + purchaseDate + ", paymentStatus=" + paymentStatus
				+ ", paymentId=" + paymentId + ", priceInInr=" + priceInInr + ", requestedBy=" + requestedBy + "]";
	}
	
	
	

}

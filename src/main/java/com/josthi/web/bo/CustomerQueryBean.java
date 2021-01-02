package com.josthi.web.bo;

public class CustomerQueryBean {
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String customerNotes;
	
	
	
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerNotes() {
		return customerNotes;
	}
	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}
	@Override
	public String toString() {
		return "CustomerQueryBean [customerFirstName=" + customerFirstName + ", customerLastName=" + customerLastName
				+ ", customerEmail=" + customerEmail + ", customerNotes=" + customerNotes + "]";
	}
	
	
	

}

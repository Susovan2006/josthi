package com.josthi.web.bo;

public class UserRegistrationBean {
	
	private String customerID;
	private String firstName;
	private String lastName;
	private String validEmailId;
	private String wordApp;
	private String confirmWordApp;
	private boolean licAgreementChecked;
	private String areaOfCoverage;
	private String userType;
	
	
	

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getConfirmWordApp() {
		return confirmWordApp;
	}
	public void setConfirmWordApp(String confirmWordApp) {
		this.confirmWordApp = confirmWordApp;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getValidEmailId() {
		return validEmailId;
	}
	public void setValidEmailId(String validEmailId) {
		this.validEmailId = validEmailId;
	}
	public String getWordApp() {
		return wordApp;
	}
	public void setWordApp(String wordApp) {
		this.wordApp = wordApp;
	}
	
	
	
	public boolean isLicAgreementChecked() {
		return licAgreementChecked;
	}
	public void setLicAgreementChecked(boolean licAgreementChecked) {
		this.licAgreementChecked = licAgreementChecked;
	}
	public String getAreaOfCoverage() {
		return areaOfCoverage;
	}
	public void setAreaOfCoverage(String areaOfCoverage) {
		this.areaOfCoverage = areaOfCoverage;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	@Override
	public String toString() {
		return "UserRegistrationBean [firstName=" + firstName + ", lastName=" + lastName + ", validEmailId="
				+ validEmailId + ", wordApp=" + wordApp + ", licAgreementChecked=" + licAgreementChecked
				+ ", areaOfCoverage=" + areaOfCoverage + "]";
	}
	

	
	
	

}

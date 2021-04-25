package com.josthi.web.bo;

public class AgentRegistrationBean {
	
	private String agentID;
	private String firstName;
	private String lastName;
	private String emailID;
	private String contactNo;
	private String password;
	private String confirmPassword;
	private String agentRequest;
	private String coverageArea;
	private String agentEnqueryDetails;
	
	
	
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
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
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getAgentRequest() {
		return agentRequest;
	}
	public void setAgentRequest(String agentRequest) {
		this.agentRequest = agentRequest;
	}
	public String getCoverageArea() {
		return coverageArea;
	}
	public void setCoverageArea(String coverageArea) {
		this.coverageArea = coverageArea;
	}
	public String getAgentEnqueryDetails() {
		return agentEnqueryDetails;
	}
	public void setAgentEnqueryDetails(String agentEnqueryDetails) {
		this.agentEnqueryDetails = agentEnqueryDetails;
	}
	@Override
	public String toString() {
		return "AgentRegistrationBean [firstName=" + firstName + ", lastName=" + lastName + ", emailID=" + emailID
				+ ", contactNo=" + contactNo + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", agentRequest=" + agentRequest + ", coverageArea=" + coverageArea + ", agentEnqueryDetails="
				+ agentEnqueryDetails + "]";
	}
	
	
	
	
	

}

package com.josthi.web.bo ;
import java.sql.Timestamp ;
import java.util.List;



public class UserDetailsBeanForProfile {
	private String customerId ;
	private String useridEmail ;
	private Timestamp registrationDateTime ;
	private String registrationDateTimeStr ;
	private String status ;
	private String loginStatus ;
	private String role ;
	private String verifiedUser;
	private String firstName;
	private String lastName;
	private String cityTown;
	private String country;
	private String state;
	private String zipPin;
	
	
	
	public String getZipPin() {
		return zipPin;
	}
	public void setZipPin(String zipPin) {
		this.zipPin = zipPin;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getUseridEmail() {
		return useridEmail;
	}
	public void setUseridEmail(String useridEmail) {
		this.useridEmail = useridEmail;
	}
	public Timestamp getRegistrationDateTime() {
		return registrationDateTime;
	}
	public void setRegistrationDateTime(Timestamp registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}
	public String getRegistrationDateTimeStr() {
		return registrationDateTimeStr;
	}
	public void setRegistrationDateTimeStr(String registrationDateTimeStr) {
		this.registrationDateTimeStr = registrationDateTimeStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getVerifiedUser() {
		return verifiedUser;
	}
	public void setVerifiedUser(String verifiedUser) {
		this.verifiedUser = verifiedUser;
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
	public String getCityTown() {
		return cityTown;
	}
	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "UserDetailsBeanForProfile [customerId=" + customerId + ", useridEmail=" + useridEmail
				+ ", registrationDateTime=" + registrationDateTime + ", registrationDateTimeStr="
				+ registrationDateTimeStr + ", status=" + status + ", loginStatus=" + loginStatus + ", role=" + role
				+ ", verifiedUser=" + verifiedUser + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", cityTown=" + cityTown + ", country=" + country + ", state=" + state + "]";
	}
	
	
	
	
	
	
}

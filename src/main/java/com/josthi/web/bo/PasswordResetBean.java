package com.josthi.web.bo;

public class PasswordResetBean {
	
	private String oldPassword;
	private String newPassword;
	private String newConfirmPassword;
	private String userID;
	private String role;
	private String emailId;
	private String otp;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewConfirmPassword() {
		return newConfirmPassword;
	}
	public void setNewConfirmPassword(String newConfirmPassword) {
		this.newConfirmPassword = newConfirmPassword;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "PasswordResetBean [oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ ", newConfirmPassword=" + newConfirmPassword + ", userID=" + userID + ", role=" + role + ", emailId="
				+ emailId + ", otp=" + otp + "]";
	}

	
	
	
	
	
	

}

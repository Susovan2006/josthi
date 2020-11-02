package com.josthi.web.bo;

public class UserSessionBean {
      private String userName;
      private String userRole;
      private String userEmailId;
      private String userProfileImagePath;
      private String userStatus;
      private String verifiedUser;
      private boolean userSessionValid;
      private String customerId;
      
      
      
      
      

	public UserSessionBean(String userName, String userRole, String userEmailId, String userProfileImagePath,
			String userStatus, String verifiedUser, boolean userSessionValid, String customerId) {
		super();
		this.userName = userName;
		this.userRole = userRole;
		this.userEmailId = userEmailId;
		this.userProfileImagePath = userProfileImagePath;
		this.userStatus = userStatus;
		this.verifiedUser = verifiedUser;
		this.userSessionValid = userSessionValid;
		this.customerId = customerId;
	}
	
	
	
	public String getVerifiedUser() {
		return verifiedUser;
	}
	public void setVerifiedUser(String verifiedUser) {
		this.verifiedUser = verifiedUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserProfileImagePath() {
		return userProfileImagePath;
	}
	public void setUserProfileImagePath(String userProfileImagePath) {
		this.userProfileImagePath = userProfileImagePath;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public boolean isUserSessionValid() {
		return userSessionValid;
	}
	public void setUserSessionValid(boolean userSessionValid) {
		this.userSessionValid = userSessionValid;
	}	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	@Override
	public String toString() {
		return "UserSessionBean [userName=" + userName + ", userRole=" + userRole + ", userEmailId=" + userEmailId
				+ ", userProfileImagePath=" + userProfileImagePath + ", userStatus=" + userStatus + ", verifiedUser="
				+ verifiedUser + ", userSessionValid=" + userSessionValid + ", customerId=" + customerId + "]";
	}
      
	
	
	
      
      
}

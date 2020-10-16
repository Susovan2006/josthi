package com.josthi.web.bo;

public class UserSessionBean {
      private String userName;
      private String userRole;
      private String userEmailId;
      private String userProfileImagePath;
      private String userStatus;
      private String userType;
      private boolean userSessionValid;
      
      
      
      
      
	public UserSessionBean(String userName, String userRole, String userEmailId, String userProfileImagePath,
			String userStatus, String userType, boolean userSessionValid) {
		super();
		this.userName = userName;
		this.userRole = userRole;
		this.userEmailId = userEmailId;
		this.userProfileImagePath = userProfileImagePath;
		this.userStatus = userStatus;
		this.userType = userType;
		this.userSessionValid = userSessionValid;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
      
      
      
}

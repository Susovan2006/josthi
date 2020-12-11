package com.josthi.web.service;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.SchedulerTimerBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserAuthService {
	
	
	public UserAuthBo getValidUser(String uid, String password) throws Exception;

	public int isValidUserID(String emailID);

	public UserAuthBo getValidUser(String emailID);

	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

	public int getNextID();

	public String getUserDetails(String customerId);

	public boolean resetUserIdGenTable();

	public boolean removeTempLockFromUserAccount();

	public boolean isValidPassword(String emailId, String wordApp) throws Exception;

	public boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception;

	public boolean validateOTP(PasswordResetBean passwordResetBean) throws Exception;

	public boolean updateOtp(String customerID, String emailId, String otp) throws Exception;

	public boolean isOtpFrequencyMatching(String userID) throws Exception;

	public UserPreferencesBean getUserPref(String userId) throws Exception;

	public boolean updateUserPreperences(UserPreferencesBean userPreferencesBean, String userId) throws Exception;

	public boolean saveUserPref(String customerID, String alertType, String value) throws Exception;

	public UserAuthBo getProfileDisplayDetails(String userID) throws Exception;

	public String getEmailId(String hostUserId);

	//public void updateSchedulerDetails(SchedulerTimerBean schedulerTimerBean);

	//public boolean registerNewUser(UserRegistrationBean userRegistrationBean);

}

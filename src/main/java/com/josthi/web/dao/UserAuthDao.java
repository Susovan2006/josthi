package com.josthi.web.dao;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserPreferencesBean;

import java.sql.Timestamp;

public interface UserAuthDao {

	UserAuthBo getValidUser(String uid, String password) throws Exception;

	UserAuthBo getValidUser(String emailID);

	int getValidUserCount(String emailID);

	boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

	int getNextID();

	String getUserFirstAndLastName(String customerId);
	
	String getEmailId(String customerId);

	boolean resetUserIdGenTable();

	boolean removeTempLockFromUserAccount();

	boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception;

	boolean isValidOtp(String userID, String otp) throws Exception;

	void updateUserValidationStatus(String userID) throws Exception;

	boolean updateOtp(String customerID, String emailId, String otp) throws Exception ;

	Timestamp getOTPLastUpdateTimeStamp(String userID) throws Exception;

	UserPreferencesBean getUserPref(String userId) throws Exception;

	boolean updateUserPreperences(UserPreferencesBean userPreferencesBean, String userId) throws Exception;

	boolean updateUserPref(String customerID, String alertType, String value) throws Exception;

	void saveDeafultUserPref(String userId) throws Exception;

	UserAuthBo getProfileDisplayDetails(String userID) throws Exception;

	String getAgentBasedOnBeneficiaryId(String beneficiaryId) throws Exception;

	

}

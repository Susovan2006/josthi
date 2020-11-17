package com.josthi.web.dao;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserAuthBo;
import java.sql.Timestamp;

public interface UserAuthDao {

	UserAuthBo getValidUser(String uid, String password);

	UserAuthBo getValidUser(String emailID);

	int getValidUserCount(String emailID);

	boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

	int getNectID();

	String getUserFirstAndLastName(String customerId);

	boolean resetUserIdGenTable();

	boolean removeTempLockFromUserAccount();

	boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception;

	boolean isValidOtp(String userID, String otp) throws Exception;

	void updateUserValidationStatus(String userID) throws Exception;

	boolean updateOtp(String customerID, String emailId, String otp) throws Exception ;

	Timestamp getOTPLastUpdateTimeStamp(String userID) throws Exception;

}

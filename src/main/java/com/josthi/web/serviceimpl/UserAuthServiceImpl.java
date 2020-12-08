package com.josthi.web.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService{

	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
	@Autowired
	public UserAuthDao userAuthDao;
	
	@Override
	public UserAuthBo getValidUser(String uid, String password) throws Exception{
		UserAuthBo userAuthBo = userAuthDao.getValidUser(uid,password);		
		return userAuthBo;
	}

	@Override
	public int isValidUserID(String emailID) {
		return userAuthDao.getValidUserCount(emailID);
	}

	@Override
	public UserAuthBo getValidUser(String emailID) {
		return userAuthDao.getValidUser(emailID);
	}

	@Override
	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid) {
		return userAuthDao.updateLoginStatus(userDetailsOnUid);		
	}

	@Override
	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails) {
		return userAuthDao.updateLoginStatusOnSuccess(userDetails);
	}

	@Override
	public int getNextID() {
		return userAuthDao.getNextID();
	}

	@Override
	public String getUserDetails(String customerId) {
		return Utils.convertToCamelCase(userAuthDao.getUserFirstAndLastName(customerId));
	}

	@Override
	public boolean resetUserIdGenTable() {
		return userAuthDao.resetUserIdGenTable();
	}

	@Override
	public boolean removeTempLockFromUserAccount() {
		// TODO Auto-generated method stub
		return userAuthDao.removeTempLockFromUserAccount();
	}

	@Override
	public boolean isValidPassword(String emailId, String wordApp) throws Exception {
		try {
			UserAuthBo userAuthBo = userAuthDao.getValidUser(emailId,wordApp);	
			if(userAuthBo.getWordapp().length()>0) {
				return true;
			}else {
				throw new UserExceptionInvalidData("Looks like you entered incorrect password in the current Password filed.");
			}
		}catch(Exception ex) {
			throw new UserExceptionInvalidData("Looks like you entered incorrect password in the current Password filed.");
		}
	}

	@Override
	public boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception {
		passwordResetBean.setOldPassword(Security.encrypt(passwordResetBean.getOldPassword()));
		passwordResetBean.setNewPassword(Security.encrypt(passwordResetBean.getNewPassword()));
		passwordResetBean.setNewConfirmPassword(Security.encrypt(passwordResetBean.getNewConfirmPassword()));
		return userAuthDao.updatePassword(passwordResetBean);
	}

	@Override
	public boolean validateOTP(PasswordResetBean passwordResetBean) throws Exception {
		String otp = passwordResetBean.getOtp().trim();
		try {
			if(userAuthDao.isValidOtp(passwordResetBean.getUserID(), otp)) {
				userAuthDao.updateUserValidationStatus(passwordResetBean.getUserID());
				return true;
			}else {
				return false;
			}
		}catch(Exception ex) {
			//throw new UserExceptionInvalidData("OTP Validation Failed, re-validate the OTP or regenerate the OTP. ");
			throw ex;
		}
	}

	@Override
	public boolean updateOtp(String customerID, String emailId, String otp) throws Exception {
		try {
			if(userAuthDao.updateOtp(customerID, emailId, otp)) {
				return true;
			}else {
				throw new UserExceptionInvalidData("OTP Update Failed, Try again later.");
			}
		}catch(UserExceptionInvalidData ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean isOtpFrequencyMatching(String userID) throws Exception {
		try {
			Timestamp lastUpdateTime = userAuthDao.getOTPLastUpdateTimeStamp(userID);
			Timestamp now = new Timestamp(new Date().getTime());
			
			if( lastUpdateTime != null && Utils.compareTwoTimeStamps(now, lastUpdateTime) > 1) {
				//System.out.println("######"+lastUpdateTime.toString());
				return true;
			}else {
				return false;
			}

		}catch(Exception ex) {
			logger.error(ex.getMessage());
			throw new UserExceptionInvalidData("Error Occured while Processing the OTP, please try again later or Contact the Service Desk.");
			//return false;
		}
		
	}

	@Override
	public UserPreferencesBean getUserPref(String userId) throws Exception {
		try {
			UserPreferencesBean userPref = userAuthDao.getUserPref(userId);
			if(userPref == null) {
				userAuthDao.saveDeafultUserPref(userId);
				return new UserPreferencesBean("English","106IST",false, false, false);
			}else {
				return userPref;
			}
		}catch(Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean updateUserPreperences(UserPreferencesBean userPreferencesBean, String userId) throws Exception {
		try {
			if(userAuthDao.updateUserPreperences(userPreferencesBean, userId)) {
				return true;
			}else {
				throw new UserExceptionInvalidData("Data Update Failed, Try again later.");
			}
		}catch(UserExceptionInvalidData ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean saveUserPref(String customerID, String alertType, String value) throws Exception {
		try {
			if(userAuthDao.updateUserPref(customerID, alertType, value)) {
				return true;
			}else {
				throw new UserExceptionInvalidData("Data Update Failed, Try again later.");
			}
		}catch(UserExceptionInvalidData ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}

	@Override
	public UserAuthBo getProfileDisplayDetails(String userID) throws Exception {
		return userAuthDao.getProfileDisplayDetails(userID);
	}
	
	
	/*
	 * public UserRegForm getValidUser(String uid, String password){ UserRegForm
	 * userRegForm = logindao.getValidUser(uid,password); return userRegForm; }
	 */

}

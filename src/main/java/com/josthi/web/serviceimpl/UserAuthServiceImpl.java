package com.josthi.web.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.bo.UserProfileCompletionStepsBean;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService{

	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
	@Autowired
	public UserAuthDao userAuthDao;
	
	@Autowired
	public EmailService emailService;
	
	@Autowired
	public UserDetailService userDetailService;
	
	@Override
	public UserAuthBo getValidUser(String uid, String password) throws Exception{
		UserAuthBo userAuthBo = userAuthDao.getValidUser(uid,password);		
		return userAuthBo;
	}
	
	@Override
	public UserAuthBo getValidUserWithOtp(String userId, String userEmail, String otp) throws Exception {
		UserAuthBo userAuthBo = userAuthDao.getValidUserWithOtp(userId,userEmail, otp);		
		return userAuthBo;
	}

	@Override
	public int isValidUserID(String emailID) {
		return userAuthDao.getValidUserCount(emailID);
	}

	@Override
	public UserAuthBo getValidUser(String emailID) throws Exception {
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
	public String getEmailId(String customerId) {
		return userAuthDao.getEmailId(customerId);
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

	@Override
	public void sendOtpEmail(String userFirstAndLastName, String otp, String userEmailId, String userId)
			throws Exception {
		//OTP email 
        Map<String, String> otpMap = new HashMap<String, String>();
        otpMap.put("name", userFirstAndLastName);
        otpMap.put("otp",otp);
        EmailDbBean emailDbBeanForOtp = Utils.getEmailBeanForOTP(userEmailId, Utils.mapToString(otpMap));
        boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForOtp);
		
		if(otpQueueStatus) {
			EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
        	String status = "Success : OTP mailed to "+userEmailId+ ". Please check your email and enter the OTP here. You should get the email in 1-2 min.";        	    	 
        	logger.info(status);
		}else{
			throw new UserExceptionInvalidData("Error : Error Occured while triggering the email, please re-login and try");
		}
		
	}

	@Override
	public void updateOtpValidationStatus(String userId) throws Exception {
		userAuthDao.updateUserValidationStatus(userId);		
	}

	@Override
	public UserProfileCompletionStepsBean getProfileStatus(String customerId) throws Exception {
		UserProfileCompletionStepsBean userProfileCompletionStepsBean = new UserProfileCompletionStepsBean();
		
		//The User ID Should all ways be Created.
		userProfileCompletionStepsBean.setIsUserIdCreated("Y");
		
		//Generally the Email Should be validated. still we are checking from the database.
		userProfileCompletionStepsBean.setIsEmailValidated(userAuthDao.isEmailValidated(customerId));
		
		//Checking if there is any beneficiary at all registered.
		userProfileCompletionStepsBean.setIsBeneficiaryRegistered(userAuthDao.isBeneficiaryRegistered(customerId));
		
		
		//Check if Agent is assigned to atleast one Beneficiary.
		List<String> agentIds = userAuthDao.getAssignedAgents(customerId);
		List<String> filteredAgentList = new ArrayList<String>();
		List<UserDetailsBean> agentDetailBeanList = new ArrayList<UserDetailsBean>();
		
		//Here we are filtering the agents.
		if(agentIds !=null && agentIds.size() > 0 ) {			
			for(String agentId :agentIds ) {				
				if(!(StringUtils.isEmpty(agentId))) {
					filteredAgentList.add(agentId);
				}			
			}
			
		}
		
		if(filteredAgentList.size() > 0) {
			userProfileCompletionStepsBean.setIsAgentAssigned("Y");
			for(String agentId :filteredAgentList ) {				
				UserDetailsBean agentDetailsfromDb = userDetailService.getAgentAdminProfileDetails(agentId);
				agentDetailBeanList.add(agentDetailsfromDb);
			}
		}else {
			userProfileCompletionStepsBean.setIsAgentAssigned("N");
		}
		
		userProfileCompletionStepsBean.setAgentDetailsBeanList(agentDetailBeanList);
		logger.info("Agent list Size :" + filteredAgentList.size());
		
		userProfileCompletionStepsBean.setIsPlanPurchased(userAuthDao.isPlanPurchased(customerId));
		
		
		return userProfileCompletionStepsBean;
	}


	
	
	/*
	 * public UserRegForm getValidUser(String uid, String password){ UserRegForm
	 * userRegForm = logindao.getValidUser(uid,password); return userRegForm; }
	 */

}

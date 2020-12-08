package com.josthi.web.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.controller.UserAuthController;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.dao.rowmapper.UserAuthDetailsToDisplayRowMaper;
import com.josthi.web.dao.rowmapper.UserAuthRowMapper;
import com.josthi.web.dao.rowmapper.UserPreferenceRowMaper;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;
import com.josthi.web.exception.UserExceptionInvalidData;



//@Repository("userAuthDao1")
public class UserAuthDaoImpl implements UserAuthDao{

	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static final String SELECT_VALIDATE_LOGIN_SQL = "SELECT CUSTOMER_ID, USERID_EMAIL, WORDAPP, STATUS, ROLE, LOGIN_RETRY_COUNT, TEMPORARY_LOCK_ENABLED, VERIFIED_USER FROM user_auth_table where USERID_EMAIL = ? and WORDAPP = ?;";
	@Override
	public UserAuthBo getValidUser(String uid, String password) throws Exception {
		try{
			@SuppressWarnings("unchecked")

			List<UserAuthBo> userAuthBoList = getJdbcTemplate().query(
												 SELECT_VALIDATE_LOGIN_SQL,
												 new Object[] { uid.trim(),password.trim() },
												 new ValidateAuthenticityRowMaper());
			if ( userAuthBoList.isEmpty()){
				logger.info("User not Found");
				return null;
			}else if ( userAuthBoList.size() == 1 ) { // list contains exactly 1 element
				//logger.info("User Pref :"+serviceRequestBeanList.get(0));
				return userAuthBoList.get(0);
			}else{  // list contains more than 1 elements
				throw new UserExceptionInvalidData("Invalid Data in the Database...");
			}
			}catch(Exception ex){
				logger.error(ex.getMessage(), ex);
				//return new UserAuthBo();
				throw ex;
			}
	}
	
	
	public static final String SELECT_COUNT_LOGIN_ON_UID_SQL = "SELECT COUNT(*) FROM user_auth_table where USERID_EMAIL = ?;";
	@Override
	public int getValidUserCount(String emailID) {
		int count = getJdbcTemplate().queryForObject(SELECT_COUNT_LOGIN_ON_UID_SQL, new Object[] { emailID }, Integer.class);
		return count;
	}
	
	
	/**
	 * Here we are checking if the User gave a proper UserID or not. The Password might be incorrect.
	 */
	public static final String SELECT_VALIDATE_LOGIN_ON_UID_SQL = "SELECT CUSTOMER_ID, USERID_EMAIL, WORDAPP, STATUS, ROLE, LOGIN_RETRY_COUNT, TEMPORARY_LOCK_ENABLED, VERIFIED_USER FROM user_auth_table where USERID_EMAIL = ?;";
	@Override
	public UserAuthBo getValidUser(String emailID) {
		try{
			@SuppressWarnings("unchecked")
			UserAuthBo userAuthBo = (UserAuthBo) getJdbcTemplate().queryForObject(
												 SELECT_VALIDATE_LOGIN_ON_UID_SQL,
												 new Object[] { emailID},
												 new ValidateAuthenticityRowMaper());
				return userAuthBo;
			}catch(Exception ex){
				logger.error(ex.getMessage(), ex);
				return new UserAuthBo();
			}
	}


	

	public static final String UPDATE_LOGIN_DATA_ON_INVALID_PASSWORD = "UPDATE user_auth_table set LOGIN_RETRY_COUNT = ?, TEMPORARY_LOCK_ENABLED = ? where USERID_EMAIL = ?  ";
	@Override
	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid) {
		try {
			int result = jdbcTemplate.update(UPDATE_LOGIN_DATA_ON_INVALID_PASSWORD, new Object[]{userDetailsOnUid.getLoginRetryCount(),
																								userDetailsOnUid.getTemporaryLockEnabled(), 
																								userDetailsOnUid.getUseridEmail()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
		
	}
	
	
	public static final String UPDATE_LOGIN_DATA_ON_SUCCESSFUL_LOGIN = "UPDATE user_auth_table set LOGIN_TIME = ?, LOGIN_STATUS = ?, LOGIN_RETRY_COUNT = ?, TEMPORARY_LOCK_ENABLED = ? where USERID_EMAIL = ?"; 
	@Override
	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails) {
		try {
			
			//logger.info(userDetails.getLoginTime()+"--"+userDetails.getLoginStatus()+"--"+userDetails.getLoginRetryCount()+"--"+userDetails.getTemporaryLockEnabled()+"--"+userDetails.getUseridEmail());
			int result = jdbcTemplate.update(UPDATE_LOGIN_DATA_ON_SUCCESSFUL_LOGIN, new Object[]{userDetails.getLoginTime(),
																								 userDetails.getLoginStatus(),
																								 userDetails.getLoginRetryCount(),
																							     userDetails.getTemporaryLockEnabled(), 
					                                                                             userDetails.getUseridEmail()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}
	
	
	public static final String SELECT_NEXT_ID =  "SELECT NextUserID from userid_generation_table";
	@Override
	public int getNextID() {
		// TODO Auto-generated method stub
		 int result = jdbcTemplate.queryForObject(SELECT_NEXT_ID, Integer.class);
		 return result;
	}
	
	public static final String SELECT_FIRST_LAST_NAME =  "SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) FROM user_detail where UID=?";
	@Override
	public String getUserFirstAndLastName(String customerId) {
		
		try {
			String name = jdbcTemplate.queryForObject(SELECT_FIRST_LAST_NAME, new Object[]{customerId}, String.class);
			return name;
		}catch(Exception ex) {
			logger.error("Couldn't find a name for :"+customerId);
			return customerId;
		}
		
		
	}
	
	public static final String UPDATE_USER_ID_GEN = "UPDATE userid_generation_table SET NEXTUSERID = 1";
	@Override
	public boolean resetUserIdGenTable() {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_ID_GEN);	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String UPDATE_USER_TEMP_LOCK_ACCOUNT = "UPDATE user_auth_table set LOGIN_RETRY_COUNT = 0 , TEMPORARY_LOCK_ENABLED = 'NO' where TEMPORARY_LOCK_ENABLED = 'YES'";
	@Override
	public boolean removeTempLockFromUserAccount() {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_TEMP_LOCK_ACCOUNT);	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String UPDATE_USER_PASSWORD = "UPDATE user_auth_table set WORDAPP=?  where USERID_EMAIL = ? and WORDAPP = ? and CUSTOMER_ID = ?;";

	@Override
	public boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_PASSWORD, new Object[]{passwordResetBean.getNewConfirmPassword(),
																				passwordResetBean.getEmailId(),
																				passwordResetBean.getOldPassword(),
																				passwordResetBean.getUserID()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Update failed with the New Password, try again later, or Contact Customer Service.");
		}
	}
	
	
	
	//========================================================================================
	//=========================== O T P    V A L I D A T I O N  ==============================
	//========================================================================================
	
	public static final String SELECT_OTP_FROM_USER_AUTH = "SELECT OTP FROM user_auth_table WHERE  CUSTOMER_ID = ? and OTP = ?";
	@Override
	public boolean isValidOtp(String userID, String otp) throws Exception {
		List<String> otpList  = getJdbcTemplate().query(SELECT_OTP_FROM_USER_AUTH,
							new Object[] { userID , otp.trim()},
							new RowMapper<String>() {
									@Override
									public String mapRow(ResultSet rs, int rowNum) throws SQLException {
										return rs.getString(1);
									}
								});
			
			if ( otpList.isEmpty()){
				logger.info("No OTP Set");
				return false;
			}else if ( otpList.size() == 1 ) { // list contains exactly 1 element
				logger.info("Valid OTP :"+otpList.get(0));
				return true;
			}else{  // list contains more than 1 elements
				throw new UserExceptionInvalidData("Invalid Data in the Database...");
			}
	}
	
	
	public static final String UPDATE_USER_EMAIL_VALIDATION_STATUS = "UPDATE user_auth_table set VALID_EMAIL = 'YES' where CUSTOMER_ID = ?";
	@Override
	public void updateUserValidationStatus(String userID) throws Exception {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_EMAIL_VALIDATION_STATUS, new Object[]{userID});	
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Failed to Update the OTP, please try again.");
		}
		
	}
	
	
	public static final String UPDATE_USER_OTP = "UPDATE user_auth_table set OTP = ? , OTP_GEN_DATE_TIME = CURRENT_TIMESTAMP WHERE CUSTOMER_ID = ? and USERID_EMAIL = ?";
	@Override
	public boolean updateOtp(String customerID, String emailId, String otp) throws Exception {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_OTP, new Object[]{otp , customerID, emailId});	
			return (result > 0 ? true : false);
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Failed to Update the OTP, please try again.");
		}
	}
	
	
	
	public static final String SELECT_LAST_OTP_TIMESTAMP = "SELECT OTP_GEN_DATE_TIME from user_auth_table where CUSTOMER_ID = ?";
	@Override
	public Timestamp getOTPLastUpdateTimeStamp(String userID) throws Exception {
		List<Timestamp> otpTimeList  = getJdbcTemplate().query(SELECT_LAST_OTP_TIMESTAMP,
				new Object[] { userID},
				new RowMapper<Timestamp>() {
						@Override
						public Timestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getTimestamp(1);
						}
					});

			if ( otpTimeList.isEmpty()){
				logger.info("No OTP Time Set");
				return null;
			}else if ( otpTimeList.size() == 1 ) { // list contains exactly 1 element
				logger.info("Valid OTP :"+otpTimeList.get(0));
				return otpTimeList.get(0);
			}else{  // list contains more than 1 elements
				throw new UserExceptionInvalidData("Invalid Data in the Database...");
			}
	}
	
	
	
	public static final String SELECT_USER_PREF ="SELECT TID, USER_ID, TIME_ZONE, PREFERED_LANGUAGE, PLAN_RENEWAL_REMINDER, WHATSAPP_NOTIFICATION, EMAIL_NOTIFICATION_FOR_AD FROM user_preferences WHERE USER_ID=?";
	@Override
	public UserPreferencesBean getUserPref(String userId) throws Exception {
		@SuppressWarnings("unchecked")
		List<UserPreferencesBean> userPreferencesList  = getJdbcTemplate().query(SELECT_USER_PREF,
																new Object[] { userId},
																new UserPreferenceRowMaper());

			if ( userPreferencesList.isEmpty()){
				logger.info("No Pref Setup");
				return null;
			}else if ( userPreferencesList.size() == 1 ) { // list contains exactly 1 element
				logger.info("User Pref :"+userPreferencesList.get(0));
				return userPreferencesList.get(0);
			}else{  // list contains more than 1 elements
				throw new UserExceptionInvalidData("Invalid Data in the Database...");
			}
	}
	
	
	
	public static final String UPDATE_USER_PREFERENCES = "UPDATE user_preferences " 
			+ "SET TIME_ZONE=? , PREFERED_LANGUAGE=?, LAST_UPDATE_TIMESTAMP=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID = ?;";
	@Override
	public boolean updateUserPreperences(UserPreferencesBean userPreferencesBean, String userId) throws Exception {
		try {
			
			int result = jdbcTemplate.update(UPDATE_USER_PREFERENCES, new Object[]{
																userPreferencesBean.getTimeZone(),
																userPreferencesBean.getLanguage(),
																userId
														});	
			return (result > 0 ? true : false);
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Failed to Update the User Preferences, please try again.");
		}
	}
	
	
	public static final String UPDATE_USER_PREFERENCES_PLAN_RENEWAL = "UPDATE user_preferences SET LAST_UPDATE_TIMESTAMP=CURRENT_TIMESTAMP, "
																	 + "PLAN_RENEWAL_REMINDER = ? WHERE USER_ID = ? ";
	public static final String UPDATE_USER_PREFERENCES_WHATAPP_NOTIFICATION = "UPDATE user_preferences SET LAST_UPDATE_TIMESTAMP=CURRENT_TIMESTAMP, "
			 														 + "WHATSAPP_NOTIFICATION = ? WHERE USER_ID = ? ";
	public static final String UPDATE_USER_PREFERENCES_PROMOTIONAL_OFFER = "UPDATE user_preferences SET LAST_UPDATE_TIMESTAMP=CURRENT_TIMESTAMP, "
			 														 + "EMAIL_NOTIFICATION_FOR_AD = ? WHERE USER_ID = ? ";
	@Override
	public boolean updateUserPref(String customerID, String alertType, String value) throws Exception {
			try {
			
			int result = 0;
			boolean checkBoxValue = Boolean.parseBoolean(value);
			if(alertType.equalsIgnoreCase("planRenewalAlert")) {
				  result = jdbcTemplate.update(UPDATE_USER_PREFERENCES_PLAN_RENEWAL,new Object[]{checkBoxValue, customerID});
				  
			}else if(alertType.equalsIgnoreCase("whatsappNotificationsAlert")) {
				  result = jdbcTemplate.update(UPDATE_USER_PREFERENCES_WHATAPP_NOTIFICATION,new Object[]{checkBoxValue, customerID});
				  
			}else if(alertType.equalsIgnoreCase("promotionalOfferAlert")) {
				  result = jdbcTemplate.update(UPDATE_USER_PREFERENCES_PROMOTIONAL_OFFER,new Object[]{checkBoxValue, customerID});
				  
			}
					
			
			return (result > 0 ? true : false);
			
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new UserExceptionInvalidData("Failed to Update the User Preferences, please try again.");
			}
	}
	
	
	
	public static final String INSERT_DEFAULT_USER_PREF = "insert into user_preferences (USER_ID) values (?)";
	@Override
	public void saveDeafultUserPref(String userId) throws Exception {
		try {
			int result = jdbcTemplate.update(INSERT_DEFAULT_USER_PREF,new Object[]{userId});
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Error Occured while inserting the default settings");
		}
		
	}
	
	
	public static final String SELECT_USER_DETAILS_FOR_DISPLAY = "SELECT USERID_EMAIL, STATUS, ROLE, REGISTRATION_DATE_TIME, VERIFIED_USER FROM user_auth_table WHERE CUSTOMER_ID = ?";
	@Override
	public UserAuthBo getProfileDisplayDetails(String userID) throws Exception {
		try{
			@SuppressWarnings("unchecked")

			List<UserAuthBo> userAuthBoList = getJdbcTemplate().query(
												 SELECT_USER_DETAILS_FOR_DISPLAY,
												 new Object[] {userID},
												 new UserAuthDetailsToDisplayRowMaper());
			if ( userAuthBoList.isEmpty()){
				logger.info("User not Found");
				return null;
			}else if ( userAuthBoList.size() == 1 ) { // list contains exactly 1 element
				return userAuthBoList.get(0);
			}else{  // list contains more than 1 elements
				throw new UserExceptionInvalidData("Invalid Data in the Database...");
			}
			}catch(Exception ex){
				logger.error(ex.getMessage(), ex);
				throw ex;
			}
	}
	

}

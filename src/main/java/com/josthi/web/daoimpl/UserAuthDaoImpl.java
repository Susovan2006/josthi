package com.josthi.web.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.controller.UserAuthController;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.dao.rowmapper.UserAuthRowMapper;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;



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
	public UserAuthBo getValidUser(String uid, String password) {
		try{
			@SuppressWarnings("unchecked")
			UserAuthBo userAuthBo = (UserAuthBo) getJdbcTemplate().queryForObject(
												 SELECT_VALIDATE_LOGIN_SQL,
												 new Object[] { uid.trim(),password.trim() },
												 new ValidateAuthenticityRowMaper());
				return userAuthBo;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				return new UserAuthBo();
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
	public int getNectID() {
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
	

}

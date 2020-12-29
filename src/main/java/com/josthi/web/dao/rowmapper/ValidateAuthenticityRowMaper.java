package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserAuthBo;


//CUSTOMER_ID, USERID_EMAIL, WORDAPP, STATUS, ROLE
public class ValidateAuthenticityRowMaper implements RowMapper{
	@Override
	public UserAuthBo mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserAuthBo userAuth= new UserAuthBo() ;
			userAuth.setCustomerId(resultSet.getString("CUSTOMER_ID"));
			userAuth.setUseridEmail(resultSet.getString("USERID_EMAIL"));
			userAuth.setWordapp(resultSet.getString("WORDAPP"));
			userAuth.setStatus(resultSet.getString("STATUS"));
			userAuth.setRole(resultSet.getString("ROLE"));
			userAuth.setLoginRetryCount(resultSet.getInt("LOGIN_RETRY_COUNT"));
			userAuth.setTemporaryLockEnabled(resultSet.getString("TEMPORARY_LOCK_ENABLED"));
			userAuth.setVerifiedUser(resultSet.getString("VERIFIED_USER"));
			userAuth.setOtp(resultSet.getString("OTP"));
			userAuth.setValidEmail(resultSet.getString("VALID_EMAIL"));
			return userAuth;
		}
}

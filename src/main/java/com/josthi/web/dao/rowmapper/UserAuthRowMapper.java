package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserAuthBo;

public class UserAuthRowMapper implements RowMapper {

@Override
public UserAuthBo mapRow(ResultSet resultSet,int arg1)throws SQLException {
	UserAuthBo userAuth= new UserAuthBo() ;
		userAuth.setCustomerId(resultSet.getString("CUSTOMER_ID"));
		userAuth.setUseridEmail(resultSet.getString("USERID_EMAIL"));
		userAuth.setWordapp(resultSet.getString("WORDAPP"));
		userAuth.setRegistrationDateTime(resultSet.getTimestamp("REGISTRATION_DATE_TIME"));
		userAuth.setStatus(resultSet.getString("STATUS"));
		userAuth.setLoginTime(resultSet.getTimestamp("LOGIN_TIME"));
		userAuth.setLoginStatus(resultSet.getString("LOGIN_STATUS"));
		userAuth.setSecurityQuestion1(resultSet.getString("SECURITY_QUESTION_1"));
		userAuth.setSecurityQuestion2(resultSet.getString("SECURITY_QUESTION_2"));
		userAuth.setSecurityAnswer1(resultSet.getString("SECURITY_ANSWER_1"));
		userAuth.setSecurityAnswer2(resultSet.getString("SECURITY_ANSWER_2"));
		userAuth.setRole(resultSet.getString("ROLE"));
		userAuth.setComments(resultSet.getString("COMMENTS"));
		userAuth.setLoginRetryCount(resultSet.getInt("LOGIN_RETRY_COUNT"));
		userAuth.setTemporaryLockEnabled(resultSet.getString("TEMPORARY_LOCK_ENABLED"));
		return userAuth;
		}
	}

package com.josthi.web.daoimpl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.UserRegistrationDao;

public class UserRegistrationDaoImpl implements UserRegistrationDao{

private static final Logger logger = LoggerFactory.getLogger(UserRegistrationDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public static final String INSERT_USER_AUTH_DETAILS = "INSERT INTO user_auth_table (CUSTOMER_ID, USERID_EMAIL, WORDAPP, REGISTRATION_DATE_TIME, STATUS, LOGIN_STATUS, ROLE, TEMPORARY_LOCK_ENABLED)" + 
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	@Override
	public boolean insertIntoUserAuth(UserRegistrationBean userRegistrationBean) {
		int result = jdbcTemplate.update(INSERT_USER_AUTH_DETAILS,new Object[]{userRegistrationBean.getCustomerID().trim(),
																			   userRegistrationBean.getValidEmailId().trim(),
																			   userRegistrationBean.getConfirmWordApp().trim(),
																			   new Timestamp(System.currentTimeMillis()), 
																			   Constant.USER_STATUS_ACTIVE,
																			   Constant.USER_OFFLINE_STATUS,
																			   Constant.USER_TYPE_REG_USER,
																			   Constant.USER_TEMPORARY_LOCK_NO});
		return (result > 0 ? true : false);
	}
	
	
	
	
	public static final String INSERT_USER_DETAILS = "INSERT INTO user_detail (UID, FIRST_NAME, LAST_NAME, USER_STATUS) VALUES(?, ?, ? ,?)";
	@Override
	public boolean insertIntoUserDetail(UserRegistrationBean userRegistrationBean) {
		System.out.println("userRegistrationBean :"+userRegistrationBean.toString());
		int result = jdbcTemplate.update(INSERT_USER_DETAILS,new Object[]{userRegistrationBean.getCustomerID().trim(),
																			   userRegistrationBean.getFirstName().trim(),
																			   userRegistrationBean.getLastName().trim(), 
																			   Constant.USER_STATUS_ACTIVE});
			return (result > 0 ? true : false);
	}
	
	
	public static final String UPDATE_GET_NEXT_ID = "UPDATE userid_generation_table SET NextUserID = ?;";
	@Override
	public boolean updateNextUid(int nextID) {
		int result = jdbcTemplate.update(UPDATE_GET_NEXT_ID,new Object[]{nextID});				
		return (result > 0 ? true : false);
	}
	
	
	
	
}

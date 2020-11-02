package com.josthi.web.daoimpl ;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.dao.UserDetailsDao ;
import com.josthi.web.dao.rowmapper.UserProfileDetailRowMapperOnCustID;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean ;

//@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public static final String SELECT_USER_DETAILS_ON_CUST_ID = "SELECT UID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, GENDER, "
			+ "USER_ADDRESS_FIRST_LINE, USER_ADDRESS_SECOND_LINE, CITY_TOWN, STATE, COUNTY_DISTRICT, COUNTRY, ZIP_PIN, "
			+ "MOBILE_NO1, MOBILE_NO2, WHATSAPP_NO, LAND_LINE_NO, FAX_NO, OFFICE_PH_NO, SECONDARY_EMAIL, WEBSITE, "
			+ "FACEBOOK_LINK, USER_STATUS FROM user_detail where UID = ?";
	@Override
	public UserDetailsBean getUserDetails(String customerId) {
		try{
			@SuppressWarnings("unchecked")
			UserDetailsBean userDetailsBean = (UserDetailsBean) getJdbcTemplate().queryForObject(
												 SELECT_USER_DETAILS_ON_CUST_ID,
												 new Object[] {customerId},
												 new UserProfileDetailRowMapperOnCustID());
				return userDetailsBean;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				return null;
			}
	}
	
	
	
	public static final String UPDATE_USER_PROFILE_DETAILS = "Update user_detail set FIRST_NAME = ? , LAST_NAME = ? , GENDER = ? , "
			+ "USER_ADDRESS_FIRST_LINE = ?, USER_ADDRESS_SECOND_LINE = ? , CITY_TOWN = ?," 
			+ "STATE = ?, COUNTY_DISTRICT = ? , COUNTRY = ?, ZIP_PIN = ? , MOBILE_NO1 = ? , "
			+ "MOBILE_NO2 = ? , WHATSAPP_NO = ? , LAND_LINE_NO = ? , FAX_NO = ? ," 
			+ "OFFICE_PH_NO = ?, SECONDARY_EMAIL = ? , WEBSITE = ? , FACEBOOK_LINK = ? where  UID = ?";
	@Override
	public boolean updateUserDetails(UserDetailsBean userDetailsBean, String custId) {
		try {
			int result = jdbcTemplate.update(UPDATE_USER_PROFILE_DETAILS, new Object[]{userDetailsBean.getFirstName(),
																		               userDetailsBean.getLastName(), 
																		               userDetailsBean.getGender(),
																		               userDetailsBean.getUserAddressFirstLine(),
																		               userDetailsBean.getUserAddressSecondLine(),
																		               userDetailsBean.getCityTown(),
																		               userDetailsBean.getState(),
																		               userDetailsBean.getCountyDistrict(),
																		               userDetailsBean.getCountry(),
																		               userDetailsBean.getZipPin(),
																		               userDetailsBean.getMobileNo1(),
																		               userDetailsBean.getMobileNo2(),
																		               userDetailsBean.getWhatsappNo(),
																		               userDetailsBean.getLandLineNo(),
																		               userDetailsBean.getFaxNo(),
																		               userDetailsBean.getOfficePhNo(),
																		               userDetailsBean.getSecondaryEmail(),
																		               userDetailsBean.getWebsite(),
																		               userDetailsBean.getFacebookLink(),
																		               custId});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}
	
}

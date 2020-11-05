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
import com.josthi.web.dao.rowmapper.EmergencyContactDetailsRowMapper;
import com.josthi.web.dao.rowmapper.UserProfileDetailRowMapperOnCustID;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;
import com.josthi.web.bo.EmergencyContactBean;
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
				throw ex;
				
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
			throw ex;
		}
	}
	
	
	
	
	
	public static final String SELECT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID = "SELECT CONTACT_ID, PRIMARY_UID, BEN_ID, "
			+ " EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_NUMBER, EMERGENCY_CONTACT_STAY_LOCATION, RELATION, "
			+ " CONTACT_NO_VALIDATED, INSERT_DATE, UPDATE_DATE, NOTES "  
			+ " FROM emergency_contact_details WHERE PRIMARY_UID = ?;";
	@Override
	public List<EmergencyContactBean> getEmergencyContactListForUser(String customerId) {
		try{
			@SuppressWarnings("unchecked")
			List<EmergencyContactBean> emergencyContactBeanList = getJdbcTemplate().query(
												SELECT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID,
												 new Object[] {customerId},
												 new EmergencyContactDetailsRowMapper());
				return emergencyContactBeanList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	
	public static final String INSERT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID = "INSERT INTO emergency_contact_details (PRIMARY_UID, "
			+ " EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_NUMBER, RELATION, NOTES) "
			+ " VALUES (?, ? , ? , ? , ?)";
	@Override
	public boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) {
		
		try {
			int result = jdbcTemplate.update(INSERT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID, new Object[]{
																						custId,
																						emergencyContactBean.getEmergencyContactName(),
																						emergencyContactBean.getEmergencyContactNumber(),
																						emergencyContactBean.getRelation(),
																						emergencyContactBean.getNotes()
																					});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String DELETE_EMERGENCY_CONTACT_DETAILS_ON_CONTACT_ID = "DELETE FROM emergency_contact_details " + 
			"WHERE CONTACT_ID=?";
	@Override
	public void deleteEmergencyContact(int contactId) {
		jdbcTemplate.update(DELETE_EMERGENCY_CONTACT_DETAILS_ON_CONTACT_ID, new Object[]{
				contactId
			});
		
	}
	
	
	
	public static final String SELECT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID_CONTACT_ID = "SELECT CONTACT_ID, PRIMARY_UID, BEN_ID, "
			+ " EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_NUMBER, EMERGENCY_CONTACT_STAY_LOCATION, RELATION, "
			+ " CONTACT_NO_VALIDATED, INSERT_DATE, UPDATE_DATE, NOTES "  
			+ " FROM emergency_contact_details WHERE PRIMARY_UID = ? AND CONTACT_ID = ? ;";
	@Override
	public EmergencyContactBean getEmergencyContactBean(int contactId, String customerId) {
		try{
			@SuppressWarnings("unchecked")
			EmergencyContactBean emergencyContactBean = (EmergencyContactBean) getJdbcTemplate().queryForObject(
												 SELECT_EMERGENCY_CONTACT_DETAILS_ON_CUST_ID_CONTACT_ID,
												 new Object[] {customerId, contactId},
												 new EmergencyContactDetailsRowMapper());
				return emergencyContactBean;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
				
			}
	}
	
	
	public static final String SELECT_COUNT_CONTACTID = "SELECT COUNT(*) FROM emergency_contact_details where CONTACT_ID = ?;";
	@Override
	public int isValidContactId(Integer contactId) {
		int count = getJdbcTemplate().queryForObject(SELECT_COUNT_CONTACTID, new Object[] { contactId }, Integer.class);
		return count;
	}
	
	
	
	public static final String UPDATE_EMERGENCY_CONTACT_DETAILS = "UPDATE emergency_contact_details SET "
			+ "EMERGENCY_CONTACT_NAME=? , EMERGENCY_CONTACT_NUMBER=? , RELATION = ? , NOTES = ? "
			+ " WHERE PRIMARY_UID = ? and CONTACT_ID = ?";
	@Override
	public boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) {
		try {
			
			int result = jdbcTemplate.update(UPDATE_EMERGENCY_CONTACT_DETAILS, new Object[]{ 
																					emergencyContactBean.getEmergencyContactName(),
																					emergencyContactBean.getEmergencyContactNumber(),
																					emergencyContactBean.getRelation(),
																					emergencyContactBean.getNotes(),
																					custId,
																					emergencyContactBean.getContactId()});
			
			return (result > 0 ? true : false);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
}

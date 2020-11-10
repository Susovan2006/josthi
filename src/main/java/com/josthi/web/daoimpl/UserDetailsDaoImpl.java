package com.josthi.web.daoimpl ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import com.josthi.web.dao.rowmapper.BeneficiaryDetailRowMapper;
import com.josthi.web.dao.rowmapper.EmergencyContactDetailsRowMapper;
import com.josthi.web.dao.rowmapper.UserProfileDetailRowMapperOnCustID;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;

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
	
	
	
	//*****************************************************************************************************************
	//*************************** B E N E F I C I A R Y    D E T A I L S  *********************************************
	//*****************************************************************************************************************
	
	
	public static final String SELECT_BENEFICIARY_ID_FROM_RELATION = "SELECT BENEFICIARY_ID FROM relation WHERE CUSTOMER_ID = ? AND BENEFICIARY_TYPE = ?";
	@Override
	public String getPrimaryBeneficiaryIdFromRelation(String customerId) throws Exception {
		
		List<String> primaryBeneficiaryIdLst  = getJdbcTemplate().query(SELECT_BENEFICIARY_ID_FROM_RELATION,
																	new Object[] { customerId , Constant.BENEFICIARY_TYPE_PRIMARY },
																	new RowMapper<String>() {
																			@Override
																			public String mapRow(ResultSet rs,
																					int rowNum) throws SQLException {
																				return rs.getString(1);
																			}
																		});

					if ( primaryBeneficiaryIdLst.isEmpty() ){
						return null;
					}else if ( primaryBeneficiaryIdLst.size() == 1 ) { // list contains exactly 1 element
						return primaryBeneficiaryIdLst.get(0);
					}else{  // list contains more than 1 elements
						//your wish, you can either throw the exception or return 1st element.   
						throw new Exception("Invalid Data in the Database...");
					}
		
		}
	
	@Override
	public String getSecondaryBeneficiaryIdFromRelation(String customerId) throws Exception {
			
		List<String> secondaryBeneficiaryIdLst  = getJdbcTemplate().query(SELECT_BENEFICIARY_ID_FROM_RELATION,
																	new Object[] { customerId , Constant.BENEFICIARY_TYPE_SECONDARY },
																	new RowMapper<String>() {
																			@Override
																			public String mapRow(ResultSet rs,
																					int rowNum) throws SQLException {
																				return rs.getString(1);
																			}
																		});

			if ( secondaryBeneficiaryIdLst.isEmpty() ){
			  return null;
			}else if ( secondaryBeneficiaryIdLst.size() == 1 ) { // list contains exactly 1 element
			  return secondaryBeneficiaryIdLst.get(0);
			}else{  // list contains more than 1 elements
			  //your wish, you can either throw the exception or return 1st element.   
				throw new Exception("Invalid Data in the Database...");
			}
	}
	
	
	
	@Override
	public UserDetailsBean getPrimaryBeneficiaryDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserDetailsBean getSecondaryBeneficiaryDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static final String SELECT_BENEFICIARY_DETAILS_ON_BEN_ID = "SELECT UID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, GENDER, "
			+ "USER_ADDRESS_FIRST_LINE, USER_ADDRESS_SECOND_LINE, CITY_TOWN, STATE, COUNTY_DISTRICT, COUNTRY, ZIP_PIN, "
			+ "MOBILE_NO1, MOBILE_NO2, WHATSAPP_NO, LAND_LINE_NO, FAX_NO, OFFICE_PH_NO, SECONDARY_EMAIL, WEBSITE, "
			+ "FACEBOOK_LINK, USER_STATUS FROM user_detail where UID = ?";
	@Override
	public UserDetailsBean getBeneficiaryDetails(String beneficiaryId) {
		try{
			@SuppressWarnings("unchecked")
			UserDetailsBean userDetailsBean = (UserDetailsBean) getJdbcTemplate().queryForObject(
												 SELECT_USER_DETAILS_ON_CUST_ID,
												 new Object[] {beneficiaryId},
												 new UserProfileDetailRowMapperOnCustID());
				return userDetailsBean;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
				
			}
	}
	
	
	public static final String SELECT_BENEFICIARY_DETAILS_LIST = "SELECT A.UID, A.FIRST_NAME, A.MIDDLE_NAME, A.LAST_NAME, A.GENDER, A.USER_ADDRESS_FIRST_LINE, "
			+ "A.USER_ADDRESS_SECOND_LINE, A.ADDITIONAL_ADDRESS_LINE, A.NEAREST_LAND_MARK, A.CITY_TOWN, "
			+ "A.STATE, A.COUNTY_DISTRICT, A.COUNTRY, A.ZIP_PIN, A.MOBILE_NO1, A.WHATSAPP_NO, A.SECONDARY_EMAIL, A.USER_STATUS, "
			+ "B.TID, B.CUSTOMER_ID, B.BENEFICIARY_ID, B.RELATION_WITH_CUSTOMER, B.DATE_OF_BIRTH, B.AGE, B.HEIGHT, B.WEIGHT, B.BLOOD_GROUP, "
			+ "B.PREFFERED_HOSPITAL, B.MEDICLAME_NAME, B.INSURANCE_NOTE, B.HEALTH_CONDITION, B.MEDICAL_CHALLENGES "
			+ "FROM user_detail A, beneficiary_detail B where A.UID =  B.BENEFICIARY_ID and B.CUSTOMER_ID = ?";

	@Override
	public List<BeneficiaryDetailBean> getBeneficiaryList(String customerId) {
		try{
			@SuppressWarnings("unchecked")
			List<BeneficiaryDetailBean> beneficiaryDetailList = getJdbcTemplate().query(
												 SELECT_BENEFICIARY_DETAILS_LIST,
												 new Object[] {customerId},
												 new BeneficiaryDetailRowMapper());
				return beneficiaryDetailList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	
	
	
	public static final String INSERT_USER_DETAILS_FOR_BEN = "INSERT INTO user_detail" + 
			"(UID, FIRST_NAME, LAST_NAME, GENDER, USER_ADDRESS_FIRST_LINE, USER_ADDRESS_SECOND_LINE, "
			+ " NEAREST_LAND_MARK, CITY_TOWN, STATE, COUNTY_DISTRICT, COUNTRY, ZIP_PIN, MOBILE_NO1, "
			+ " WHATSAPP_NO, SECONDARY_EMAIL, USER_STATUS)" + 
			"VALUES(?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?);";
	@Override
	public boolean insertIntoUserDetail(BeneficiaryDetailBean beneficiaryDetailBean) {
		try {
			int result = jdbcTemplate.update(INSERT_USER_DETAILS_FOR_BEN,
					new Object[]{beneficiaryDetailBean.getBeneficiaryID().trim(),
								 beneficiaryDetailBean.getFirstName().trim(),
								 beneficiaryDetailBean.getLastName().trim(),
								 beneficiaryDetailBean.getGender().trim(),
								 beneficiaryDetailBean.getUserAddressFirstLine().trim(),
								 beneficiaryDetailBean.getUserAddressSecondLine().trim(),
								 beneficiaryDetailBean.getNearestLandMark().trim(),
								 beneficiaryDetailBean.getCityTown().trim(),
								 beneficiaryDetailBean.getState().trim(),
								 beneficiaryDetailBean.getCountyDistrict().trim(),
								 beneficiaryDetailBean.getCountry().trim(),
								 beneficiaryDetailBean.getZipPin().trim(),
								 beneficiaryDetailBean.getMobileNo1().trim(),
								 beneficiaryDetailBean.getWhatsappNo().trim(),
								 beneficiaryDetailBean.getSecondaryEmail().trim(), 
								 Constant.USER_STATUS_ACTIVE});    
				return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String INSERT_BENEFICIARY_DETAILS = "INSERT INTO beneficiary_detail " 
			+ "(CUSTOMER_ID, BENEFICIARY_ID, RELATION_WITH_CUSTOMER, DATE_OF_BIRTH, AGE, HEIGHT, "
			+ " WEIGHT, BLOOD_GROUP, PREFFERED_HOSPITAL, MEDICLAME_NAME, INSURANCE_NOTE, HEALTH_CONDITION, MEDICAL_CHALLENGES)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	@Override
	public boolean insertIntoBeneficiaryDetail(BeneficiaryDetailBean beneficiaryDetailBean) {
		try {
			int result = jdbcTemplate.update(INSERT_BENEFICIARY_DETAILS,
					new Object[]{beneficiaryDetailBean.getCustomerID().trim(),
								 beneficiaryDetailBean.getBeneficiaryID().trim(),
								 beneficiaryDetailBean.getRelationWithCustomer().trim(),
								 beneficiaryDetailBean.getDateOfBirthInTimeStamp(), 
								 beneficiaryDetailBean.getAge(),
								 beneficiaryDetailBean.getHeight().trim(),
								 beneficiaryDetailBean.getWeight().trim(),
								 beneficiaryDetailBean.getBloodGroup().trim(),
								 beneficiaryDetailBean.getPrefferedHospital().trim(),
								 beneficiaryDetailBean.getMediclameInsuranceName().trim(),
								 beneficiaryDetailBean.getInsuranceRelatedNotes().trim(),
								 beneficiaryDetailBean.getHealthCondition().trim(),
								 beneficiaryDetailBean.getMedicalChallenges().trim()});    
				return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String INSERT_RELATION_DETAILS = " INSERT INTO relation (CUSTOMER_ID, BENEFICIARY_ID) "
															+ "VALUES(?, ?);";
	@Override
	public boolean insertIntoRelationDetail(BeneficiaryDetailBean beneficiaryDetailBean) {
		try {
			int result = jdbcTemplate.update(INSERT_RELATION_DETAILS,
					new Object[]{beneficiaryDetailBean.getCustomerID().trim(),
								 beneficiaryDetailBean.getBeneficiaryID().trim() });    
				
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
}

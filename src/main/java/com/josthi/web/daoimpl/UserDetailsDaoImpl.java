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
import com.josthi.web.utils.Utils;
import com.josthi.web.dao.UserDetailsDao ;
import com.josthi.web.dao.rowmapper.AgentAdminProfileDetailRowMapperOnCustID;
import com.josthi.web.dao.rowmapper.BeneficiaryDetailRowMapper;
import com.josthi.web.dao.rowmapper.DropDownRowmapper;
import com.josthi.web.dao.rowmapper.EmergencyContactDetailsRowMapper;
import com.josthi.web.dao.rowmapper.UserProfileDetailRowMapperOnCustID;
import com.josthi.web.dao.rowmapper.ValidateAuthenticityRowMaper;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.DropDownBean;
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
	public boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception{
		
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
	public boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception{
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
	
	
	/*
	 * public static final String SELECT_BENEFICIARY_ID_FROM_RELATION =
	 * "SELECT BENEFICIARY_ID FROM relation WHERE CUSTOMER_ID = ? AND BENEFICIARY_TYPE = ?"
	 * ;
	 * 
	 * @Override public String getPrimaryBeneficiaryIdFromRelation(String
	 * customerId) throws Exception {
	 * 
	 * List<String> primaryBeneficiaryIdLst =
	 * getJdbcTemplate().query(SELECT_BENEFICIARY_ID_FROM_RELATION, new Object[] {
	 * customerId , Constant.BENEFICIARY_TYPE_PRIMARY }, new RowMapper<String>() {
	 * 
	 * @Override public String mapRow(ResultSet rs, int rowNum) throws SQLException
	 * { return rs.getString(1); } });
	 * 
	 * if ( primaryBeneficiaryIdLst.isEmpty() ){ return null; }else if (
	 * primaryBeneficiaryIdLst.size() == 1 ) { // list contains exactly 1 element
	 * return primaryBeneficiaryIdLst.get(0); }else{ // list contains more than 1
	 * elements //your wish, you can either throw the exception or return 1st
	 * element. throw new Exception("Invalid Data in the Database..."); }
	 * 
	 * }
	 */
	
	/*
	 * @Override public String getSecondaryBeneficiaryIdFromRelation(String
	 * customerId) throws Exception {
	 * 
	 * List<String> secondaryBeneficiaryIdLst =
	 * getJdbcTemplate().query(SELECT_BENEFICIARY_ID_FROM_RELATION, new Object[] {
	 * customerId , Constant.BENEFICIARY_TYPE_SECONDARY }, new RowMapper<String>() {
	 * 
	 * @Override public String mapRow(ResultSet rs, int rowNum) throws SQLException
	 * { return rs.getString(1); } });
	 * 
	 * if ( secondaryBeneficiaryIdLst.isEmpty() ){ return null; }else if (
	 * secondaryBeneficiaryIdLst.size() == 1 ) { // list contains exactly 1 element
	 * return secondaryBeneficiaryIdLst.get(0); }else{ // list contains more than 1
	 * elements //your wish, you can either throw the exception or return 1st
	 * element. throw new Exception("Invalid Data in the Database..."); } }
	 */
	
	
	
	
	
	
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
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//------------------------------------- I N S E R T     B E N E F I C I E R Y    D E T A I L S    -----------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	//NB: User Auth entry is being done from userRegistrationDao
	
	
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
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//------------------------------------- D E L E T E     B E N E F I C I E R Y    D E T A I L S    --------- -------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	public static final String DELETE_USER_AUTH = "DELETE FROM user_auth_table WHERE CUSTOMER_ID = ?";
	@Override
	public boolean deleteBeneficiaryFromUserAuth(String beneficiaryID) throws Exception {
		try {
			
			int result = jdbcTemplate.update(DELETE_USER_AUTH, new Object[]{beneficiaryID});
			
			return (result > 0 ? true : false);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String DELETE_USER_DETAIL = "DELETE FROM user_detail WHERE UID = ?";
	@Override
	public boolean deleteBeneficiaryFromUserDetail(String beneficiaryID) throws Exception {
		try {
			
			int result = jdbcTemplate.update(DELETE_USER_DETAIL, new Object[]{beneficiaryID});
			
			return (result > 0 ? true : false);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	public static final String DELETE_BENEFICIARY_DETAIL = "DELETE FROM beneficiary_detail WHERE BENEFICIARY_ID = ?";
	@Override
	public boolean deleteBeneficiaryFromBeneficiaryDetail(String beneficiaryID) throws Exception {
		try {
			
			int result = jdbcTemplate.update(DELETE_BENEFICIARY_DETAIL, new Object[]{beneficiaryID});
			
			return (result > 0 ? true : false);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String DELETE_RELATION = "DELETE FROM relation WHERE BENEFICIARY_ID = ?";
	@Override
	public boolean deleteBeneficiaryFromRelationDetail(String beneficiaryID) throws Exception {
		try {
			
			int result = jdbcTemplate.update(DELETE_RELATION, new Object[]{beneficiaryID});
			
			return (result > 0 ? true : false);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//--------------------------------- S E L E C T     B E N E F I C I E R Y    D E T A I L S    T O   E D I T -------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	
	public static final String SELECT_BENEFICIARY_DETAIL_TO_EDIT = "SELECT A.UID, A.FIRST_NAME, A.MIDDLE_NAME, A.LAST_NAME, A.GENDER, A.USER_ADDRESS_FIRST_LINE, "
			+ "A.USER_ADDRESS_SECOND_LINE, A.ADDITIONAL_ADDRESS_LINE, A.NEAREST_LAND_MARK, A.CITY_TOWN, "
			+ "A.STATE, A.COUNTY_DISTRICT, A.COUNTRY, A.ZIP_PIN, A.MOBILE_NO1, A.WHATSAPP_NO, A.SECONDARY_EMAIL, A.USER_STATUS, "
			+ "B.TID, B.CUSTOMER_ID, B.BENEFICIARY_ID, B.RELATION_WITH_CUSTOMER, B.DATE_OF_BIRTH, B.AGE, B.HEIGHT, B.WEIGHT, B.BLOOD_GROUP, "
			+ "B.PREFFERED_HOSPITAL, B.MEDICLAME_NAME, B.INSURANCE_NOTE, B.HEALTH_CONDITION, B.MEDICAL_CHALLENGES "
			+ "FROM user_detail A, beneficiary_detail B where A.UID =  B.BENEFICIARY_ID and B.BENEFICIARY_ID = ? and B.CUSTOMER_ID = ?";
	@Override
	public BeneficiaryDetailBean getBeneficiaryDetailToEdit(String beneficiaryID, String customerId) throws Exception {
		try{
			@SuppressWarnings("unchecked")
			List<BeneficiaryDetailBean> beneficiaryDetailList = getJdbcTemplate().query(
												 SELECT_BENEFICIARY_DETAIL_TO_EDIT,
												 new Object[] {beneficiaryID, customerId},
												 new BeneficiaryDetailRowMapper());
				if ( beneficiaryDetailList.isEmpty() ){
					return null;
				}else if ( beneficiaryDetailList.size() == 1 ) { // list contains exactly 1 element
					return beneficiaryDetailList.get(0);
				}else{  // list contains more than 1 elements
					//your wish, you can either throw the exception or return 1st element.   
					throw new Exception("Invalid Data in the Database...");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------- V A L I D A T E     B E N E F I C I E R Y    D E T A I L S -------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	//Validate Beneficiary
	public static final String SELECT_COUNT_USER_AUTH = "SELECT COUNT(*) FROM user_auth_table WHERE CUSTOMER_ID = ?";
	public static final String SELECT_COUNT_USER_DETAIL = "SELECT COUNT(*) FROM user_detail WHERE UID = ?";
	public static final String SELECT_COUNT_BENEFICIARY_DETAIL = "SELECT COUNT(*) FROM beneficiary_detail WHERE BENEFICIARY_ID = ?";
	public static final String SELECT_COUNT_RELATION = "SELECT COUNT(*) FROM relation WHERE BENEFICIARY_ID = ?";
	@Override
	public boolean isValidBeneficiaryID(String beneficiaryID) throws Exception {
		int authCount = getJdbcTemplate().queryForObject(SELECT_COUNT_USER_AUTH, new Object[] { beneficiaryID }, Integer.class);
		int userDetailCount = getJdbcTemplate().queryForObject(SELECT_COUNT_USER_DETAIL, new Object[] { beneficiaryID }, Integer.class);
		int beneficiaryDetailCount = getJdbcTemplate().queryForObject(SELECT_COUNT_BENEFICIARY_DETAIL, new Object[] { beneficiaryID }, Integer.class);
		int relationCount = getJdbcTemplate().queryForObject(SELECT_COUNT_RELATION, new Object[] { beneficiaryID }, Integer.class);
		
		if(authCount > 0 && userDetailCount > 0 && beneficiaryDetailCount > 0 && relationCount > 0) {
			logger.info("Valid Beneficiary with validation Count : -->"+authCount+userDetailCount+beneficiaryDetailCount+relationCount);
			return true;
		}else {
			return false;
		}
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------- U P D A T E   B E N E F I C I E R Y    D E T A I L S -------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------
		
	
	public static final String UPDATE_USER_DETAILS_TABLE = "Update user_detail set FIRST_NAME = ? , LAST_NAME = ? , GENDER = ? , "
			+ "USER_ADDRESS_FIRST_LINE = ?, USER_ADDRESS_SECOND_LINE = ? , NEAREST_LAND_MARK = ? , CITY_TOWN = ?," 
			+ "COUNTY_DISTRICT = ? , ZIP_PIN = ? , MOBILE_NO1 = ? , "
			+ "WHATSAPP_NO = ? , SECONDARY_EMAIL = ?  where  UID = ?";
	@Override
	public boolean updateBeneficiaryFromUserDetail(BeneficiaryDetailBean beneficiaryDetailBean) throws Exception {
		
		try {
			int result = jdbcTemplate.update(UPDATE_USER_DETAILS_TABLE, new Object[]{beneficiaryDetailBean.getFirstName(),
																					   beneficiaryDetailBean.getLastName(), 
																					   beneficiaryDetailBean.getGender(),
																					   beneficiaryDetailBean.getUserAddressFirstLine(),
																					   beneficiaryDetailBean.getUserAddressSecondLine(),
																					   beneficiaryDetailBean.getNearestLandMark(),
																					   beneficiaryDetailBean.getCityTown(),
																					   //"West Bengal",
																					   beneficiaryDetailBean.getCountyDistrict(),
																					   //"India",
																					   beneficiaryDetailBean.getZipPin(),
																					   beneficiaryDetailBean.getMobileNo1(),
																					   beneficiaryDetailBean.getWhatsappNo(),
																					   beneficiaryDetailBean.getSecondaryEmail(),
																					   beneficiaryDetailBean.getBeneficiaryID()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String UPDATE_BENEFICIARY_DETAILS_TABLE ="UPDATE beneficiary_detail SET RELATION_WITH_CUSTOMER=?, DATE_OF_BIRTH=?, "
			+ "AGE=?, HEIGHT=?, WEIGHT=?, BLOOD_GROUP=?, PREFFERED_HOSPITAL=?, MEDICLAME_NAME=?, INSURANCE_NOTE=?, HEALTH_CONDITION=?, "
			+ "MEDICAL_CHALLENGES=? WHERE BENEFICIARY_ID = ?;";
	@Override
	public boolean updateBeneficiaryFromBeneficiaryDetail(BeneficiaryDetailBean beneficiaryDetailBean)
			throws Exception {
		
		try {
			
			java.sql.Timestamp beneficiaryDateOfBirth = Utils.getDob(beneficiaryDetailBean.getDateOfBirth());
			if(beneficiaryDateOfBirth!=null) {
				beneficiaryDetailBean.setDateOfBirthInTimeStamp(beneficiaryDateOfBirth);
				beneficiaryDetailBean.setAge(Utils.calculateAge(beneficiaryDetailBean.getDateOfBirth().trim()));
			}
			
			int result = jdbcTemplate.update(UPDATE_BENEFICIARY_DETAILS_TABLE, new Object[]{beneficiaryDetailBean.getRelationWithCustomer(),
																					   beneficiaryDetailBean.getDateOfBirthInTimeStamp(), 
																					   beneficiaryDetailBean.getAge(),
																					   beneficiaryDetailBean.getHeight(),
																					   beneficiaryDetailBean.getWeight(),
																					   beneficiaryDetailBean.getBloodGroup(),
																					   beneficiaryDetailBean.getPrefferedHospital(),
																					   beneficiaryDetailBean.getMediclameInsuranceName(),
																					   beneficiaryDetailBean.getInsuranceRelatedNotes(),
																					   beneficiaryDetailBean.getHealthCondition(),
																					   beneficiaryDetailBean.getMedicalChallenges(),
																					   beneficiaryDetailBean.getBeneficiaryID()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	
	public static final String SELECT_BLOOD_GROUP_LIST = "SELCT TID, DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE FROM dropdown_metadata WHERE  DROPDOWN_TYPE = ? AND ACTIVE = 'Y'";
	@Override
	public List<DropDownBean> getBloodGroup(String bloodGroup) {		
		try{
			@SuppressWarnings("unchecked")
			List<DropDownBean> bloodGroupList = getJdbcTemplate().query(
												 SELECT_BLOOD_GROUP_LIST,
												 new Object[] {bloodGroup},
												 new DropDownRowmapper());
				return bloodGroupList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	//*****************************************************************************************************************
	//******************** A G E N T    A D M I N    S E C T I O N  ***************************************************
	//*****************************************************************************************************************
	
	
	public static final String UPDATE_AGENT_ADMIN_PROFILE_DETAILS = "Update user_detail set FIRST_NAME = ? , LAST_NAME = ? , "
			+ " GENDER = ? , USER_ADDRESS_FIRST_LINE = ?, USER_ADDRESS_SECOND_LINE = ? , ADDITIONAL_ADDRESS_LINE= ?, "
			+ " POLICE_STATION = ? , POST_OFFICE = ? , NEAREST_LAND_MARK = ?, COVERAGE_AREA = ? ,  CITY_TOWN = ?," 
			+ " STATE = 'West Bengal', COUNTY_DISTRICT = ? , COUNTRY = 'India', ZIP_PIN = ? , MOBILE_NO1 = ? , "
			+ " MOBILE_NO2 = ? , WHATSAPP_NO = ? , LAND_LINE_NO = ? , FAX_NO = ? ,  OFFICE_PH_NO = ?, " 
			+ " SECONDARY_EMAIL = ? , WEBSITE = ? , FACEBOOK_LINK = ? , AGENCY_NAME = ? , AGENCY_DESCRIPTION = ? where  UID = ?";
	@Override
	public boolean updateAgentAdminProfile(UserDetailsBean userDetailsBean) throws Exception {
		try {
			int result = jdbcTemplate.update(UPDATE_AGENT_ADMIN_PROFILE_DETAILS, new Object[]{userDetailsBean.getFirstName(),
																		               userDetailsBean.getLastName(), 
																		               userDetailsBean.getGender(),
																		               userDetailsBean.getUserAddressFirstLine(),
																		               userDetailsBean.getUserAddressSecondLine(),
																		               userDetailsBean.getUserAdditionalAddressLine(),
																		               userDetailsBean.getPoliceStation(),
																		               userDetailsBean.getPostOffice(),
																		               userDetailsBean.getNearestLandMark(),
																		               userDetailsBean.getCoverageArea(),
																		               userDetailsBean.getCityTown(),
																		               //userDetailsBean.getState(),
																		               userDetailsBean.getCountyDistrict(),
																		               //userDetailsBean.getCountry(),
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
																		               userDetailsBean.getAgencyName(),
																		               userDetailsBean.getAgencyDescription(),
																		               userDetailsBean.getUid()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String SELECT_AGENT_ADMIN_DETAILS_ON_CUST_ID = "SELECT UID, FIRST_NAME, LAST_NAME, GENDER,  "
			+ "USER_ADDRESS_FIRST_LINE, USER_ADDRESS_SECOND_LINE, ADDITIONAL_ADDRESS_LINE, "
			+ "POLICE_STATION, POST_OFFICE, NEAREST_LAND_MARK, COVERAGE_AREA, CITY_TOWN, STATE, COUNTY_DISTRICT, COUNTRY, ZIP_PIN, "
			+ "MOBILE_NO1, MOBILE_NO2, WHATSAPP_NO, LAND_LINE_NO, FAX_NO, OFFICE_PH_NO, SECONDARY_EMAIL, WEBSITE, "
			+ "FACEBOOK_LINK, USER_STATUS, AGENCY_NAME, AGENCY_DESCRIPTION FROM user_detail where UID = ?";
	@Override
	public UserDetailsBean getAgentAdminProfileDetails(String customerId) throws Exception{
		try{
			@SuppressWarnings("unchecked")
			UserDetailsBean userDetailsBean = (UserDetailsBean) getJdbcTemplate().queryForObject(
													SELECT_AGENT_ADMIN_DETAILS_ON_CUST_ID,
												 new Object[] {customerId},
												 new AgentAdminProfileDetailRowMapperOnCustID());
				return userDetailsBean;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
				
			}
	}
	
	
}

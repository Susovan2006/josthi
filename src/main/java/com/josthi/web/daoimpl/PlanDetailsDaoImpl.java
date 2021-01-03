package com.josthi.web.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PriceDiscountBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.bo.PurchasedPlanToDisplay;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.bo.UnitFamilyPlanPrice;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.PlanDetailsDao;
import com.josthi.web.dao.rowmapper.BeneficiaryAgentEmailRowMapper;
import com.josthi.web.dao.rowmapper.BeneficiaryDropDownRowmapper;
import com.josthi.web.dao.rowmapper.BeneficiaryDropForPlanDownRowmapper;
import com.josthi.web.dao.rowmapper.DropDownPlanDurationRowmapper;
import com.josthi.web.dao.rowmapper.DropDownRowmapper;
import com.josthi.web.dao.rowmapper.DropDownServiceDetailsRowmapper;
import com.josthi.web.dao.rowmapper.DropDownServiceTypeRowmapper;
import com.josthi.web.dao.rowmapper.PlanAndBenefitDetailRowMapper;
import com.josthi.web.dao.rowmapper.PlanAndPriceDetailToDisplayRowMapper;
import com.josthi.web.dao.rowmapper.PriceBreakupAndOfferRowMapper;
import com.josthi.web.dao.rowmapper.PurchaseHistoryRowmapper;
import com.josthi.web.dao.rowmapper.PurchasedPlanToDisplayRowMapper;
import com.josthi.web.dao.rowmapper.RelationRowmapper;
import com.josthi.web.dao.rowmapper.ServicePriceDetailRowMapper;
import com.josthi.web.exception.UserExceptionInvalidData;

public class PlanDetailsDaoImpl implements PlanDetailsDao{

private static final Logger logger = LoggerFactory.getLogger(PlanDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public static final String SELECT_RELATION_DETAILS_ON_CUSTOMER_ID_AND_BENEFICIARYID = "SELECT RELATION_ID, CUSTOMER_ID, BENEFICIARY_ID, "
			+ "AGENT_ID, INSERT_DATE, UPDATE_DATE, PLAN_NAME, PLAN_EXPIRE_DATE"
			+ " FROM relation WHERE CUSTOMER_ID = ? AND BENEFICIARY_ID = ?";
	
	public static final String SELECT_RELATION_DETAILS_ON_BENEFICIARYID = "SELECT RELATION_ID, CUSTOMER_ID, BENEFICIARY_ID, "
			+ "AGENT_ID, INSERT_DATE, UPDATE_DATE, PLAN_NAME, PLAN_EXPIRE_DATE"
			+ " FROM relation WHERE BENEFICIARY_ID = ?";
	@Override
	public RelationBean getEnrolledPlan(String customerId, String beneficiaryId) throws Exception {
		try{
			
			List<RelationBean> relationBeanList = null;
				if((customerId!=null && customerId.trim().equals("")) || customerId == null) {
	
					
					relationBeanList = getJdbcTemplate().query(
														 SELECT_RELATION_DETAILS_ON_BENEFICIARYID,
														 new Object[] {beneficiaryId},
														 new RelationRowmapper());
				}else {
					
					relationBeanList = getJdbcTemplate().query(
														 SELECT_RELATION_DETAILS_ON_CUSTOMER_ID_AND_BENEFICIARYID,
														 new Object[] {customerId, beneficiaryId},
														 new RelationRowmapper());
				}
			
			
				if ( relationBeanList.isEmpty() ){
					return null;
				}else if ( relationBeanList.size() == 1 ) { // list contains exactly 1 element
					return relationBeanList.get(0);
				}else{  // list contains more than 1 elements
					//your wish, you can either throw the exception or return 1st element.   
					throw new Exception("Invalid Data in the Database...");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
		
	}
	
	
	
	public static final String SELECT_SERVICE_DETAILS_FOR_DEFAULT ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and ON_DEMAND_FLAG ='Y'";
	
	public static final String SELECT_SERVICE_DETAILS_ONDEMAND_FOR_BASIC ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and ON_DEMAND_FLAG ='Y' and SERVICE_TYPE = 'BasicService'";
	
	public static final String SELECT_SERVICE_DETAILS_ONDEMAND_FOR_EMERGENCY ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and ON_DEMAND_FLAG ='Y' and SERVICE_TYPE = 'EmergencyService' ";
	
	public static final String SELECT_SERVICE_DETAILS_ONDEMAND_ON_GENERAL ="SELECT ID, SERVICE_CODE,  SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and ON_DEMAND_FLAG ='Y' and SERVICE_TYPE = 'GeneralService' ";
	
	public static final String SELECT_SERVICE_DETAILS_PLAN_ON_BASIC ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 1, 1) = 'Y'";
	
	public static final String SELECT_SERVICE_DETAILS_PLAN_ON_SILVER ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 2, 1) = 'Y'";
	
	public static final String SELECT_SERVICE_DETAILS_PLAN_ON_GOLD ="SELECT ID, SERVICE_CODE, SERVICE_NAME" + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 3, 1) = 'Y'";
	
	
	@Override
	public List<DropDownBean> getServiceDetailList(String searchCriteria) throws Exception {
		try{
			logger.info("Search Criteria :"+searchCriteria);
			List<DropDownBean> serviceDetailList = null;
				
			   if((searchCriteria!=null && searchCriteria.trim().equals(Constant.SERVICE_DEFAILT))) {
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_FOR_DEFAULT,														 
							new DropDownServiceDetailsRowmapper());
				
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.SERVICE_BASIC_SERVICE))){					
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_ONDEMAND_FOR_BASIC,														 
							 new DropDownServiceDetailsRowmapper());
					
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.SERVICE_EMERGENCY_SERVICE))){				
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_ONDEMAND_FOR_EMERGENCY,														 
							new DropDownServiceDetailsRowmapper());
					
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.SERVICE_GENERAL_SERVICE))){					
					serviceDetailList = getJdbcTemplate().query(
							 SELECT_SERVICE_DETAILS_ONDEMAND_ON_GENERAL,														 
							 new DropDownServiceDetailsRowmapper());
					
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.PLAN_BASIC))){					
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_PLAN_ON_BASIC,														 
							 new DropDownServiceDetailsRowmapper());
					
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.PLAN_SILVER))){					
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_PLAN_ON_SILVER,														 
							 new DropDownServiceDetailsRowmapper());
					
				}else if((searchCriteria!=null && searchCriteria.trim().equals(Constant.PLAN_GOLD))){					
					serviceDetailList = getJdbcTemplate().query(
							SELECT_SERVICE_DETAILS_PLAN_ON_GOLD,														 
							 new DropDownServiceDetailsRowmapper());
				}
			
			
				return serviceDetailList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	
	//public static final String SELECT_DROPDOWN_FOR_SERVICE_ON_PLAN_LIST = "SELECT TID, DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, "
	//		+ "ACTIVE FROM dropdown_metadata WHERE ACTIVE = 'Y' AND  DROPDOWN_TYPE in ";
	
	public static final String SELECT_DROPDOWN_FOR_SERVICE_ON_PLAN_LIST = "SELECT ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY " + 
			" FROM service_type where ACTIVE = 'Y' and CATEGORY in ";
	
	@Override
	public List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception {
		try{
				 String whereClause = "";
				 if(planType.equalsIgnoreCase(Constant.SERVICE_DEFAILT)) {
					whereClause = "('OnDemand') order by CATEGORY  desc";
				}else if(planType.equalsIgnoreCase(Constant.PLAN_BASIC)) {
					whereClause = "('OnDemand','PlanBasic') order by CATEGORY  desc";
				}else if(planType.equalsIgnoreCase(Constant.PLAN_SILVER)) {
					whereClause = "('OnDemand','PlanSilver') order by CATEGORY  desc";
				}else if(planType.equalsIgnoreCase(Constant.PLAN_GOLD)) {
					whereClause = "('OnDemand','PlanGold') order by CATEGORY  desc";
				}else {
					throw new UserExceptionInvalidData("Invalid Plan Name :"+planType+" Please contact the Customer Service");
				}
							
					
				List<DropDownBean> dropDownServiceList = getJdbcTemplate().query(
													 SELECT_DROPDOWN_FOR_SERVICE_ON_PLAN_LIST + whereClause,
													 new DropDownServiceTypeRowmapper());
					return dropDownServiceList;

			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	
	public static final String SELECT_PLAN_PRICE = "SELECT ID, SERVICE_NAME, INCLUDED_IN_PLAN, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD , DISCLAIMER " + 
			"FROM service where ACTIVE = 'Y' and SERVICE_CODE = ?";
	@Override
	public ServiceDetailsBean getServicePrice(String serviceId) throws Exception {
		try{	
					
			List<ServiceDetailsBean> serviceDetailsBeanList = getJdbcTemplate().query(
														 SELECT_PLAN_PRICE,
														 new Object[] {serviceId},
														 new ServicePriceDetailRowMapper());			
				if ( serviceDetailsBeanList.isEmpty() ){
					return null;
				}else if ( serviceDetailsBeanList.size() == 1 ) {
					return serviceDetailsBeanList.get(0);
				}else{  // list contains more than 1 elements  
					throw new Exception("Invalid Data in the Database...");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}

	
	
	public static final String SELECT_SERVICE_PRICE_FOR_BASIC ="SELECT ID, SERVICE_NAME, INCLUDED_IN_PLAN, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER " + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 1, 1) = 'Y' and SERVICE_CODE = ?";
	
	public static final String SELECT_SERVICE_PRICE_FOR_SILVER ="SELECT ID, SERVICE_NAME, INCLUDED_IN_PLAN, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER " + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 2, 1) = 'Y' and SERVICE_CODE = ? ";
	
	public static final String SELECT_SERVICE_PRICE_FOR_GOLD ="SELECT ID, SERVICE_NAME, INCLUDED_IN_PLAN, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER " + 
			" FROM service where ACTIVE = 'Y' and  SUBSTRING(INCLUDED_IN_PLAN, 3, 1) = 'Y' and SERVICE_CODE = ?";
	
	@Override
	public ServiceDetailsBean getServicePriceOnPlan(String serviceId, String userEnrolledPlan) throws Exception {
		
		String SQL = "";
		if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_BASIC)) {
					SQL = SELECT_SERVICE_PRICE_FOR_BASIC;
		}else if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_SILVER)) {
					SQL = SELECT_SERVICE_PRICE_FOR_SILVER;
		}else if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_GOLD)) {
					SQL = SELECT_SERVICE_PRICE_FOR_GOLD;
		}else {
			return null;
		}
		
		try{	
			
			List<ServiceDetailsBean> serviceDetailsBeanList = getJdbcTemplate().query(
														 SQL,
														 new Object[] {serviceId},
														 new ServicePriceDetailRowMapper());			
				if ( serviceDetailsBeanList.isEmpty() ){
					return null;
				}else if ( serviceDetailsBeanList.size() == 1 ) { // list contains exactly 1 element
					return serviceDetailsBeanList.get(0);
				}else{  // list contains more than 1 elements  
					throw new Exception("Invalid Data in the Database...");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	public static final String SELECT_PLAN_AND_SERVICE_TO_DISPLAY = "SELECT SERVICE_CODE, SERVICE_NAME, "
			+ " DESCRIPTION, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, DISCLAIMER "
			+ " FROM service where ACTIVE='Y' order by SORT_ORDER asc";
	@Override
	public List<PlanAndBenefitBean> getServiceAndPlanToDisplay() throws Exception {
		try {
			
			List<PlanAndBenefitBean> planAndBenefitBeanList = getJdbcTemplate().query(
											 SELECT_PLAN_AND_SERVICE_TO_DISPLAY,
											 new PlanAndBenefitDetailRowMapper());
			return planAndBenefitBeanList;
			
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	public static final String SELECT_PLAN_AND_PRICE_TO_DISPLAY = "select A.PLAN_TYPE_ID, A.PLAN_NAME, A.DESCRIPTION, " + 
			"B.PRICE_FOR_1_PERSON as ONE_MONTH_ACTUAL, " + 
			"B.PRICE_FOR_1_PERSON as ONE_MONTH_DISCOUNT, " + 
			" 3*B.PRICE_FOR_1_PERSON as THREE_MONTH_ACTUAL, " + 
			"(B.PRICE_FOR_1_PERSON * 3) - ((select (DICSOUNT_PERCENTAGE/100) from PLAN_DISCOUNT_ON_DURATION where DISCOUNT_ID = '90DAY')*(B.PRICE_FOR_1_PERSON * 3)) as THREE_MONTH_DISCOUNT, " + 
			" 6*B.PRICE_FOR_1_PERSON as SIX_MONTH_ACTUAL, " + 
			"(B.PRICE_FOR_1_PERSON * 6) - ((select (DICSOUNT_PERCENTAGE/100) from PLAN_DISCOUNT_ON_DURATION where DISCOUNT_ID = '180DAY')*(B.PRICE_FOR_1_PERSON * 6)) as SIX_MONTH_DISCOUNT, " + 
			" 12*B.PRICE_FOR_1_PERSON as TWELVE_MONTH_ACTUAL, " + 
			"(B.PRICE_FOR_1_PERSON * 12) - ((select (DICSOUNT_PERCENTAGE/100) from PLAN_DISCOUNT_ON_DURATION where DISCOUNT_ID = '365DAY')*(B.PRICE_FOR_1_PERSON * 12)) as TWELVE_MONTH_DISCOUNT " + 
			"from plan_type A, PLAN_PRICE_DETAIL B where A.PLAN_TYPE_ID = B.PLAN_TYPE_ID and B.CURRENCY = 'INR';";
	
	
	

	@Override
	public List<PlanSelectionForUserBean> getPlanDetailsToDisplay() throws Exception {
		try {
			
			List<PlanSelectionForUserBean> planSelectionForUserBeanList = getJdbcTemplate().query(
											 SELECT_PLAN_AND_PRICE_TO_DISPLAY,
											 new PlanAndPriceDetailToDisplayRowMapper());
			return planSelectionForUserBeanList;
			
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	public static final String SELECT_PLAN_DURATION_KEY_VALUE_FOR_DROPDOWN = "SELECT DISCOUNT_ID, DURATION_NAME FROM plan_discount_on_duration order by ID asc;";
	@Override
	public List<DropDownBean> getPlanDurationList() throws Exception {
		// TODO Auto-generated method stub
		try{   
			
			List<DropDownBean> planDurationList = getJdbcTemplate().query(
							SELECT_PLAN_DURATION_KEY_VALUE_FOR_DROPDOWN,														 
							new DropDownPlanDurationRowmapper());
			return planDurationList;
		}catch(Exception ex){
			logger.error(ex.getMessage());
			throw ex;
		}

	}
	
	
	//Bad SQL need tuning.
	public static final String SELECT_BENEFICIARY_FOR_PLAN_SELECTION = "select A.BENEFICIARY_ID, B.FIRST_NAME, B.LAST_NAME FROM  " + 
			" beneficiary_detail A, user_detail B , relation C " + 
			" where A.BENEFICIARY_ID =  B.UID and A.CUSTOMER_ID = ? " + 
			" and A.BENEFICIARY_ID = C.BENEFICIARY_ID and " + 
			" (C.PLAN_NAME = '' or C.PLAN_NAME is null or C.PLAN_EXPIRE_DATE < CURDATE())";
	@Override
	public List<DropDownBean> getBeneficiaryListForPlan(String hostCustomerId) throws Exception {
		try{   
			
			List<DropDownBean> planDurationList = getJdbcTemplate().query(
							SELECT_BENEFICIARY_FOR_PLAN_SELECTION,	
							new Object[] {hostCustomerId},
							new BeneficiaryDropForPlanDownRowmapper());
			return planDurationList;
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	@Override
	public double getBasePrice(String selectedPlan) throws Exception {
		try {
			double unitPrice = getJdbcTemplate().queryForObject("SELECT PRICE_FOR_1_PERSON from plan_price_detail where PLAN_TYPE_ID = ? and "
					+ "CURRENCY = 'INR' and ACTIVE = 'Y'", new Object[] {selectedPlan}, double.class);
		   return unitPrice;
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String SELECT_UNIT_PRICE_FOR_FAMILY_PLAN ="SELECT PRICE_FOR_1_PERSON, PRICE_FOR_2_PERSON, PRICE_FOR_3_PERSON from plan_price_detail where PLAN_TYPE_ID = ? and " + 							"CURRENCY = 'INR' and ACTIVE = 'Y'";
	@Override
	public UnitFamilyPlanPrice getPlanPriceForOneMonth(String selectedPlan) throws Exception {
		try{	
			
			List<UnitFamilyPlanPrice> unitFamilyPlanPrice = getJdbcTemplate().query(
														 SELECT_UNIT_PRICE_FOR_FAMILY_PLAN,
														 new Object[] {selectedPlan},
														 new RowMapper<UnitFamilyPlanPrice>() {
											                 @Override
											                 public UnitFamilyPlanPrice mapRow(ResultSet rs, int rowNumber) throws SQLException {
											                	 UnitFamilyPlanPrice unitFamilyPlanPrice = new UnitFamilyPlanPrice();
											                	 unitFamilyPlanPrice.setPriceForOneBeneficiary(rs.getDouble("PRICE_FOR_1_PERSON"));
											                	 unitFamilyPlanPrice.setPriceForTwoBeneficiary(rs.getDouble("PRICE_FOR_2_PERSON"));
											                	 unitFamilyPlanPrice.setPriceForThreeBeneficiary(rs.getDouble("PRICE_FOR_3_PERSON"));
											                     // set other properties
											                     return unitFamilyPlanPrice;
											                 }
											             });		
				if ( unitFamilyPlanPrice.isEmpty() ){
					return null;
				}else if ( unitFamilyPlanPrice.size() == 1 ) { // list contains exactly 1 element
					return unitFamilyPlanPrice.get(0);
				}else{  // list contains more than 1 elements  
					throw new Exception("Invalid Data in the Database...");
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
		
	}
	
	public static final String SELECT_DISCOUNT_PERCENTAGE = "SELECT DICSOUNT_PERCENTAGE "
	+" FROM plan_discount_on_duration where ACTIVE = 'Y' and DISCOUNT_ID = ?;";
	@Override
	public int getDiscountInPercentage(String discountId) throws Exception {
		
		int percentage = getJdbcTemplate().queryForObject(
				SELECT_DISCOUNT_PERCENTAGE, new Object[] { discountId }, Integer.class);
		return percentage;
	}
	
	
	public static final String INSERT_OFFER_PRICE_ON_AJAX_CALL = "INSERT INTO price_breakup_offer " + 
			"(PLAN_START_DATE, PLAN_END_DATE, PLAN_NAME, PLAN_DURATION_CODE, PLAN_BENEFICIARY_COUNT_CODE, " + 
			"BREAKUP_REQUESTED_BY, BREAKUP_REQUESTED_FOR, " + 
			"PLAN_PRICE_PER_PERSON_PER_MONTH, " + 
			"BENEFECICIARY_COUNT_TO_DISPLAY, " + 
			"BASE_PRICE_FOR_TOTAL_BENEFECIARY, " + 
			"DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY, " + 
			"PLAN_DURATION_TO_DISPLAY, " + 
			"DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN, " + 
			"BASE_PRICE_FOR_SELECTED_DURATION, " + 
			"DISCOUNTED_PRICE_FOR_SELECTED_DURATION, " + 
			"FINAL_DISCOUNTED_PRICE, TOTAL_GAIN, FAMILY_DISCOUNT, LONG_TERM_DISCOUNT, NON_DISCOUNTED_PRICE) " + 
			"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	@Override
	public int saveOfferDetails(Timestamp planStartDate, Timestamp planEndDate, String planName,
			String planDurationCode, String beneficiaryCountCode, String customerId, String requestFor,
			String planPricePerPersonPerMonth, String beneficiaryCount, String basePriceForTotalBeneficiary,
			String discountedPriceForTotalBeneficiary, String planDuration, String discountPercentageForFamilyPlan,
			String basePriceForSelectedDuration, String discountedPriceForSelectedDuration, String finalDiscountedPrice,
			String totalGain, double familyDiscount, double longTermDiscount,double nonDiscountedPlanPrice) throws Exception {
		try {
			
			//logger.info("PLAN_START_DATE :"+planStartDate +"formatted :"+ Date.valueOf(planStartDate.toLocalDateTime().toLocalDate()));
			//logger.info("PLAN_END_DATE :"+planEndDate +"formatted :"+ Date.valueOf(planEndDate.toLocalDateTime().toLocalDate()));
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			
			jdbcTemplate.update(connection -> {

				PreparedStatement ps = connection.prepareStatement(INSERT_OFFER_PRICE_ON_AJAX_CALL, new String[] { "OFFER_ID" });
				ps.setDate(1, Date.valueOf(planStartDate.toLocalDateTime().toLocalDate()));
				ps.setDate(2, Date.valueOf(planEndDate.toLocalDateTime().toLocalDate()));
				ps.setString(3, planName);
				ps.setString(4, planDurationCode);
				ps.setString(5, beneficiaryCountCode);
				ps.setString(6, customerId);
				ps.setString(7, requestFor);
				ps.setString(8, planPricePerPersonPerMonth);
				ps.setString(9, beneficiaryCount);
				ps.setString(10, basePriceForTotalBeneficiary);
				ps.setString(11, discountedPriceForTotalBeneficiary);
				ps.setString(12, planDuration);
				ps.setString(13, discountPercentageForFamilyPlan);
				ps.setString(14, basePriceForSelectedDuration);
				ps.setString(15, discountedPriceForSelectedDuration);
				ps.setString(16, finalDiscountedPrice);
				ps.setString(17, totalGain);
				ps.setDouble(18, familyDiscount);
				ps.setDouble(19, longTermDiscount);
				ps.setDouble(20, nonDiscountedPlanPrice);

				return ps;

			}, keyHolder);

			int offerId = (int)keyHolder.getKey().longValue();
			logger.info("offerId :"+ offerId);
			return offerId;
			
			
			
			
			/*int result = jdbcTemplate.update(INSERT_OFFER_PRICE_ON_AJAX_CALL, new Object[]{
																					planStartDate,
																					planEndDate,
																					planName,
																					planDurationCode,
																					beneficiaryCountCode,
																					customerId,
																					requestFor, //7
																					planPricePerPersonPerMonth,
																					beneficiaryCount,
																					basePriceForTotalBeneficiary,
																					discountedPriceForTotalBeneficiary, 
																					planDuration,
																					discountPercentageForFamilyPlan, //13
																					basePriceForSelectedDuration,
																					discountedPriceForSelectedDuration,
																					finalDiscountedPrice,
																					totalGain
																			});
		
				return result;*/
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	
	
	public static final String UPDATE_PRICE_BREAKUP_OFFER_TABLE = "UPDATE price_breakup_offer set IS_PLAN_PURCHASED='Y' Where OFFER_ID = ?";
	@Override
	public boolean updatePriceBreakupOfferTable(String offerId) throws Exception {
		int result = jdbcTemplate.update(UPDATE_PRICE_BREAKUP_OFFER_TABLE,new Object[]{offerId});				
		return (result > 0 ? true : false);
	}
	
	
	public static final String SELECT_PLAN_EXPIRE_DATE = "Select PLAN_END_DATE from price_breakup_offer where OFFER_ID = ?";
	@Override
	public Timestamp getPlanExpireDate(String offerId) throws Exception {
		try {
		java.sql.Timestamp planExpireDate = jdbcTemplate.queryForObject(SELECT_PLAN_EXPIRE_DATE, new Object[]{offerId}, java.sql.Timestamp.class);
		return planExpireDate;
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	public static final String UPDATE_RELATION_TABLE_WITH_PLAN_DETAILS = "UPDATE relation SET UPDATE_DATE=CURRENT_TIMESTAMP, "
											+ " PLAN_NAME = ? , PLAN_PRICE_BREAKUP_ID = ? , PLAN_EXPIRE_DATE = ?,"
											+ " PLAN_INVOICE_ID = ? Where CUSTOMER_ID = ? and BENEFICIARY_ID = ?";
	@Override
	public boolean setPlanDetailsInRelationTable(String beneficiaryId, String custId, String planID, String offerId,
			Timestamp planExpireDate, String customerPlanInvoiceId) throws Exception {

		try {
			int result = jdbcTemplate.update(UPDATE_RELATION_TABLE_WITH_PLAN_DETAILS,new Object[]{planID,
																									offerId,
																									planExpireDate,
																									customerPlanInvoiceId,
																									custId,
																									beneficiaryId
																									});				
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	
	
	public static final String SELECT_PLAN_BREAKUP_DETAIL_FOR_EMAIL = " SELECT OFFER_ID, PLAN_START_DATE, PLAN_END_DATE, PLAN_NAME, "
			+ " PLAN_DURATION_CODE, PLAN_BENEFICIARY_COUNT_CODE, BREAKUP_REQUESTED_BY, BREAKUP_REQUESTED_FOR, "
			+ " PLAN_PRICE_PER_PERSON_PER_MONTH, BENEFECICIARY_COUNT_TO_DISPLAY, BASE_PRICE_FOR_TOTAL_BENEFECIARY, "
			+ " DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY, PLAN_DURATION_TO_DISPLAY, DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN, "
			+ " BASE_PRICE_FOR_SELECTED_DURATION, DISCOUNTED_PRICE_FOR_SELECTED_DURATION, FINAL_DISCOUNTED_PRICE, TOTAL_GAIN, BREAKUP_CREATED_ON, "
			+ " FAMILY_DISCOUNT, LONG_TERM_DISCOUNT, NON_DISCOUNTED_PRICE "
			+ " FROM price_breakup_offer where OFFER_ID = ? and IS_PLAN_PURCHASED = 'Y'";
	@Override
	public PriceBreakupAndOfferBean getDetailsFromPriceBreakupTable(String offerId)
			throws Exception {
		try{	
			logger.info("Offer ID :"+offerId);
			List<PriceBreakupAndOfferBean> priceBreakupAndOfferBeanList = getJdbcTemplate().query(
																 SELECT_PLAN_BREAKUP_DETAIL_FOR_EMAIL,
																 new Object[] {offerId},
																 new PriceBreakupAndOfferRowMapper());
			
			
			if ( priceBreakupAndOfferBeanList.isEmpty() ){
				return null;
			}else if ( priceBreakupAndOfferBeanList.size() == 1 ) { // list contains exactly 1 element
				return priceBreakupAndOfferBeanList.get(0);
			}else{  // list contains more than 1 elements
				//your wish, you can either throw the exception or return 1st element.   
				throw new Exception("Invalid Data in the Database...");
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
			throw ex;
		}
			
	}
	
	public static final String SELECT_ALL_PURCHASE =  "";
	public static final String SELECT_PLAN_PURCHASE =  "SELECT PURCHASE_ID_TKT, PURCHASE_ITEM, PURCHASE_DETAILS, PURCHASE_DATE, "
			+ "PAYMENT_STATUS, PAYMENT_INVOICE_ID, PRICE_IN_USD, PRICE_IN_INR, TX_BY "  
			+ "FROM purchase_history WHERE TX_BY = ? and SUBSTR(PURCHASE_ID_TKT, 1, 1) = 'P' order by PURCHASE_DATE desc";
	public static final String SELECT_TICKET_PURCHASE =  "SELECT PURCHASE_ID_TKT, PURCHASE_ITEM, PURCHASE_DETAILS, PURCHASE_DATE, "
			+ "PAYMENT_STATUS, PAYMENT_INVOICE_ID, PRICE_IN_USD, PRICE_IN_INR, TX_BY "  
			+ "FROM purchase_history WHERE TX_BY = ? and SUBSTR(PURCHASE_ID_TKT, 1, 1) = 'T' order by PURCHASE_DATE desc";
	
	@Override
	public List<PurchaseHistoryBean> PurchaseDoneByCustomer(String customerId, String purchaseType) throws Exception {
		
		String SQL = "";
			if(purchaseType.equalsIgnoreCase(Constant.PURCHASE_TYPE_PLAN)) {
				SQL = SELECT_PLAN_PURCHASE;
			}else if(purchaseType.equalsIgnoreCase(Constant.PURCHASE_TYPE_SERVICE)) {
				SQL = SELECT_TICKET_PURCHASE;
			}
			
			try {
				List<PurchaseHistoryBean> purchaseHistoryBeanList = getJdbcTemplate().query(
															 SQL,
															 new Object[] {customerId},
															 new PurchaseHistoryRowmapper()
															 );
			return purchaseHistoryBeanList;
		}catch(Exception ex){
			logger.error(ex.getMessage());
			throw ex;
		}
	}
	
	
	
	public static final String SELECT_PLAN_COUNT_FROM_RELATION = "SELECT COUNT(*) from relation WHERE PLAN_INVOICE_ID = ?";
	public static final String SELECT_PLAN_COUNT_FROM_PURCHASE_HISTORY = "SELECT COUNT(*) from purchase_history WHERE PURCHASE_ID_TKT = ?";
	@Override
	public boolean isValidPlanId(String planId) throws Exception {
		int relationCount = getJdbcTemplate().queryForObject(SELECT_PLAN_COUNT_FROM_RELATION, new Object[] { planId }, Integer.class);
		int purchaseHistoryCount = getJdbcTemplate().queryForObject(SELECT_PLAN_COUNT_FROM_PURCHASE_HISTORY, new Object[] { planId }, Integer.class);
		if(purchaseHistoryCount ==  1 && relationCount >0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static final String SELECT_PLAN_PRICE_ID_FROM_RELATION ="select distinct PLAN_PRICE_BREAKUP_ID from relation where CUSTOMER_ID = ? and  PLAN_INVOICE_ID = ?";
	@Override
	public int getPriceBreakUpId(String hostCustomerId, String planId) throws Exception {
		try {
			int planPriceId = getJdbcTemplate().queryForObject(SELECT_PLAN_PRICE_ID_FROM_RELATION, new Object[] {hostCustomerId, planId }, Integer.class);
			return planPriceId;
		}catch(Exception ex) {
			logger.error("hostCustomerId :"+ hostCustomerId +"/ planId :"+planId +" not found.", ex);
			return 0;
		}
	}
	
	
	
	public static final String SELECT_CURRENT_PLAN ="Select PLAN_START_DATE,PLAN_END_DATE,PLAN_NAME, PLAN_DURATION_CODE, "
			+ "BREAKUP_REQUESTED_FOR, FINAL_DISCOUNTED_PRICE  from price_breakup_offer where OFFER_ID in "
			+ "(select  distinct PLAN_PRICE_BREAKUP_ID from relation where " 
			+ " CUSTOMER_ID = ? and PLAN_PRICE_BREAKUP_ID is not null)  order by PLAN_END_DATE asc";
	@Override
	public List<PurchasedPlanToDisplay> getPreviousPurchasedPlan(String userId) throws Exception {
		try {
			List<PurchasedPlanToDisplay> purchasedPlanToDisplayList = getJdbcTemplate().query(
														 SELECT_CURRENT_PLAN,
														 new Object[] {userId},
														 new PurchasedPlanToDisplayRowMapper()
														 );
				return purchasedPlanToDisplayList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
}

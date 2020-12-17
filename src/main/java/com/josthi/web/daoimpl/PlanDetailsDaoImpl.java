package com.josthi.web.daoimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.PlanDetailsDao;
import com.josthi.web.dao.rowmapper.BeneficiaryAgentEmailRowMapper;
import com.josthi.web.dao.rowmapper.DropDownRowmapper;
import com.josthi.web.dao.rowmapper.DropDownServiceDetailsRowmapper;
import com.josthi.web.dao.rowmapper.DropDownServiceTypeRowmapper;
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
					/*
					 * if(planType.equalsIgnoreCase(Constant.SERVICE_DEFAILT)) { whereClause =
					 * "('ServiceTypePlanBasic','ServiceTypeOnDemand') order by DROPDOWN_TYPE  desc"
					 * ; }else if(planType.equalsIgnoreCase(Constant.PLAN_BASIC)) { whereClause =
					 * "('ServiceTypePlanBasic','ServiceTypeOnDemand') order by DROPDOWN_TYPE  desc"
					 * ; }else if(planType.equalsIgnoreCase(Constant.PLAN_SILVER)) { whereClause =
					 * "('ServiceTypePlanSilver','ServiceTypeOnDemand') order by DROPDOWN_TYPE  desc"
					 * ; }else if(planType.equalsIgnoreCase(Constant.PLAN_GOLD)) { whereClause =
					 * "('ServiceTypePlanGold','ServiceTypeOnDemand') order by DROPDOWN_TYPE  desc";
					 * }else { throw new UserExceptionInvalidData("Invalid Plan Name :"
					 * +planType+" Please contact the Customer Service"); }
					 */
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
	
	
}

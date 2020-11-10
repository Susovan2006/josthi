package com.josthi.web.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.UserDetailsDao;
import com.josthi.web.dao.UserRegistrationDao;
import com.josthi.web.service.BeneficiaryService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

//@Service("beneficiaryService")
public class BeneficiaryServiceImpl implements BeneficiaryService{

	private PlatformTransactionManager platformTransactionManager;
	
	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}




	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);
	
	@Autowired
	private UserDetailsDao userDetailsDao;
	
	@Autowired
	private UserRegistrationDao userRegistrationDao;
	
	@Override
	public void getBeneficiaryDetails(UserDetailsBean primaryBeneficiaryDetailsBean,
			UserDetailsBean secondaryBeneficiaryDetailsBean, String customerId) throws Exception {
		
		String primaryBeneficiaryId = userDetailsDao.getPrimaryBeneficiaryIdFromRelation(customerId);
		String secondaryBeneficiaryId = userDetailsDao.getSecondaryBeneficiaryIdFromRelation(customerId);
		
		logger.info("primaryBeneficiaryId :"+primaryBeneficiaryId);
		logger.info("secondaryBeneficiaryId :"+secondaryBeneficiaryId);
		
		//Primary Beneficiary Details 
		if(primaryBeneficiaryId!=null && primaryBeneficiaryId.length() > 0) {
			UserDetailsBean primaryBeneficiaryDetailsFromDB = userDetailsDao.getPrimaryBeneficiaryDetails();
		}
		
		//Secondary Beneficiary Details 
		if(secondaryBeneficiaryId!=null && secondaryBeneficiaryId.length() > 0) {
			UserDetailsBean secondaryBeneficiaryDetailsFromDB = userDetailsDao.getSecondaryBeneficiaryDetails();
		}		
		
	}

	@Override
	public UserDetailsBean geBeneficiaryDetails(String customerId, String beneficiaryType) throws Exception {
		UserDetailsBean userDetailsBean = null;
		
		//For Primary beneficiary
		if(beneficiaryType.equals(Constant.BENEFICIARY_TYPE_PRIMARY)) {
			String primaryBeneficiaryId = userDetailsDao.getPrimaryBeneficiaryIdFromRelation(customerId);
			logger.info("primaryBeneficiaryId :"+primaryBeneficiaryId);
			if(primaryBeneficiaryId!=null && primaryBeneficiaryId.length() > 0) {
				userDetailsBean = userDetailsDao.getBeneficiaryDetails(primaryBeneficiaryId);
			}else {
				userDetailsBean = new UserDetailsBean();
			}
		
		//For Primary beneficiary	
		}else if(beneficiaryType.equals(Constant.BENEFICIARY_TYPE_SECONDARY)) {
			String secondaryBeneficiaryId = userDetailsDao.getSecondaryBeneficiaryIdFromRelation(customerId);
			logger.info("secondaryBeneficiaryId :"+secondaryBeneficiaryId);
			if(secondaryBeneficiaryId!=null && secondaryBeneficiaryId.length() > 0) {
				userDetailsBean = userDetailsDao.getBeneficiaryDetails(secondaryBeneficiaryId);
			}else {
				userDetailsBean = new UserDetailsBean();
			}
		}
		userDetailsBean.setState("West Bengal");
		return userDetailsBean;
	}

	@Override
	public List<BeneficiaryDetailBean> getBeneficiaryList(String customerId) {
		
		return userDetailsDao.getBeneficiaryList(customerId);
	}

	
	
	
	/**
	 * Save Beneficiary details.
	 */
	@Override
	public boolean saveBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId, int nextId) {
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block");
			
			beneficiaryDetailBean.setFirstName(Utils.convertToCamelCase(beneficiaryDetailBean.getFirstName().trim()));
			beneficiaryDetailBean.setLastName(Utils.convertToCamelCase(beneficiaryDetailBean.getLastName().trim()));
			beneficiaryDetailBean.setState("West Bengal");
			beneficiaryDetailBean.setCountry("India");
			beneficiaryDetailBean.setCustomerID(custId);
			logger.info("beneficiaryDetailBean :"+beneficiaryDetailBean.toString());
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
				txnStatus = platformTransactionManager.getTransaction(def);
				
				
				//DB Calls for Users
				boolean status = false;
				
				//Insert in to User Auth Table.
				UserRegistrationBean userRegistrationBean = new UserRegistrationBean();
				userRegistrationBean.setUserType(Constant.USER_TYPE_BENEFICIARY);
				userRegistrationBean.setCustomerID(beneficiaryDetailBean.getBeneficiaryID());
				userRegistrationBean.setValidEmailId(beneficiaryDetailBean.getBeneficiaryID()+"@josthi");
				userRegistrationBean.setConfirmWordApp(Security.encrypt("1234"));
				status = userRegistrationDao.insertIntoUserAuth(userRegistrationBean);
				logger.info("User Registration Table Updated Successfully!!"+userRegistrationBean.toString());
						
				//--> Insert in user_detail Table
				status = userDetailsDao.insertIntoUserDetail(beneficiaryDetailBean);
				logger.info("User Detail Table Updated Successfully!!");
				
				//--> Insert in to beneficiary detail Table
				java.sql.Timestamp beneficiaryDateOfBirth = Utils.getDob(beneficiaryDetailBean.getDateOfBirth());				
				if(beneficiaryDateOfBirth!=null) {
					beneficiaryDetailBean.setDateOfBirthInTimeStamp(beneficiaryDateOfBirth);
					beneficiaryDetailBean.setAge(Utils.calculateAge(beneficiaryDetailBean.getDateOfBirth().trim()));
				}
				status = userDetailsDao.insertIntoBeneficiaryDetail(beneficiaryDetailBean);
				logger.info("Beneficiary Detail Table Updated Successfully!!");
						
						
				//--> Insert into Relation Table'
				status = userDetailsDao.insertIntoRelationDetail(beneficiaryDetailBean);
				logger.info("Relation Table Updated Successfully!!");
				
				//--> Increment the next ID +1
				status = userRegistrationDao.updateNextUid(nextId+1);
				
				platformTransactionManager.commit(txnStatus);
				return status;
			}
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			return false;
		}
	}

}

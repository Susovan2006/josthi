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
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.HistoryDao;
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
	
	@Autowired
	private HistoryDao historyDao;
	
	

	@Override
	public List<BeneficiaryDetailBean> getBeneficiaryList(String customerId) {
		
		return userDetailsDao.getBeneficiaryList(customerId);
	}

		
	/**
	 * Save Beneficiary details.
	 * @throws Exception 
	 */
	@Override
	public boolean saveBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId, int nextId) throws Exception {
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
				
				//--> Add the details in the Activity Table.
				String activityNotes = "User added beneficiary "+beneficiaryDetailBean.getFirstName()+" "
										+beneficiaryDetailBean.getLastName()+". Beneficiary ID:"+beneficiaryDetailBean.getBeneficiaryID();
				status = historyDao.logActivityHistory(beneficiaryDetailBean.getBeneficiaryID(), custId, activityNotes);
				
				//--> Increment the next ID +1
				status = userRegistrationDao.updateNextUid(nextId+1);
				
				platformTransactionManager.commit(txnStatus);
				return status;
			}
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			throw ex;
		}
	}

	@Override
	public void deleteBeneficiaryDetail(String beneficiaryID, String sessionCustomerId) throws Exception {
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block for Delete beneficiary details.");
			
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
				txnStatus = platformTransactionManager.getTransaction(def);
				
				
				//DB Calls for Users
				boolean status = false;
				
				//Delete from User Auth Table.
				status = userDetailsDao.deleteBeneficiaryFromUserAuth(beneficiaryID);
				logger.info(beneficiaryID + "Removed from User Auth Table");
						
				//--> Insert in user_detail Table
				status = userDetailsDao.deleteBeneficiaryFromUserDetail(beneficiaryID);
				logger.info(beneficiaryID + "Removed from User Detail Table");
				
				//--> Insert in to beneficiary detail Table
				status = userDetailsDao.deleteBeneficiaryFromBeneficiaryDetail(beneficiaryID);
				logger.info(beneficiaryID + "Removed from Beneficiary Detail Table");
						
						
				//--> Insert into Relation Table'
				status = userDetailsDao.deleteBeneficiaryFromRelationDetail(beneficiaryID);
				logger.info(beneficiaryID + "Removed from Relation Table");
				
				//--> Add the details in the Activity Table.
				String activityNotes = "User deleted a beneficiary with Beneficiary ID:"+beneficiaryID;
				status = historyDao.logActivityHistory(beneficiaryID, sessionCustomerId, activityNotes);
				
				platformTransactionManager.commit(txnStatus);
				//return status;
			}
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			//return false;
			throw ex;
		}
		
	}

	@Override
	public BeneficiaryDetailBean getBeneficiaryDetailToEdit(String beneficiaryID, String customerId) throws Exception {
		return userDetailsDao.getBeneficiaryDetailToEdit(beneficiaryID, customerId);
	}




	@Override
	public boolean isValidBeneficiaryID(String beneficiaryID) throws Exception {
		// TODO Auto-generated method stub
		return userDetailsDao.isValidBeneficiaryID(beneficiaryID);
	}




	@Override
	public boolean updateBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId) throws Exception {
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block for Delete beneficiary details.");
			
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
				txnStatus = platformTransactionManager.getTransaction(def);
				
				
				//DB Calls for Users
				boolean status = false;
				
				//Update from User Auth Table.
				//status = userDetailsDao.updateBeneficiaryFromUserAuth(beneficiaryDetailBean);
				//logger.info(beneficiaryDetailBean.getBeneficiaryID() + " Updated User Auth Table");
						
				//--> Update in user_detail Table
				status = userDetailsDao.updateBeneficiaryFromUserDetail(beneficiaryDetailBean);
				logger.info(beneficiaryDetailBean.getBeneficiaryID()  + "Updated User Detail Table");
				
				//--> Update in to beneficiary detail Table
				status = userDetailsDao.updateBeneficiaryFromBeneficiaryDetail(beneficiaryDetailBean);
				logger.info(beneficiaryDetailBean.getBeneficiaryID()  + "Updated Beneficiary Detail Table");
				
				//--> Add the details in the Activity Table.
				String activityNotes = "User updated beneficiary details for "+beneficiaryDetailBean.getFirstName()+" "
										+beneficiaryDetailBean.getLastName()+". Beneficiary ID:"+beneficiaryDetailBean.getBeneficiaryID();
				status = historyDao.logActivityHistory(beneficiaryDetailBean.getBeneficiaryID(), custId, activityNotes);
						
				
				platformTransactionManager.commit(txnStatus);
				return status;
			}
			
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			//return false;
			throw ex;
		}
	}


	@Override
	public List<DropDownBean> getBloodGroup(String bloodGroup) {
		return userDetailsDao.getBloodGroup(bloodGroup);
	}


	@Override
	public BeneficiaryDetailBean getBeneficiaryDetailToView(String userID) throws Exception {
		return userDetailsDao.getBeneficiaryDetailToView(userID);
	}

}

package com.josthi.web.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.josthi.web.bo.AgentRegistrationBean;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.UserRegistrationDao;
import com.josthi.web.service.UserRegistrationService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

//@Service("userRegistrationService")
public class UserRegistrationServiceImpl implements UserRegistrationService{

	private PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	UserRegistrationDao userRegistrationDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
	

	@Override
	public boolean registerNewUser(UserRegistrationBean userRegistrationBean, int getNextID) {
		//Transaction Details
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block");
			//Here we are encrypting the Password.
			userRegistrationBean.setConfirmWordApp(Security.encrypt(userRegistrationBean.getConfirmWordApp().trim()));
			userRegistrationBean.setFirstName(Utils.convertToCamelCase(userRegistrationBean.getFirstName().trim()));
			userRegistrationBean.setLastName(Utils.convertToCamelCase(userRegistrationBean.getLastName().trim()));
			userRegistrationBean.setUserType(Constant.USER_TYPE_REG_USER);
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
				txnStatus = platformTransactionManager.getTransaction(def);
				//DB Calls for Users
				//--> Insert in the userAuth table.
				boolean status = false;
				status = userRegistrationDao.insertIntoUserAuth(userRegistrationBean);
				//--> Insert in user_detail Table
				status = userRegistrationDao.insertIntoUserDetail(userRegistrationBean);
				//--> Increment the next ID +1
				status = userRegistrationDao.updateNextUid(getNextID+1);
				
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
	
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}


	@Override
	public boolean registerNewAgent(AgentRegistrationBean agentRegistrationBean, int getNextID) {
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block");
			//Here we are encrypting the Password.

			
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
				txnStatus = platformTransactionManager.getTransaction(def);
				//DB Calls
				//--> Insert in the userAuth table.
				boolean status = false;
				status = userRegistrationDao.insertIntoUserAuthForAgent(agentRegistrationBean);
				//--> Insert in user_detail Table
				status = userRegistrationDao.insertIntoUserDetailForAgent(agentRegistrationBean);
				//--> Insert in Agent_Enquery Table
				status = userRegistrationDao.insertIntoAgentEnquery(agentRegistrationBean);
				//--> Increment the next ID +1
				status = userRegistrationDao.updateNextUid(getNextID+1);
				
				
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

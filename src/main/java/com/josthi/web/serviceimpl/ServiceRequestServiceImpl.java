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

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.HistoryDao;
import com.josthi.web.dao.ServiceRequestDao;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.dao.UserRegistrationDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

//@Service("serviceRequestService")
public class ServiceRequestServiceImpl implements ServiceRequestService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);
	
	private PlatformTransactionManager platformTransactionManager;
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
	@Autowired
	UserAuthDao userAuthDao;
	
	@Autowired
	ServiceRequestDao serviceRequestDao;
	
	@Autowired
	UserRegistrationDao userRegistrationDao;
	
	@Autowired
	private HistoryDao historyDao;
	
	@Override
	public String createTicket(ServiceRequestBean serviceRequestBean, String trim) throws Exception {
		
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block");
			
			int getNextID = userAuthDao.getNextID();
			
			
			String ticketId = Utils.getNextTicketID(Constant.REQUEST_TICKET,getNextID);
			
			//Tickt ID assigned.
			serviceRequestBean.setTicketNo(ticketId);
			
			//Request via oline
			serviceRequestBean.setRequestedVia("ONLINE");
			
			//Ticket Status
			serviceRequestBean.setServiceStatus(Constant.TICKET_STATUS_INITIATED);
			
			//Converting the RequestFulfill date to TimeStamp
			String requestToBeServedOn = serviceRequestBean.getToBeCompletedByStr();
				try {
					java.sql.Timestamp dt = Utils.getRequestFulFillDate(requestToBeServedOn);
					if(dt!=null) {
						serviceRequestBean.setToBeCompletedBy(dt);
					}else {
						throw new Exception("Invalid DateFormat");
					}
				}catch(Exception ex) {
					//In in case of Exception
					logger.error("Error getting fulfill date "+ requestToBeServedOn,ex);
					serviceRequestBean.setToBeCompletedBy(Utils.getDateAdditionValue(3));
					serviceRequestBean.setComments("System Inserted Default Date, please check with Customer.");
					
				}
			
			//Getting the beneficiary Name and the Ben ID
			String beneficiaryNameAndId = serviceRequestBean.getRequestedFor();
			if(beneficiaryNameAndId!=null && beneficiaryNameAndId.length() > 0 && beneficiaryNameAndId.contains("~")) {
				String[] arrbeneficiaryNameAndId = beneficiaryNameAndId.split("~", 2);
				serviceRequestBean.setRequestedFor(arrbeneficiaryNameAndId[0].toString());
				serviceRequestBean.setBeneficiaryId(arrbeneficiaryNameAndId[1].toString());
			}else {
				throw new UserExceptionInvalidData("Invalid Request, Please re-login and try.");
			}
			
			//Setting the Service Type. From UI we are getting the ServiceTypeCode. like ODBASIC/ODEMERGENCY/PLANBASIC etc.
			String serviceTypeCode = serviceRequestDao.getServiceTypeOnServiceCode(serviceRequestBean.getServiceType());
			serviceRequestBean.setServiceType(serviceTypeCode);
			
			//Here we are building the Data for inserting into the PurchaseHistory Table.
			String priceInUsd = serviceRequestBean.getPriceInUsd();
			String priceInInr = serviceRequestBean.getPriceInInr();
			String purchaseItem = serviceRequestBean.getServiceCategory();
			String paymentStatus = "";
			String purchaseDetails = "";
			if(priceInUsd.equalsIgnoreCase("0.00") || priceInInr.equalsIgnoreCase("0.00")) {				
				purchaseDetails = "Service Covered by Plan. Service ID :"+serviceRequestBean.getServiceCategory();
				paymentStatus = "Covered by Plan";
			}else {
				purchaseDetails = "On Demand Service. Service ID :"+serviceRequestBean.getServiceCategory();
				paymentStatus = Constant.TICKET_STATUS_PENDING_PAYMENT;
				serviceRequestBean.setServiceStatus(Constant.TICKET_STATUS_PENDING_PAYMENT); //TODO: need to change based on Payment gateway.
			}
			
			String getServiceAgentId = userAuthDao.getAgentBasedOnBeneficiaryId(serviceRequestBean.getBeneficiaryId().trim());
			serviceRequestBean.setAssignedTo(getServiceAgentId);
			
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
					txnStatus = platformTransactionManager.getTransaction(def);
					//DB Calls for Users
					//--> Insert in the Service Request table.
					boolean status = false;
					status = serviceRequestDao.insertIntoServiceRequestTale(serviceRequestBean);
					
					//Insert in the Service History table.
					status = serviceRequestDao.insertUserServiceHistoryTale(serviceRequestBean);
					
					status = serviceRequestDao.insertIntoPurchaseHistory(ticketId,
																		 purchaseItem,
																		 purchaseDetails,
																		 paymentStatus,
																		 "",
																		 priceInUsd,
																		 priceInInr);
					
					//Insert into Activity History.
					String activityNotes = "User "+serviceRequestBean.getRequestedBy() + " Created a ticket "
										 +ticketId+" for "+serviceRequestBean.getRequestedFor()+".";
					status = historyDao.logActivityHistory(serviceRequestBean.getBeneficiaryId(),
														   serviceRequestBean.getRequesterId(), 
														   activityNotes);
					
					//--> Increment the next ID +1
					status = userRegistrationDao.updateNextUid(getNextID+1);
							
					platformTransactionManager.commit(txnStatus);
					return ticketId;

				
			}
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage());
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back due to user exception");
			throw ex;
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			throw ex;
		}
		
	}

	@Override
	public List<ServiceRequestBean> getServiceRequestList(String userId, String role) throws Exception {	
		return serviceRequestDao.getServiceRequestList(userId , role);
	}

	
	
	//*****************************************************************************************************
	//*********************************** FROM REST SERVICE ***********************************************
	//*****************************************************************************************************
	
	
	@Override
	public boolean updateUserNotes(String customerID, String notes, String urgency, String status, String ticketNo)
			throws Exception {
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		try {
			logger.info("Within transaction Block for Notes Update");

			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
					txnStatus = platformTransactionManager.getTransaction(def);
					//DB Calls for Users
					//--> Update in the Service Request table.
					boolean updateStatus = false;
					String requesterName = userAuthDao.getUserFirstAndLastName(customerID);
					updateStatus = serviceRequestDao.updateServiceRequestTable(customerID,
																				requesterName,
																				notes,
																				urgency, 
																				status, 
																				ticketNo);
					
					//Update Service History table.
					updateStatus = serviceRequestDao.insertUserServiceHistoryTable(customerID,
																						requesterName,
																						notes,
																						urgency, 
																						status, 
																						ticketNo);
												
					platformTransactionManager.commit(txnStatus);
					return true;

				
			}
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage());
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back due to user exception");
			throw ex;
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("txn rolled back");
			throw ex;
		}
	}

	@Override
	public ServiceRequestBean getServiceRequestDetailsOnTicketNumber(String customerID, String ticketNo)
			throws Exception {
		return serviceRequestDao.getServiceRequestDetailsOnTicketNumber(customerID, ticketNo);
	}

	@Override
	public boolean isValidTicket(String ticket) {
		try {
			return serviceRequestDao.isValidTicket(ticket);
		}catch(Exception ex) {
			logger.error("no ticket Found with :"+ticket, ex);
			return false;
		}
		
	}

	@Override
	public List<ServiceRequestHistoryBean> getServiceRequestHistoryBeanList(String ticket) throws Exception {
		// TODO Auto-generated method stub
		return serviceRequestDao.getServiceRequestHistoryBeanList(ticket);
	}
	
}

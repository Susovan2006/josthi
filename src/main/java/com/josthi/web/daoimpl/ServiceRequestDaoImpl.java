package com.josthi.web.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;
import com.josthi.web.dao.ServiceRequestDao;
import com.josthi.web.dao.rowmapper.BeneficiaryDetailRowMapper;
import com.josthi.web.dao.rowmapper.ServiceRequestHistoryRowMapper;
import com.josthi.web.dao.rowmapper.ServiceRequestTableRowMapper;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.utils.Utils;

public class ServiceRequestDaoImpl implements ServiceRequestDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceRequestDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public static final String INSERT_SERVICE_REQUEST_TABLE = "INSERT INTO service_request_table " 
			+ " (TICKET_NO, REQUESTED_BY, REQUESTER_ID, REQUESTED_FOR, BENEFICIARY_ID, REQUESTED_VIA, "
			+ " ASSIGNED_TO, REQUESTED_ON, TO_BE_COMPLETED_BY, SERVICE_TYPE, SERVICE_CATEGORY, "
			+ " SERVICE_REQ_DESCRIPTION, SERVICE_URGENCY, SERVICE_STATUS, LAST_UPDATE, COMMENTS) "  
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	@Override
	public boolean insertIntoServiceRequestTale(ServiceRequestBean serviceRequestBean) throws Exception {
		try {
			int result = jdbcTemplate.update(INSERT_SERVICE_REQUEST_TABLE, new Object[]{serviceRequestBean.getTicketNo(),			//1
																						serviceRequestBean.getRequestedBy(),		//2
																						serviceRequestBean.getRequesterId(),		//3
																						serviceRequestBean.getRequestedFor(),		//4
																						serviceRequestBean.getBeneficiaryId(),		//5
																						serviceRequestBean.getRequestedVia(),		//6
																						serviceRequestBean.getAssignedTo(),			//7
																						new Timestamp(System.currentTimeMillis()),	//8
																						serviceRequestBean.getToBeCompletedBy(),	//9
																						serviceRequestBean.getServiceType(),		//10
																						serviceRequestBean.getServiceCategory(),	//11
																						serviceRequestBean.getServiceReqDescription(),	//12
																						serviceRequestBean.getServiceUrgency(),		//13
																						serviceRequestBean.getServiceStatus(),		//14
																						new Timestamp(System.currentTimeMillis()),	//15
																						serviceRequestBean.getComments()			//16
																					  });	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	
	public static final String INSERT_SERVICE_HISTORY_TABLE = "INSERT INTO service_ticket_history " + 
			"(TICKET_NUMBER, STATUS, COMMENTS, UPDATE_TIMESTAMP, UPDATED_BY_NAME, UPDATED_BY_ID) " + 
			"VALUES(?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";
	@Override
	public boolean insertUserServiceHistoryTale(ServiceRequestBean serviceRequestBean) throws Exception {
		try {
			int result = jdbcTemplate.update(INSERT_SERVICE_HISTORY_TABLE, new Object[]{serviceRequestBean.getTicketNo(),
																						serviceRequestBean.getServiceStatus(),
																						serviceRequestBean.getServiceReqDescription(),
																						//Default date
																						serviceRequestBean.getRequestedBy(),
																						serviceRequestBean.getRequesterId(),
																					  });	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	public static final String SELECT_ACTIVE_TICKET_DETAILS_LIST = "SELECT UID, TICKET_NO, REQUESTED_BY, REQUESTER_ID, "
			+ "REQUESTED_FOR, BENEFICIARY_ID, REQUESTED_VIA, ASSIGNED_TO, REQUESTED_ON, TO_BE_COMPLETED_BY, SERVICE_TYPE, "
			+ "SERVICE_CATEGORY, SERVICE_REQ_DESCRIPTION, SERVICE_URGENCY, SERVICE_STATUS, LAST_UPDATE, COMMENTS, LAST_UPDATE_COMMENTS, LAST_UPDATE_USER " 
			+ "FROM service_request_table WHERE REQUESTER_ID = ? and SERVICE_STATUS <> 'CLOSED' ORDER by LAST_UPDATE DESC;" ;
	
	@Override
	public List<ServiceRequestBean> getServiceRequestList(String userId) throws Exception {
		try{
			@SuppressWarnings("unchecked")
			List<ServiceRequestBean> serviceRequestBeanList = getJdbcTemplate().query(
												 SELECT_ACTIVE_TICKET_DETAILS_LIST,
												 new Object[] {userId},
												 new ServiceRequestTableRowMapper());
				return serviceRequestBeanList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	
	//-----------------------------------------------------------------------------------------
	//------------------------------- FROM REST SERVICE ---------------------------------------
	//-----------------------------------------------------------------------------------------
	
	public static final String SELECT_ACTIVE_TICKET_ON_TICKET_NUM = "SELECT UID, TICKET_NO, REQUESTED_BY, REQUESTER_ID, "
			+ "REQUESTED_FOR, BENEFICIARY_ID, REQUESTED_VIA, ASSIGNED_TO, REQUESTED_ON, TO_BE_COMPLETED_BY, SERVICE_TYPE, "
			+ "SERVICE_CATEGORY, SERVICE_REQ_DESCRIPTION, SERVICE_URGENCY, SERVICE_STATUS, LAST_UPDATE, COMMENTS, LAST_UPDATE_COMMENTS, LAST_UPDATE_USER " 
			+ "FROM service_request_table WHERE REQUESTER_ID = ? and TICKET_NO = ? and SERVICE_STATUS <> 'CLOSED' ORDER by LAST_UPDATE DESC;" ;
	
	@Override
	public ServiceRequestBean getServiceRequestDetailsOnTicketNumber(String userId, String ticketNum) throws Exception {
		try{
			@SuppressWarnings("unchecked")
			List<ServiceRequestBean> serviceRequestBeanList = getJdbcTemplate().query(
												 SELECT_ACTIVE_TICKET_ON_TICKET_NUM,
												 new Object[] {userId, ticketNum},
												 new ServiceRequestTableRowMapper());
				//return ;
				
				if ( serviceRequestBeanList.isEmpty()){
					logger.info("No Ticket Found");
					return null;
				}else if ( serviceRequestBeanList.size() == 1 ) { // list contains exactly 1 element
					//logger.info("User Pref :"+serviceRequestBeanList.get(0));
					return serviceRequestBeanList.get(0);
				}else{  // list contains more than 1 elements
					throw new UserExceptionInvalidData("Invalid Data in the Database...");
				}
				
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
	
	
	public static final String UPDATE_SERVICE_RECUEST_TABLE_WITH_USER_NOTES = "UPDATE service_request_table set" + 
			" SERVICE_URGENCY=?, SERVICE_STATUS=?, LAST_UPDATE=CURRENT_TIMESTAMP, LAST_UPDATE_COMMENTS = ?, LAST_UPDATE_USER = ? " + 
			" WHERE TICKET_NO=? AND REQUESTER_ID = ?;";
	@Override
	public boolean updateServiceRequestTable(String customerID, String requesterName, 
											String notes, String urgency,
											String status, String ticketNo) throws Exception {
		try {
			int result = jdbcTemplate.update(UPDATE_SERVICE_RECUEST_TABLE_WITH_USER_NOTES, new Object[]{
																										urgency,
																										status,
																										notes,
																										requesterName,
																										ticketNo,
																										customerID});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
	

	@Override
	public boolean insertUserServiceHistoryTable(String customerID, String requesterName, String notes, String urgency,
																			String status, String ticketNo) throws Exception {
			try {
				int result = jdbcTemplate.update(INSERT_SERVICE_HISTORY_TABLE, new Object[]{ticketNo,
																							status,
																							notes,
																							//Default date
																							requesterName,
																							customerID,
																						  });	
				return (result > 0 ? true : false);
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw ex;
			}
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//-------------------------------- VIEW   TICKET   HISTORY  --------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public static final String SELECT_TICKET_COUNT_FROM_SERVICE_DETAILS = "SELECT COUNT(*) from service_request_table WHERE TICKET_NO = ?";
	public static final String SELECT_TICKET_COUNT_FROM_SERVICE_HISTORY = "SELECT COUNT(*) from service_ticket_history WHERE TICKET_NUMBER = ?";
	@Override
	public boolean isValidTicket(String ticket) {
		int detailCount = getJdbcTemplate().queryForObject(SELECT_TICKET_COUNT_FROM_SERVICE_DETAILS, new Object[] { ticket }, Integer.class);
		int historyCount = getJdbcTemplate().queryForObject(SELECT_TICKET_COUNT_FROM_SERVICE_HISTORY, new Object[] { ticket }, Integer.class);
		if(detailCount ==  1 && historyCount >0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public static final String SELECT_TICKET_HISTORY_LIST = "SELECT HISTORY_ID, TICKET_NUMBER, STATUS, COMMENTS, UPDATE_TIMESTAMP, UPDATED_BY_NAME, UPDATED_BY_ID " + 
			"FROM service_ticket_history where TICKET_NUMBER = ? order by UPDATE_TIMESTAMP ASC";
	@Override
	public List<ServiceRequestHistoryBean> getServiceRequestHistoryBeanList(String ticket) throws Exception {
		try{
			@SuppressWarnings("unchecked")
			List<ServiceRequestHistoryBean> serviceRequestHistoryBeanList = getJdbcTemplate().query(
													SELECT_TICKET_HISTORY_LIST,
												 new Object[] {ticket},
												 new ServiceRequestHistoryRowMapper());
				return serviceRequestHistoryBeanList;
			}catch(Exception ex){
				logger.error(ex.getMessage());
				throw ex;
			}
	}
}

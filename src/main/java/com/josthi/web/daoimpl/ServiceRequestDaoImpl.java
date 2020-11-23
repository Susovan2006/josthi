package com.josthi.web.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.dao.ServiceRequestDao;
import com.josthi.web.dao.rowmapper.BeneficiaryDetailRowMapper;
import com.josthi.web.dao.rowmapper.ServiceRequestTableRowMapper;

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
			+ "SERVICE_CATEGORY, SERVICE_REQ_DESCRIPTION, SERVICE_URGENCY, SERVICE_STATUS, LAST_UPDATE, COMMENTS " 
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
}

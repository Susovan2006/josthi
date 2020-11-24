package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.bo.ServiceRequestBean;

public interface ServiceRequestDao {

	boolean insertIntoServiceRequestTale(ServiceRequestBean serviceRequestBean) throws Exception;

	boolean insertUserServiceHistoryTale(ServiceRequestBean serviceRequestBean) throws Exception;

	List<ServiceRequestBean> getServiceRequestList(String userId)  throws Exception;

	boolean updateServiceRequestTable(String customerID, String requesterName, String notes, String urgency,
			String status, String ticketNo) throws Exception;

	boolean insertUserServiceHistoryTable(String customerID, String requesterName, String notes, String urgency,
			String status, String ticketNo) throws Exception;

	ServiceRequestBean getServiceRequestDetailsOnTicketNumber(String userId, String ticketNum) throws Exception;

}

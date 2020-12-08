package com.josthi.web.service;

import java.util.List;

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;

public interface ServiceRequestService {

	String createTicket(ServiceRequestBean serviceRequestBean, String trim) throws Exception;

	List<ServiceRequestBean> getServiceRequestList(String userId, String role) throws Exception;

	boolean updateUserNotes(String customerID, String notes, String urgency, String status, String ticketNo) throws Exception;

	ServiceRequestBean getServiceRequestDetailsOnTicketNumber(String customerID, String ticketNo) throws Exception;

	boolean isValidTicket(String ticket);

	List<ServiceRequestHistoryBean> getServiceRequestHistoryBeanList(String ticket) throws Exception;

}

package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;

public interface ServiceRequestDao {

	boolean insertIntoServiceRequestTale(ServiceRequestBean serviceRequestBean) throws Exception;

	boolean insertUserServiceHistoryTale(ServiceRequestBean serviceRequestBean) throws Exception;

	List<ServiceRequestBean> getServiceRequestList(String userId, String role)  throws Exception;

	boolean updateServiceRequestTable(String customerID, String requesterName, String notes, String urgency,
			String status, String ticketNo) throws Exception;

	boolean insertUserServiceHistoryTable(String customerID, String requesterName, String notes, String urgency,
			String status, String ticketNo) throws Exception;

	ServiceRequestBean getServiceRequestDetailsOnTicketNumber(String userId, String ticketNum) throws Exception;

	boolean isValidTicket(String ticket);

	List<ServiceRequestHistoryBean> getServiceRequestHistoryBeanList(String ticket) throws Exception;

	String getServiceTypeOnServiceCode(String serviceType) throws Exception;

	boolean insertIntoPurchaseHistory(String ticketId, String purchaseItem, String purchaseDetails,
			String paymentStatus, String paymentInvoiceId, String priceInUsd, String priceInInr, String txBy) throws Exception;

}

package com.josthi.web.service;

import java.util.List;

import com.josthi.web.bo.ServiceRequestBean;

public interface ServiceRequestService {

	String createTicket(ServiceRequestBean serviceRequestBean, String trim) throws Exception;

	List<ServiceRequestBean> getServiceRequestList(String userId) throws Exception;

}

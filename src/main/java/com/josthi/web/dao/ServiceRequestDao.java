package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.bo.ServiceRequestBean;

public interface ServiceRequestDao {

	boolean insertIntoServiceRequestTale(ServiceRequestBean serviceRequestBean) throws Exception;

	boolean insertUserServiceHistoryTale(ServiceRequestBean serviceRequestBean) throws Exception;

	List<ServiceRequestBean> getServiceRequestList(String userId)  throws Exception;

}

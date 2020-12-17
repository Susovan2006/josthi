package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.bo.ServiceDetailsBean;


public interface PlanDetailsDao {

	RelationBean getEnrolledPlan(String customerId, String beneficiaryId) throws Exception;

	List<DropDownBean> getServiceDetailList(String searchCriteria) throws Exception;

	List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception;

	ServiceDetailsBean getServicePrice(String serviceDetails) throws Exception;

	ServiceDetailsBean getServicePriceOnPlan(String serviceId, String userEnrolledPlan) throws Exception;

}

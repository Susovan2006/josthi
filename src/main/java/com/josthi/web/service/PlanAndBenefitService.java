package com.josthi.web.service;

import java.util.List;

import com.josthi.web.bo.AjaxResponsePrice;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.ServiceDetailsBean;

public interface PlanAndBenefitService {

	String getEnrolledPlan(String customerId, String beneficiaryId) throws Exception;

	List<DropDownBean> getServiceDetailList(String string) throws Exception;

	List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception;

	AjaxResponsePrice getServicePrice(String serviceDetails, String userEnrolledPlan) throws Exception;

	boolean isServiceCoveredByPlan(String serviceDetails, String userEnrolledPlan) throws Exception;

}

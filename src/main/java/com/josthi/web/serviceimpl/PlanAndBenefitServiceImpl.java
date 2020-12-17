package com.josthi.web.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.AjaxResponsePrice;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.dao.PlanDetailsDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.constants.Constant;

@Service("planAndBenefitService")
public class PlanAndBenefitServiceImpl implements PlanAndBenefitService{

	@Autowired
	PlanDetailsDao planDetailsDao;
	
	
	@Override
	public String getEnrolledPlan(String customerId, String beneficiaryId) throws Exception {
		try {
			RelationBean relationBean =  planDetailsDao.getEnrolledPlan(customerId,beneficiaryId);
			
			//relationBean null means there is no entry in the relation table for the Customer and Beneficiary.
			if(relationBean == null) {
				throw new UserExceptionInvalidData("No matching Beneficiary found in the System, please check if the beneficiary is registered properly or not.");
			}else {
				if(relationBean.getAgentId().isEmpty()) {
					throw new UserExceptionInvalidData("As of now there is no Agent assigned to Beneficiary "+beneficiaryId+". Please wait for sometime or call the customer Support.");
				}
				if(relationBean.getPlanName().isEmpty()) {
					return Constant.SERVICE_DEFAILT;
				}else {
					return relationBean.getPlanName();
				}
			}
		}catch(Exception ex) {
			throw ex;
		}
	}


	@Override
	public List<DropDownBean> getServiceDetailList(String serviceType) throws Exception {
		
		return planDetailsDao.getServiceDetailList(serviceType);
	}


	@Override
	public List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception {
		return planDetailsDao.getServiceTypeListBasedOnPlan(planType);
	}


	@Override
	public AjaxResponsePrice getServicePrice(String serviceCategoryCode, String userEnrolledPlan) throws Exception {		
		ServiceDetailsBean serviceDetailsBean = planDetailsDao.getServicePrice(serviceCategoryCode);
		AjaxResponsePrice ajaxResponsePrice = new AjaxResponsePrice();
		BigDecimal inr = serviceDetailsBean.getOnDemantPriceInr();
		BigDecimal usd = serviceDetailsBean.getOnDemantPriceUsd();
		ajaxResponsePrice.setStatus(Constant.AJAX_SUCCESS);
		ajaxResponsePrice.setMessage("Successfully received the price for Ondemand service.");
		ajaxResponsePrice.setComments(inr+"&#8377 /"+usd+"$");
		ajaxResponsePrice.setPriceInInr(inr+"");
		ajaxResponsePrice.setPriceInUsd(usd+"");
		ajaxResponsePrice.setDisclaimer(serviceDetailsBean.getDisclaimer());
		return ajaxResponsePrice;
	}


	@Override
	public boolean isServiceCoveredByPlan(String serviceCategoryCode, String userEnrolledPlan) throws Exception {
		ServiceDetailsBean serviceDetailsBean = planDetailsDao.getServicePriceOnPlan(serviceCategoryCode, userEnrolledPlan);
		if(serviceDetailsBean==null) {
			return false;
		}else {
			return true;
		}
		
	}

}

package com.josthi.web.service;

import java.util.List;
import java.util.Map;

import com.josthi.web.bo.AjaxResponsePrice;
import com.josthi.web.bo.AjaxRestResponseForPriceCalculation;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanInvoiceEmailBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PlanSubscriptionForUserBean;
import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.bo.PurchasedPlanToDisplay;
import com.josthi.web.bo.ServiceDetailsBean;


public interface PlanAndBenefitService {

	String getEnrolledPlan(String customerId, String beneficiaryId) throws Exception;

	List<DropDownBean> getServiceDetailList(String string) throws Exception;

	List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception;

	AjaxResponsePrice getServicePrice(String serviceDetails, String userEnrolledPlan) throws Exception;

	boolean isServiceCoveredByPlan(String serviceDetails, String userEnrolledPlan) throws Exception;

	List<PlanAndBenefitBean> getServiceAndPlanToDisplay() throws Exception;

	List<PlanSelectionForUserBean> getPlanDetailsToDisplay() throws Exception;

	List<DropDownBean> getPlanDurationList() throws Exception;

	List<DropDownBean> getBeneficiaryListForPlan(String hostCustomerId) throws Exception;

	AjaxRestResponseForPriceCalculation getPriceBreakup(String customerId, String selectedPlan, String planDuration,
			String beneficiaryCount) throws Exception;
	
	AjaxRestResponseForPriceCalculation getCleanPriceBreakup(String customerId, String selectedPlan, String planDuration,
			String beneficiaryCount) throws Exception;

	int saveOfferDetails(AjaxRestResponseForPriceCalculation ajaxRestResponseForPriceCalculation, String customerId,
			String beneficiary1, String beneficiary2, String beneficiary3, String planDurationCode, String beneficiaryCountCode) throws Exception;

	String savePlanDetails(PlanSubscriptionForUserBean planSubscriptionForUserBean, String custId) throws Exception;

	PlanInvoiceEmailBean getPlanInvoiceDetailsForEmail(String trim,String[] beneficiaryArray, String customerPlanInvoiveId, String offerId, String customerEmail) throws Exception;

	PlanInvoiceEmailBean getCleanPlanInvoiceDetailsForEmail(String trim,String[] beneficiaryArray, String customerPlanInvoiveId, String offerId, String customerEmail) throws Exception;

	Map<String, String> getMapForInvoiceEmail(PlanInvoiceEmailBean planInvoiceEmailBean) throws Exception;

	PlanSubscriptionForUserBean validateIncomingData(PlanSubscriptionForUserBean planSubscriptionForUserBean) throws Exception;

	List<PurchaseHistoryBean> getPurchaseDoneByCustomer(String hostCustomerId, String purchaseType) throws Exception ;

	boolean isValidPlanId(String planId) throws Exception;

	int getPriceBreakUpId(String hostCustomerId, String planId) throws Exception;

	List<PurchasedPlanToDisplay> getPreviousPurchasedPlan(String hostCustomerId) throws Exception;

}

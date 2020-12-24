package com.josthi.web.dao;

import java.sql.Timestamp;
import java.util.List;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PriceDiscountBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.bo.UnitFamilyPlanPrice;



public interface PlanDetailsDao {

	RelationBean getEnrolledPlan(String customerId, String beneficiaryId) throws Exception;

	List<DropDownBean> getServiceDetailList(String searchCriteria) throws Exception;

	List<DropDownBean> getServiceTypeListBasedOnPlan(String planType) throws Exception;

	ServiceDetailsBean getServicePrice(String serviceDetails) throws Exception;

	ServiceDetailsBean getServicePriceOnPlan(String serviceId, String userEnrolledPlan) throws Exception;

	List<PlanAndBenefitBean> getServiceAndPlanToDisplay() throws Exception;

	List<PlanSelectionForUserBean> getPlanDetailsToDisplay() throws Exception;

	List<DropDownBean> getPlanDurationList() throws Exception;

	List<DropDownBean> getBeneficiaryListForPlan(String hostCustomerId) throws Exception;

	double getBasePrice(String selectedPlan) throws Exception;

	UnitFamilyPlanPrice getPlanPriceForOneMonth(String selectedPlan) throws Exception;

	public int getDiscountInPercentage(String discountId) throws Exception;

	int saveOfferDetails(Timestamp planStartDate, Timestamp planEndDate, String planName, String planDurationCode,
			String beneficiaryCountCode, String customerId, String string, String planPricePerPersonPerMonth,
			String beneficiaryCount, String basePriceForTotalBeneficiary, String discountedPriceForTotalBeneficiary,
			String planDuration, String discountPercentageForFamilyPlan, String basePriceForSelectedDuration,
			String discountedPriceForSelectedDuration, String finalDiscountedPrice, String totalGain,
			double familyDiscount, double longTermDiscount, double nonDiscountedPlanPrice) throws Exception;

	boolean updatePriceBreakupOfferTable(String offerId) throws Exception;

	Timestamp getPlanExpireDate(String offerId)  throws Exception;

	boolean setPlanDetailsInRelationTable(String beneficiaryId, String custId, String planID, String offerId,
			Timestamp planExpireDate, String customerPlanInvoiceId) throws Exception;

	PriceBreakupAndOfferBean getDetailsFromPriceBreakupTable(String offerId) throws Exception;

	//List<PurchaseHistoryBean> PurchaseDoneByCustomer(String customerId) throws Exception;

	List<PurchaseHistoryBean> PurchaseDoneByCustomer(String customerId, String purchaseType)throws Exception;

}

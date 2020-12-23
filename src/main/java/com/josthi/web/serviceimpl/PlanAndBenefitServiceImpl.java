package com.josthi.web.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.josthi.web.bo.AjaxResponsePrice;
import com.josthi.web.bo.AjaxRestResponseForPriceCalculation;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanInvoiceEmailBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PlanSubscriptionForUserBean;
import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PriceDiscountBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.dao.HistoryDao;
import com.josthi.web.dao.PlanDetailsDao;
import com.josthi.web.dao.ServiceRequestDao;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.dao.UserDetailsDao;
import com.josthi.web.dao.UserRegistrationDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.utils.Utils;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.bo.UnitFamilyPlanPrice;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.constants.Constant;

//@Service("planAndBenefitService") initiated via SpringConfig.
public class PlanAndBenefitServiceImpl implements PlanAndBenefitService{

	private static final Logger logger = LoggerFactory.getLogger(PlanAndBenefitServiceImpl.class);
	
	private PlatformTransactionManager platformTransactionManager;
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
	
	@Autowired
	PlanDetailsDao planDetailsDao;
	
	@Autowired
	UserAuthDao userAuthDao;
	
	@Autowired
	UserRegistrationDao userRegistrationDao;
	
	@Autowired
	ServiceRequestDao serviceRequestDao;
	
	@Autowired
	private HistoryDao historyDao;
	
	@Autowired
	private UserDetailsDao userDetailsDao;
	
	
	@Override
	public String getEnrolledPlan(String customerId, String beneficiaryId) throws Exception {
		try {
			RelationBean relationBean =  planDetailsDao.getEnrolledPlan(customerId,beneficiaryId);
			
			//relationBean null means there is no entry in the relation table for the Customer and Beneficiary.
			if(relationBean == null) {
				throw new UserExceptionInvalidData("No matching Beneficiary found in the System, please check if the beneficiary is registered properly or not.");
			}else {
				if(relationBean.getAgentId()==null || relationBean.getAgentId().isEmpty()) {
					throw new UserExceptionInvalidData("As of now there is no Agent assigned to Beneficiary "+beneficiaryId+". Please wait for sometime or call the customer Support.");
				}
				if(relationBean.getPlanName() == null || relationBean.getPlanName().isEmpty()) {
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

	/**
	 * This method is to fetch the Plan and Service Details in Tabular format.
	 */
	@Override
	public List<PlanAndBenefitBean> getServiceAndPlanToDisplay() throws Exception {
		return planDetailsDao.getServiceAndPlanToDisplay();
	}


	@Override
	public List<PlanSelectionForUserBean> getPlanDetailsToDisplay() throws Exception {
		return planDetailsDao.getPlanDetailsToDisplay();
	}


	@Override
	public List<DropDownBean> getPlanDurationList() throws Exception {	
		return planDetailsDao.getPlanDurationList();
	}


	@Override
	public List<DropDownBean> getBeneficiaryListForPlan(String hostCustomerId) throws Exception {
		return planDetailsDao.getBeneficiaryListForPlan(hostCustomerId);
	}


	
	//"selectedPlan":"Gold",
	//"planDuration":"365DAY",
	//"beneficiaryCount":"3BEN"}
	@Override
	public AjaxRestResponseForPriceCalculation getPriceBreakup(String customerId, String selectedPlan,
			String planDuration, String beneficiaryCount) throws Exception {
		UnitFamilyPlanPrice unitFamilyPlanPrice = planDetailsDao.getPlanPriceForOneMonth(selectedPlan);
		
		double unitPriceOneBen = unitFamilyPlanPrice.getPriceForOneBeneficiary();
		double unitPriceTwoBen = unitFamilyPlanPrice.getPriceForTwoBeneficiary();
		double unitPriceThreeBen = unitFamilyPlanPrice.getPriceForThreeBeneficiary();
		
		
		//List<PriceDiscountBean> priceDiscountList = planDetailsDao.getDiscountInPercentage();
		
		logger.info("******** Price :"+unitPriceOneBen+"--"+unitPriceTwoBen+"--"+unitPriceThreeBen);
		
		AjaxRestResponseForPriceCalculation ajaxRestResponseForPriceCalculation = new AjaxRestResponseForPriceCalculation();
		ajaxRestResponseForPriceCalculation.setPlanName(selectedPlan + " Plan");
		ajaxRestResponseForPriceCalculation.setPlanPricePerPersonPerMonth(Utils.formattedCurrency(unitPriceOneBen+"")); 
		ajaxRestResponseForPriceCalculation.setPlanStartDate(new Timestamp(System.currentTimeMillis()));
		ajaxRestResponseForPriceCalculation.setPlanStartDateStr(Utils.timestampToFormattedString(new Timestamp(System.currentTimeMillis()), "d MMM yyyy hh:mm aaa"));
		

		
		//planDuration
		double basePriceOnDuration = 0.0f;
		double finalDiscountedPrice =0.0f;
		double finalActualPrice = 0.0f;
		float percentage = 0.0f;
		
		
		if(planDuration.equalsIgnoreCase("30DAY")) {
			ajaxRestResponseForPriceCalculation.setPlanDuration("30 Days");
			setValueOnBeneficiary(ajaxRestResponseForPriceCalculation, beneficiaryCount, unitPriceOneBen, unitPriceTwoBen, unitPriceThreeBen);
			
			basePriceOnDuration = ajaxRestResponseForPriceCalculation.getDiscountedPriceForTotalBeneficiaryDouble()*1;
			ajaxRestResponseForPriceCalculation.setBasePriceForSelectedDuration(Utils.formattedCurrency(basePriceOnDuration+""));
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForSelectedDuration(Utils.formattedCurrency(basePriceOnDuration+""));
			ajaxRestResponseForPriceCalculation.setActualPlanPrice(Utils.formattedCurrency((ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiaryDouble()*1)+""));
			ajaxRestResponseForPriceCalculation.setFinalDiscountedPrice(Utils.formattedCurrency(basePriceOnDuration+""));
			ajaxRestResponseForPriceCalculation.setTotalGain("0% Off");
			ajaxRestResponseForPriceCalculation.setDiscountPercentageForFamilyPlan("0%");
			
			ajaxRestResponseForPriceCalculation.setPlanEndDate(Utils.getDateAdditionValue(30));
			ajaxRestResponseForPriceCalculation.setPlanEndDateStr(Utils.timestampToFormattedString(Utils.getDateAdditionValue(30), "d MMM yyyy hh:mm aaa"));

			finalActualPrice = ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiaryDouble()*1;
			finalDiscountedPrice = basePriceOnDuration;
			percentage = (float)(100 - (((float)finalDiscountedPrice / (float)finalActualPrice) * 100));
			ajaxRestResponseForPriceCalculation.setTotalGain(Math.round(percentage)+"% Off !!");
		
		
		}else if(planDuration.equalsIgnoreCase("90DAY")) {
			ajaxRestResponseForPriceCalculation.setPlanDuration("90 Days");
			setValueOnBeneficiary(ajaxRestResponseForPriceCalculation, beneficiaryCount, unitPriceOneBen, unitPriceTwoBen, unitPriceThreeBen);
			
			basePriceOnDuration = ajaxRestResponseForPriceCalculation.getDiscountedPriceForTotalBeneficiaryDouble()*3;
			ajaxRestResponseForPriceCalculation.setBasePriceForSelectedDuration(Utils.formattedCurrency(basePriceOnDuration+""));
			
			int percentageOfffromDb = planDetailsDao.getDiscountInPercentage(planDuration);
			float percentageFactor = ((float) percentageOfffromDb )/100;
			ajaxRestResponseForPriceCalculation.setDiscountPercentageForFamilyPlan(percentageOfffromDb+"%");
			
			finalDiscountedPrice = basePriceOnDuration - (basePriceOnDuration*percentageFactor); //eg for 10% percentageFactor = 10/100 = 0.1;
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForSelectedDuration(Utils.formattedCurrency(finalDiscountedPrice+""));
			ajaxRestResponseForPriceCalculation.setFinalDiscountedPrice(Utils.formattedCurrency(finalDiscountedPrice+""));
			
			finalActualPrice = ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiaryDouble()*3;
			ajaxRestResponseForPriceCalculation.setActualPlanPrice(Utils.formattedCurrency(finalActualPrice+""));
			
			percentage = (float)(100 - (((float)finalDiscountedPrice / (float)finalActualPrice) * 100));
			ajaxRestResponseForPriceCalculation.setTotalGain(Math.round(percentage)+"% Off !!");
			
			ajaxRestResponseForPriceCalculation.setPlanEndDate(Utils.getDateAdditionValue(90));
			ajaxRestResponseForPriceCalculation.setPlanEndDateStr(Utils.timestampToFormattedString(Utils.getDateAdditionValue(90), "d MMM yyyy hh:mm aaa"));
			//ajaxRestResponseForPriceCalculation.setTotalGain("0% Off");
		}else if(planDuration.equalsIgnoreCase("180DAY")) {
			ajaxRestResponseForPriceCalculation.setPlanDuration("180 Days");
			setValueOnBeneficiary(ajaxRestResponseForPriceCalculation, beneficiaryCount, unitPriceOneBen, unitPriceTwoBen, unitPriceThreeBen);
			
			basePriceOnDuration = ajaxRestResponseForPriceCalculation.getDiscountedPriceForTotalBeneficiaryDouble()*6;
			ajaxRestResponseForPriceCalculation.setBasePriceForSelectedDuration(Utils.formattedCurrency(basePriceOnDuration+""));
			
			int percentageOfffromDb = planDetailsDao.getDiscountInPercentage(planDuration);
			float percentageFactor = ((float) percentageOfffromDb )/100;
			ajaxRestResponseForPriceCalculation.setDiscountPercentageForFamilyPlan(percentageOfffromDb+"%");
			
			finalDiscountedPrice = basePriceOnDuration - (basePriceOnDuration*percentageFactor);
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForSelectedDuration(Utils.formattedCurrency(finalDiscountedPrice +""));
			ajaxRestResponseForPriceCalculation.setFinalDiscountedPrice(Utils.formattedCurrency(finalDiscountedPrice+""));
			
			finalActualPrice = ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiaryDouble()*6;
			ajaxRestResponseForPriceCalculation.setActualPlanPrice(Utils.formattedCurrency(finalActualPrice+""));
			
			percentage = (float)(100 - (((float)finalDiscountedPrice / (float)finalActualPrice) * 100));
			ajaxRestResponseForPriceCalculation.setTotalGain(Math.round(percentage)+"% Off !!");
			
			ajaxRestResponseForPriceCalculation.setPlanEndDate(Utils.getDateAdditionValue(180));
			ajaxRestResponseForPriceCalculation.setPlanEndDateStr(Utils.timestampToFormattedString(Utils.getDateAdditionValue(180), "d MMM yyyy hh:mm aaa"));
			
		}else if(planDuration.equalsIgnoreCase("365DAY")) {
			ajaxRestResponseForPriceCalculation.setPlanDuration("1 Year");
			setValueOnBeneficiary(ajaxRestResponseForPriceCalculation, beneficiaryCount, unitPriceOneBen, unitPriceTwoBen, unitPriceThreeBen);
			
			basePriceOnDuration = ajaxRestResponseForPriceCalculation.getDiscountedPriceForTotalBeneficiaryDouble()*12;
			ajaxRestResponseForPriceCalculation.setBasePriceForSelectedDuration(Utils.formattedCurrency(basePriceOnDuration+""));
			
			int percentageOfffromDb = planDetailsDao.getDiscountInPercentage(planDuration);
			float percentageFactor = ((float) percentageOfffromDb )/100;
			ajaxRestResponseForPriceCalculation.setDiscountPercentageForFamilyPlan(percentageOfffromDb+"%");
			
			finalDiscountedPrice = basePriceOnDuration - (basePriceOnDuration*percentageFactor);
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForSelectedDuration(Utils.formattedCurrency(finalDiscountedPrice+""));
			ajaxRestResponseForPriceCalculation.setFinalDiscountedPrice(Utils.formattedCurrency(finalDiscountedPrice+""));
			
			finalActualPrice = ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiaryDouble()*12;
			ajaxRestResponseForPriceCalculation.setActualPlanPrice(Utils.formattedCurrency(finalActualPrice+""));
			
			percentage = (float)(100 - (((float)finalDiscountedPrice / (float)finalActualPrice) * 100));
			ajaxRestResponseForPriceCalculation.setTotalGain(Math.round(percentage)+"% Off !!");
			
			ajaxRestResponseForPriceCalculation.setPlanEndDate(Utils.getDateAdditionValue(365));
			ajaxRestResponseForPriceCalculation.setPlanEndDateStr(Utils.timestampToFormattedString(Utils.getDateAdditionValue(365), "d MMM yyyy hh:mm aaa"));
			
		}else {
			throw new UserExceptionInvalidData("Invalid Duration :"+ planDuration);
		}
		logger.info("Values Set :"+ajaxRestResponseForPriceCalculation.toString());
		//This is Just for Review Purpose.
		ajaxRestResponseForPriceCalculation.setPlanFinalPrice(ajaxRestResponseForPriceCalculation.getDiscountedPriceForSelectedDuration());
		
		return ajaxRestResponseForPriceCalculation;
	}
	
	
	
	
	public void setValueOnBeneficiary(AjaxRestResponseForPriceCalculation ajaxRestResponseForPriceCalculation, 
																		String beneficiaryCount, 
																		double unitPriceOneBen, 
																		double unitPriceTwoBen,
																		double unitPriceThreeBen) throws UserExceptionInvalidData {
		
		if(beneficiaryCount.equalsIgnoreCase("1BEN")) {
			ajaxRestResponseForPriceCalculation.setBeneficiaryCount("1 Beneficiary");
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiary(Utils.formattedCurrency(unitPriceOneBen+""));
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiaryDouble(unitPriceOneBen);
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiary(Utils.formattedCurrency(unitPriceOneBen+""));
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiaryDouble(unitPriceOneBen);
		}else if(beneficiaryCount.equalsIgnoreCase("2BEN")) {
			ajaxRestResponseForPriceCalculation.setBeneficiaryCount("2 Beneficiaries");
			double twoBenBasePrice = unitPriceOneBen * 2;
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiary(Utils.formattedCurrency(twoBenBasePrice+""));
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiaryDouble(twoBenBasePrice);
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiary(Utils.formattedCurrency(unitPriceTwoBen+""));
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiaryDouble(unitPriceTwoBen);
		}else if(beneficiaryCount.equalsIgnoreCase("3BEN")) {
			ajaxRestResponseForPriceCalculation.setBeneficiaryCount("3 Beneficiaries");
			double threeBenBasePrice = unitPriceOneBen * 3;
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiary(Utils.formattedCurrency(threeBenBasePrice+""));
			ajaxRestResponseForPriceCalculation.setBasePriceForTotalBeneficiaryDouble(threeBenBasePrice);
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiary(Utils.formattedCurrency(unitPriceThreeBen+""));
			ajaxRestResponseForPriceCalculation.setDiscountedPriceForTotalBeneficiaryDouble(unitPriceThreeBen);
		}else {
			throw new UserExceptionInvalidData("Invalid Selection :"+ beneficiaryCount);
		}
	}


	@Override
	public int saveOfferDetails(AjaxRestResponseForPriceCalculation ajaxRestResponseForPriceCalculation,
											String customerId, String beneficiary1, String beneficiary2, String beneficiary3, 
											String planDurationCode, String beneficiaryCountCode) throws Exception {
		
		int offerId = planDetailsDao.saveOfferDetails(ajaxRestResponseForPriceCalculation.getPlanStartDate(),
														ajaxRestResponseForPriceCalculation.getPlanEndDate(),
														ajaxRestResponseForPriceCalculation.getPlanName(),
														planDurationCode,
														beneficiaryCountCode,
														customerId,
														beneficiary1+"~"+beneficiary2+"~"+beneficiary3,
														ajaxRestResponseForPriceCalculation.getPlanPricePerPersonPerMonth(),
														ajaxRestResponseForPriceCalculation.getBeneficiaryCount(),
														ajaxRestResponseForPriceCalculation.getBasePriceForTotalBeneficiary(), //BASE_PRICE_FOR_TOTAL_BENEFECIARY
														ajaxRestResponseForPriceCalculation.getDiscountedPriceForTotalBeneficiary(), //DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY
														ajaxRestResponseForPriceCalculation.getPlanDuration(),						//PLAN_DURATION_TO_DISPLAY
														ajaxRestResponseForPriceCalculation.getDiscountPercentageForFamilyPlan(), //DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN
														ajaxRestResponseForPriceCalculation.getBasePriceForSelectedDuration(),     //BASE_PRICE_FOR_SELECTED_DURATION
														ajaxRestResponseForPriceCalculation.getDiscountedPriceForSelectedDuration(), //DISCOUNTED_PRICE_FOR_SELECTED_DURATION
														ajaxRestResponseForPriceCalculation.getFinalDiscountedPrice(),         //FINAL_DISCOUNTED_PRICE
														ajaxRestResponseForPriceCalculation.getTotalGain());
		
		return offerId;
	}

	
	
	/**
	 * Method to save the Plan Details.
	 * 1. Need to generate a InvoiceID for Plan.
	 * 2. Need to use this Invoice ID as Key to pull the Plan Details.
	 * 3. We will make an entry in the Purchase History Table, with the Ack ID from Payment gateWay.
	 * 4. Based on the No of Beneficiary, there will be entry for each in the Relation table and Activity History Table.
	 * 		We will use the PriceBreakupID received from the Ajax key to generate the invoice, so it will be stored in the relation Table.
	 * 5. In the Price Breakup and Offer Table, we can update the purchaseFlag i.e. IS_PLAN_PURCHASED = 'Y' and update the InvoiceID.
	 * All the above DB operations will be done in Transaction.
	 */
	@Override
	public String savePlanDetails(PlanSubscriptionForUserBean planSubscriptionForUserBean, String custId) throws Exception {
		
		TransactionStatus txnStatus = null;
		DefaultTransactionDefinition def = null;
		boolean status = false;
		boolean rollbackNeeded = true;
		
		try {
			int getNextID = userAuthDao.getNextID();
			String userSelectedPlan = planSubscriptionForUserBean.getPlanID();
			String customerPlanInvoiceId = Utils.getNextPlanInvoiceID(Constant.REQUEST_PLAN,userSelectedPlan, getNextID);
			logger.info(userSelectedPlan +"/" + customerPlanInvoiceId);
			//Validate the offerID, it shouldn't be blank.
			if(StringUtils.isEmpty(planSubscriptionForUserBean.getOfferId())) {
				rollbackNeeded = false;
				throw new UserExceptionInvalidData("Exception Occured while processing the plan purchase. Please call the Customer Service");
			}
			
			//Validate the selected Beneficiary is Matching with the BeneficiaryCount.
			if(StringUtils.isEmpty(planSubscriptionForUserBean.getBeneficiaryCount())) {
				rollbackNeeded = false;
				throw new UserExceptionInvalidData("Exception Occured while processing the plan purchase. Possible error, beneficiary count is blank. Logout and try again.");
			}
			
			//Validate the Beneficiary ID list is not blank.
			if(ArrayUtils.isEmpty(planSubscriptionForUserBean.getBeneficiaryIdArr())) {
				rollbackNeeded = false;
				throw new UserExceptionInvalidData("Exception Occured while processing the plan purchase. The Beneficiary name list might be blank, Logout and try again.");
			}
			
			//The Selected Beneficiary Names and the Slected Count Should Match.
			int beneficiaryCount = Utils.getBeneficiaryCountFromDropdownVal(planSubscriptionForUserBean.getBeneficiaryCount());
			int beneficiaryIdArrayCount = planSubscriptionForUserBean.getBeneficiaryIdArr().length;
			//User selected 3BEN but selected only one Beneficiary.
			if(beneficiaryCount > beneficiaryIdArrayCount) {
				rollbackNeeded = false;
				throw new UserExceptionInvalidData("Can proceed with the Purchase, You selected "+beneficiaryCount+ " beneficiary Plan, but only selected "+ beneficiaryIdArrayCount +" Beneficiary Name."
						+ " Make the necessary correction and try again.");
			}
			logger.info("Validation Successful!!");
			//------------------- V A L I D A T I O N    E N D   -------------------------
			
			String purchaseDetails = planSubscriptionForUserBean.getBeneficiaryCount()
								+"~"+planSubscriptionForUserBean.getPlanDuration();
			String paymentStatus = Constant.TICKET_STATUS_PENDING_PAYMENT;
			
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			{
					txnStatus = platformTransactionManager.getTransaction(def);
					
					//1. First we will Insert PURCHASE_HISTORY TABLE
					status = serviceRequestDao.insertIntoPurchaseHistory(customerPlanInvoiceId,
																		 planSubscriptionForUserBean.getPlanID(),
																		 purchaseDetails,
																		 paymentStatus,
																		 "",
																		 "",
																		 planSubscriptionForUserBean.getCalculatedPrice()
																		 );
					//2. Price Breakup Table.
					status = planDetailsDao.updatePriceBreakupOfferTable(planSubscriptionForUserBean.getOfferId());
					
					Timestamp planExpireDate = planDetailsDao.getPlanExpireDate(planSubscriptionForUserBean.getOfferId());
					
					
					//This Loop is for Each Beneficiary.
					for (String beneficiaryId : planSubscriptionForUserBean.getBeneficiaryIdArr()){

						String beneficiaryName = userAuthDao.getUserFirstAndLastName(beneficiaryId);	
						//3. Insert into Activity History.
						String activityNotes = "User "+custId + " Purchased a Plan "
											 +customerPlanInvoiceId+" for "+beneficiaryName+".";
						status = historyDao.logActivityHistory(beneficiaryId,
															   custId, 
															   activityNotes);
						
						//4. Update Relation Table.
						status = planDetailsDao.setPlanDetailsInRelationTable(beneficiaryId, custId , 															
																planSubscriptionForUserBean.getPlanID(),
																planSubscriptionForUserBean.getOfferId(),
																planExpireDate,
																customerPlanInvoiceId);
						

					}
					
					
					
					//5. --> Increment the next ID +1
					status = userRegistrationDao.updateNextUid(getNextID+1);
							
					platformTransactionManager.commit(txnStatus);
					return customerPlanInvoiceId;

				
			}
			
		}catch(UserExceptionInvalidData ex) {
			//logger.error(ex.getMessage());
			if(rollbackNeeded) {
				platformTransactionManager.rollback(txnStatus);
				logger.info("Plan save - txn rolled back due to user exception");
			}
			throw ex;
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			platformTransactionManager.rollback(txnStatus);
			logger.info("Plan save - txn rolled back");
			throw ex;
		}
	}

	@Override
	public PlanInvoiceEmailBean getPlanInvoiceDetailsForEmail(String customerId, String[] beneficiaryArray, String customerPlanInvoiveId, String offerId, String customerEmail)
			throws Exception {
		try {
			PriceBreakupAndOfferBean priceBreakupAndOfferBean = planDetailsDao.getDetailsFromPriceBreakupTable(offerId);
			logger.info("Data from Database for "+offerId +"-->"+priceBreakupAndOfferBean);
			UserDetailsBean userDetailBean = userDetailsDao.getUserDetails(customerId);
			
			int monthsToMultiply = 1;
			int benefeciaryToMultiply = 1;
			
			PlanInvoiceEmailBean planInvoiceEmailBean = new PlanInvoiceEmailBean();
			planInvoiceEmailBean.setInvoiceId(customerPlanInvoiveId);
			planInvoiceEmailBean.setInvoiceCreationDate(new SimpleDateFormat("MMMMM d, yyyy").format(new Date()));			
			planInvoiceEmailBean.setPlanStartDate(Utils.timestampToFormattedString(priceBreakupAndOfferBean.getPlanStartDate(), "MMMMM d, yyyy"));
			planInvoiceEmailBean.setPlanEndDate(Utils.timestampToFormattedString(priceBreakupAndOfferBean.getPlanEndDate(), "MMMMM d, yyyy"));
			planInvoiceEmailBean.setPlanName(priceBreakupAndOfferBean.getPlanName()); //Gold Plan
			
			//Duration
			String durationCode = priceBreakupAndOfferBean.getPlanDurationCode();
			if(durationCode.equalsIgnoreCase(Constant.ONE_MONTH)) {
				planInvoiceEmailBean.setPlanDuration("30 Days");
				monthsToMultiply =1;
			}else if(durationCode.equalsIgnoreCase(Constant.THREE_MONTH)) {
				planInvoiceEmailBean.setPlanDuration("90 Days");
				monthsToMultiply =3;
			}else if(durationCode.equalsIgnoreCase(Constant.SIX_MONTH)) {
				planInvoiceEmailBean.setPlanDuration("180 Days");
				monthsToMultiply =6;
			}else if(durationCode.equalsIgnoreCase(Constant.ONE_YEAR)) {
				planInvoiceEmailBean.setPlanDuration("365 Days");
				monthsToMultiply =12;
			}
			
			//Beneficiary Count
			String beneficiaryCode = priceBreakupAndOfferBean.getPlanBeneficiaryCountCode();
			if(beneficiaryCode.equalsIgnoreCase(Constant.ONE_BENEFICIARY)) {
				planInvoiceEmailBean.setBeneficiaryCount("1 Beneficiary");
				benefeciaryToMultiply =1;
			}else if(beneficiaryCode.equalsIgnoreCase(Constant.TWO_BENEFICIARY)) {
				planInvoiceEmailBean.setBeneficiaryCount("2 Beneficiaries");
				benefeciaryToMultiply =2;
			}else if(beneficiaryCode.equalsIgnoreCase(Constant.THREE_BENEFICIARY)) {
				planInvoiceEmailBean.setBeneficiaryCount("3 Beneficiaries");
				benefeciaryToMultiply =3;
			}
			
			//Josthi Address
			planInvoiceEmailBean.setJosthiAddressLine1(Constant.JOSTHI_ADDRESS_LINE_1);
			planInvoiceEmailBean.setJosthiAddressLine2(Constant.JOSTHI_ADDRESS_LINE_2);
			planInvoiceEmailBean.setJosthiCity(Constant.JOSTHI_CITY);
			planInvoiceEmailBean.setJosthiPin(Constant.JOSTHI_PIN);
			planInvoiceEmailBean.setJosthiState("West Bengal");
			planInvoiceEmailBean.setJosthiEmail(Constant.JOSTHI_EMAIL);
			planInvoiceEmailBean.setJosthiContactNumber(Constant.JOSTHI_CONTACT);
			
			//Host Customer Details
			planInvoiceEmailBean.setHostCustomerName(userDetailBean.getFirstName()+" "+userDetailBean.getLastName());
			planInvoiceEmailBean.setHostCustomerAddressLine1(userDetailBean.getUserAddressFirstLine());
			planInvoiceEmailBean.setHostCustomerAddressLine2(userDetailBean.getUserAddressSecondLine());
			planInvoiceEmailBean.setHostCustomerState(userDetailBean.getState());
			planInvoiceEmailBean.setHostCustomerCityTown(userDetailBean.getCityTown());
			planInvoiceEmailBean.setHostCustomerZipPin(userDetailBean.getZipPin());
			planInvoiceEmailBean.setHostCustomerCountry(userDetailBean.getCountry());
			planInvoiceEmailBean.setHostCustomerEmail(customerEmail);
			
			//Payment Details
			planInvoiceEmailBean.setPaymentMethod("Online");
			planInvoiceEmailBean.setPaymentId("1234567890");
			planInvoiceEmailBean.setPaymentStatus("Completed");
			
			//Beneficiary Names.
			StringBuffer beneficiaryNames = new StringBuffer();
			for(String beneficiaryId : beneficiaryArray) {
				beneficiaryNames.append(userAuthDao.getUserFirstAndLastName(beneficiaryId));
				beneficiaryNames.append(" , ");
			}
			
			String beneficiaryNamesToDisplay = beneficiaryNames.toString();
			beneficiaryNamesToDisplay =	StringUtils.substring(beneficiaryNamesToDisplay, 0, beneficiaryNamesToDisplay.length() - 1);
			planInvoiceEmailBean.setBeneficiaryNames(beneficiaryNamesToDisplay);
			planInvoiceEmailBean.setBeneficiaryIds(priceBreakupAndOfferBean.getBreakupRequestedFor());
			
			
			//Plan Details
			String planDetails = "("+priceBreakupAndOfferBean.getPlanName()+"/"+planInvoiceEmailBean.getBeneficiaryCount()+"/"+planInvoiceEmailBean.getPlanDuration()+" )";
			planInvoiceEmailBean.setPlanDetails(planDetails);
			
			//Actual Price = Unit Price(on plan) X no of Months X no of Beneficiary
			String actualPriceStr = priceBreakupAndOfferBean.getPlanPricePerPersonPerMonth();
			double pricePerPersonPerMonth= Utils.getDoubleValue(actualPriceStr);
			double actualPrice = pricePerPersonPerMonth * monthsToMultiply * benefeciaryToMultiply;
			planInvoiceEmailBean.setActualPlanPrice(Utils.formattedCurrency(actualPrice+""));
			logger.info("actualPrice : "+actualPrice);
			//Family Discount for
			planInvoiceEmailBean.setFamilyDiscountfor("Family Plan Discount(for "+benefeciaryToMultiply+" Beneficiary)");
			
			//Family Discount in INR.
			String discountPriceStr = priceBreakupAndOfferBean.getDiscountedPriceForTotalBenefeciary();
			logger.info("discountPriceStr : "+discountPriceStr);
			double familyDiscountPricePerMonth = Utils.getDoubleValue(discountPriceStr);
			//Discounted Price on beneficiary count X months.
			logger.info("familyDiscountPricePerMonth : "+familyDiscountPricePerMonth);
			double totalPriceAfterDiscount = familyDiscountPricePerMonth * monthsToMultiply;
			logger.info("totalPriceAfterDiscount : "+totalPriceAfterDiscount);
			double discountedPriceValue = actualPrice - totalPriceAfterDiscount;
			planInvoiceEmailBean.setFamilyDiscountPrice(Utils.formattedCurrency(discountedPriceValue+""));	
			logger.info("discountedPriceValue : "+discountedPriceValue);
			
			//Discount for LongTerm Plan
			planInvoiceEmailBean.setLongTermDiscountFor("Long Term '"+planInvoiceEmailBean.getPlanDuration()+"' Plan Discount ("+priceBreakupAndOfferBean.getDiscountPercentageForFamilyPlan()+")");
			
			String discountPercentage = priceBreakupAndOfferBean.getDiscountPercentageForFamilyPlan();
			logger.info("discountPercentage :"+discountPercentage);
			int percentage = Integer.parseInt(StringUtils.substring(discountPercentage, 0, discountPercentage.length() - 1));
			logger.info("percentage :"+percentage);
			double priceDiscountForLongTerm = 0.00;
			if(percentage == 0) {
				priceDiscountForLongTerm = 0.00;
			}else {
				priceDiscountForLongTerm = ((double) ((percentage * totalPriceAfterDiscount)/100));
			}
			logger.info("priceDiscountForLongTerm : "+priceDiscountForLongTerm);
			planInvoiceEmailBean.setLongTermDiscountPrice(Utils.formattedCurrency(priceDiscountForLongTerm+""));
			
			//Final Price
			double finalPrice = actualPrice - (discountedPriceValue + priceDiscountForLongTerm);
			logger.info("finalPrice : "+finalPrice);
			planInvoiceEmailBean.setFinalPrice(Utils.formattedCurrency(finalPrice+""));
			
			//Final Percentage Savings
			float FinalPercentageSavings = (float)(100 - (((float)finalPrice / (float)actualPrice) * 100));		
			planInvoiceEmailBean.setFinalDiscount(Math.round(FinalPercentageSavings)+"% Off !!");
			
			return planInvoiceEmailBean;
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
	}

	@Override
	public Map<String, String> getMapForInvoiceEmail(PlanInvoiceEmailBean planInvoiceEmailBean) throws Exception {
       
		Map<String, String> serviceEmailMap = new HashMap<String, String>();
        serviceEmailMap.put("invoiceId", planInvoiceEmailBean.getInvoiceId());
        serviceEmailMap.put("invoiceCreationDate", planInvoiceEmailBean.getInvoiceCreationDate());
        serviceEmailMap.put("planStartDate", planInvoiceEmailBean.getPlanStartDate());       	        
        serviceEmailMap.put("planEndDate", planInvoiceEmailBean.getPlanEndDate());
        serviceEmailMap.put("planName", planInvoiceEmailBean.getPlanName());
        serviceEmailMap.put("planDuration", planInvoiceEmailBean.getPlanDuration());
        serviceEmailMap.put("beneficiaryCount", planInvoiceEmailBean.getBeneficiaryCount());
        serviceEmailMap.put("assignedAgentId", planInvoiceEmailBean.getAssignedAgentId());
        serviceEmailMap.put("assignedAgentName", planInvoiceEmailBean.getAssignedAgentName());
        
        serviceEmailMap.put("josthiAddressLine1",planInvoiceEmailBean.getJosthiAddressLine1());
        serviceEmailMap.put("josthiAddressLine2",planInvoiceEmailBean.getJosthiAddressLine2());
        serviceEmailMap.put("josthiCity",planInvoiceEmailBean.getJosthiCity());
        serviceEmailMap.put("josthiPin",planInvoiceEmailBean.getJosthiPin());
        serviceEmailMap.put("josthiState",planInvoiceEmailBean.getJosthiState());
        serviceEmailMap.put("josthiEmail",planInvoiceEmailBean.getJosthiEmail());       
        serviceEmailMap.put("josthiContactNumber",planInvoiceEmailBean.getJosthiContactNumber());
        
        serviceEmailMap.put("hostCustomerName",planInvoiceEmailBean.getHostCustomerName());
        serviceEmailMap.put("hostCustomerAddressLine1",planInvoiceEmailBean.getHostCustomerAddressLine1());
        serviceEmailMap.put("hostCustomerAddressLine2",planInvoiceEmailBean.getHostCustomerAddressLine2());
        serviceEmailMap.put("hostCustomerCityTown",planInvoiceEmailBean.getHostCustomerCityTown());
        serviceEmailMap.put("hostCustomerState",planInvoiceEmailBean.getHostCustomerState());
        serviceEmailMap.put("hostCustomerZipPin",planInvoiceEmailBean.getHostCustomerZipPin());
        serviceEmailMap.put("hostCustomerCountry",planInvoiceEmailBean.getHostCustomerCountry());
        serviceEmailMap.put("hostCustomerEmail",planInvoiceEmailBean.getHostCustomerEmail());
        //Price Section.
        serviceEmailMap.put("paymentMethod",planInvoiceEmailBean.getPaymentMethod());
        serviceEmailMap.put("paymentId",planInvoiceEmailBean.getPaymentId());
        serviceEmailMap.put("paymentStatus",planInvoiceEmailBean.getPaymentStatus());
        
        serviceEmailMap.put("beneficiaryNames",planInvoiceEmailBean.getBeneficiaryNames());
        serviceEmailMap.put("beneficiaryIds",planInvoiceEmailBean.getBeneficiaryIds());
        
        serviceEmailMap.put("planDetails",planInvoiceEmailBean.getPlanDetails());
        serviceEmailMap.put("actualPlanPrice",planInvoiceEmailBean.getActualPlanPrice());
        serviceEmailMap.put("familyDiscountfor",planInvoiceEmailBean.getFamilyDiscountfor());
        serviceEmailMap.put("familyDiscountPrice",planInvoiceEmailBean.getFamilyDiscountPrice());
        serviceEmailMap.put("longTermDiscountFor",planInvoiceEmailBean.getLongTermDiscountFor());
        serviceEmailMap.put("longTermDiscountPrice",planInvoiceEmailBean.getLongTermDiscountPrice());
        
        serviceEmailMap.put("finalPrice",planInvoiceEmailBean.getFinalPrice());
        serviceEmailMap.put("finalDiscount",planInvoiceEmailBean.getFinalDiscount());
        
        return serviceEmailMap;
	}

}

package com.josthi.web.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.josthi.web.bo.AjaxRestResponse;
import com.josthi.web.bo.AjaxRestResponseForPriceCalculation;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.ValidateSession;

@RestController
public class UserBuyPlanController {

	private static final Logger logger = LoggerFactory.getLogger(UserBuyPlanController.class);
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	ServiceRequestService serviceRequestService;
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	
	
	
	@RequestMapping(value = "/getPlanAndPriceBreakupOnSelectedCriteria", 
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE, 
    		consumes = MediaType.APPLICATION_JSON_VALUE)

		public @ResponseBody AjaxRestResponseForPriceCalculation getPlanAndPriceBreakupOnSelectedCriteria(@RequestBody String jsonString,
				 															HttpServletRequest request) {	
		logger.info("---> Ajax request for :"+jsonString);
		try {
			
			Gson gson = new Gson(); 
			JsonObject jsonObject = gson.fromJson( jsonString,JsonObject.class);
			
			
			String customerId = (jsonObject.get("customerId").toString()).replace("\"","");
			String beneficiary1 = (jsonObject.get("beneficiary1").toString()).replace("\"","");
			String beneficiary2 = (jsonObject.get("beneficiary2").toString()).replace("\"","");
			String beneficiary3 = (jsonObject.get("beneficiary3").toString()).replace("\"","");
			String selectedPlan = (jsonObject.get("selectedPlan").toString()).replace("\"","");
			String planDuration = (jsonObject.get("planDuration").toString()).replace("\"","");
			String beneficiaryCount = (jsonObject.get("beneficiaryCount").toString()).replace("\"","");
	
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerId);
			
			
			if(selectedPlan == null || selectedPlan.isEmpty() || 
					planDuration == null || planDuration.isEmpty() ||
					beneficiaryCount == null || beneficiaryCount.isEmpty() ||
					customerId == null || customerId.isEmpty()) {
				throw new UserExceptionInvalidData("Invalid selection, Looks like some values are missing. Please logout and re-login.");
			}
			
			
			// Ajax request for :{"customerId":"RU200921008",
			//"beneficiary1":"BE201111001",
			//"beneficiary2":"BE201119001",
			//"beneficiary3":"BE201218010",
			//"selectedPlan":"Gold",
			//"planDuration":"365DAY",
			//"beneficiaryCount":"3BEN"}

			AjaxRestResponseForPriceCalculation ajaxRestResponseForPriceCalculation = planAndBenefitService.getCleanPriceBreakup(customerId, selectedPlan, planDuration, beneficiaryCount);
			ajaxRestResponseForPriceCalculation.setStatus(Constant.AJAX_SUCCESS);
			
			
			int offerId = planAndBenefitService.saveOfferDetails(ajaxRestResponseForPriceCalculation, customerId, 
																beneficiary1, beneficiary2, beneficiary3,planDuration, beneficiaryCount);
			ajaxRestResponseForPriceCalculation.setOfferId(offerId);
			
			logger.info(ajaxRestResponseForPriceCalculation.toString());
			return ajaxRestResponseForPriceCalculation;
			
			}catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponseForPriceCalculation(Constant.AJAX_EXCEPTION,ex.getMessage());
			}catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponseForPriceCalculation(Constant.AJAX_EXCEPTION,ex.getMessage());			
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponseForPriceCalculation(Constant.AJAX_EXCEPTION,"Error Occured, please logout and try again.");
			}
		
		
		}
}

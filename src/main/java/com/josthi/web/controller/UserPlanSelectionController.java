package com.josthi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanInvoiceEmailBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PlanSubscriptionForUserBean;
import com.josthi.web.bo.PriceBreakupAndOfferBean;
import com.josthi.web.bo.PurchasedPlanToDisplay;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserPlanSelectionController {
	
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	EmailService emailService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserPlanSelectionController.class);
	
	@GetMapping("/user/subscribePlan")
	public String userSubscribePlan(Model model,
				@RequestParam (name="status", required = false, defaultValue = "") String status,
				@RequestParam (name="message", required = false, defaultValue = "") String message,
				HttpServletRequest request) {
		
		
		String actionStatus = "";
		String displayMsg = "";
		try {
			
			ValidateSession.isValidSession(request);
			
			String hostCustomerId=ValidateSession.getUserId(request);
			
			//Used for CARD 1
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			//Here we are displaying the plan the user already purchased.
			List<PurchasedPlanToDisplay> registeredPlanList = planAndBenefitService.getPreviousPurchasedPlan(hostCustomerId);
			model.addAttribute("registeredPlanList",registeredPlanList);
			
			
			//Logic to fetch the Plan details from Database.
			List<PlanAndBenefitBean> planAndBenefitBeanList = planAndBenefitService.getServiceAndPlanToDisplay();
			model.addAttribute("planAndBenefitBeanList",planAndBenefitBeanList);
			
			//Bean to get the values when ever User Submits a form.			
			model.addAttribute("planSubscriptionForUserBean",new PlanSubscriptionForUserBean());
			
			//Plan Selection Bean for User
			List <PlanSelectionForUserBean> planSelectionForUserBeanList = planAndBenefitService.getPlanDetailsToDisplay();
			
			model.addAttribute("planSelectionForUserBeanList",planSelectionForUserBeanList);
			
			//Beneficiary Count Selection(Single Plan/Family Plan)
			List<DropDownBean> planTypeList = cacheConfigService.getPLanType(Constant.PLAN_TYPE);
			model.addAttribute("planTypeList",planTypeList);
			
			//Plan Duration Selection
			List<DropDownBean> planDurationList = planAndBenefitService.getPlanDurationList();
			model.addAttribute("planDurationList",planDurationList);
			
			
			//Beneficiary Selection
			List<DropDownBean> beneficiaryList = planAndBenefitService.getBeneficiaryListForPlan(hostCustomerId);
			logger.info(beneficiaryList.toString());
			model.addAttribute("beneficiaryList",beneficiaryList);

			return MappingConstant.USER_SUBSCRIBE_PLAN;
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			
			//model.addAttribute("serviceRequestBean",new ServiceRequestBean());
			
			
			return MappingConstant.USER_SUBSCRIBE_PLAN;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message =  ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			//model.addAttribute("serviceRequestBean",new ServiceRequestBean());
						
			return MappingConstant.USER_SUBSCRIBE_PLAN;
		}
	}
	
	
	
	@RequestMapping(path ="/user/buyPlan/{custId}", method = RequestMethod.POST)
	public String buyPlan(Model model, PlanSubscriptionForUserBean planSubscriptionForUserBean,
															@PathVariable String custId,
															HttpServletRequest request) {
		
		logger.info("PlanSubscriptionForUserBean before Update:"+planSubscriptionForUserBean.toString());
		
		String actionStatus = "";
		String message = "";
		try {		
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, custId.trim());
			
			PlanSubscriptionForUserBean validatedPlanBean = planAndBenefitService.validateIncomingData(planSubscriptionForUserBean);
			
			String customerPlanInvoiveId = planAndBenefitService.savePlanDetails(validatedPlanBean, custId.trim());
								
			
				if(customerPlanInvoiveId!=null && customerPlanInvoiveId.length() > 0) {
					actionStatus = MessageConstant.USER_SUCCESS_STATUS;
					message = "Plan Purchase Successful, here is your Ack ID/ Invoice ID :"+customerPlanInvoiveId;
					
					String sessionEmailId = ValidateSession.getUserEmail(request);
					
					try {
						PlanInvoiceEmailBean planInvoiceEmailBean = planAndBenefitService.getCleanPlanInvoiceDetailsForEmail(custId.trim(),
																														planSubscriptionForUserBean.getBeneficiaryIdArr(),
																														customerPlanInvoiveId,
																														planSubscriptionForUserBean.getOfferId(),
																														sessionEmailId);
						
																														
						logger.debug("############ Data to print email :"+planInvoiceEmailBean.toString());																								
						logger.debug("############ EMAIL Sent to :" +sessionEmailId);
						if(planInvoiceEmailBean!=null) {
		                	//Service email 
		        	        
		        	        Map<String, String> serviceEmailMap = planAndBenefitService.getMapForInvoiceEmail(planInvoiceEmailBean);
		        	        
		        	        EmailDbBean emailDbBeanForService = Utils.getEmailBeanForPlanInvoive(sessionEmailId, customerPlanInvoiveId, Utils.mapToString(serviceEmailMap));
		        	        boolean buyPlanQueueStatus = emailService.queueEmail(emailDbBeanForService);
		        	        if(buyPlanQueueStatus) {
		        	        	EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
		        	        }
		    			}
					}catch(Exception ex) { // Here we are supressing the Email failure. so that the customer is not cofused.
						logger.error("Email Generation error for Invoive",ex);
						actionStatus = MessageConstant.USER_SUCCESS_STATUS;
						message = "Plan Purchase Successful, here is your Ack ID/ Invoice ID :"+customerPlanInvoiveId +". Please Note, the Invoice email failed. but you can view the invoice from the above link.";
					}
				}else{
					actionStatus = MessageConstant.USER_FAILURE_STATUS;
					message = "Ticket creation Failed.";
				}
				
				
				
				return MappingConstant.USER_SUBSCRIBE_PLAN_REDIRECT+"?status="+actionStatus+"&message="+message;
			
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return MappingConstant.USER_SUBSCRIBE_PLAN_REDIRECT+"?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while saving the Request. Call Customer Service.";
			return MappingConstant.USER_SUBSCRIBE_PLAN_REDIRECT+"?status="+actionStatus+"&message="+message;
		}
		
		
	}

}

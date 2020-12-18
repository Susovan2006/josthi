package com.josthi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanSelectionForUserBean;
import com.josthi.web.bo.PlanSubscriptionForUserBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserPlanSelectionController {
	
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
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

}

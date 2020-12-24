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

import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserPurchaseHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(UserPurchaseHistoryController.class);
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	
	@GetMapping("/user/purchaseHistory")
	public String userPurchaseHistory(Model model,
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
				
				List<PurchaseHistoryBean> purchaseHistoryPlanList = planAndBenefitService.getPurchaseDoneByCustomer(hostCustomerId, Constant.PURCHASE_TYPE_PLAN);
				model.addAttribute("purchaseHistoryPlanList",purchaseHistoryPlanList);			
				logger.info("Plan Purchase :"+purchaseHistoryPlanList.toString());
				
				List<PurchaseHistoryBean> purchaseHistoryServiceList = planAndBenefitService.getPurchaseDoneByCustomer(hostCustomerId, Constant.PURCHASE_TYPE_SERVICE);
				model.addAttribute("purchaseHistoryServiceList",purchaseHistoryServiceList);
				logger.info("Service Purchase :"+purchaseHistoryServiceList.toString());
			
			return MappingConstant.USER_PURCHASE_HISTORY;
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			
			//model.addAttribute("serviceRequestBean",new ServiceRequestBean());
			
			
			return MappingConstant.USER_PURCHASE_HISTORY;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message =  ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			//model.addAttribute("serviceRequestBean",new ServiceRequestBean());
						
			return MappingConstant.USER_PURCHASE_HISTORY;
		}
	}
	
}

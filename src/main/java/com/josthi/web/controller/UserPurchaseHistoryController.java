package com.josthi.web.controller;

import java.util.List;

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

import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.PlanInvoiceEmailBean;
import com.josthi.web.bo.PurchaseHistoryBean;
import com.josthi.web.bo.ServiceInvoiceBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserPurchaseHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(UserPurchaseHistoryController.class);
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	@Autowired
	ServiceRequestService serviceRequestService;
	
	
	@GetMapping("/user/purchaseHistory")
	public String userPurchaseHistory(Model model,
									@RequestParam (name="status", required = false, defaultValue = "") String status,
									@RequestParam (name="message", required = false, defaultValue = "") String message,
									@RequestParam (name="gotoPoint", required = false, defaultValue = "") String gotoPoint,
									HttpServletRequest request) {
			
			String actionStatus = "";
			String displayMsg = "";
			
			logger.info("GOTO : "+gotoPoint);
			try {
				
				ValidateSession.isValidSession(request);
				
				String hostCustomerId=ValidateSession.getUserId(request);
				
				//Used for CARD 1
				if(status!=null && status.length() > 0) {
			    	 model.addAttribute("status", status);
					 model.addAttribute("message", message);
		   	 	}
				
				if(gotoPoint!=null && gotoPoint.length()>0) {
					gotoPoint = gotoPoint.replace("\"", "");
					model.addAttribute("gotoPoint",gotoPoint);
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
	
	
	
	
	
	/**
	 * 
	 * @param model
	 * @param planId
	 * @param request
	 * @return
	 * 
	 * This method is for generating the Invoice for the Purchased Plan.
	 * 
	 */
	@RequestMapping(path ="/user/planPurchaseHistory/{planId}", method = RequestMethod.GET)
	public String ticketHistory(Model model,
									@PathVariable String planId,
									HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
		try {					
			ValidateSession.isValidSession(request);
			String hostCustomerId = ValidateSession.getUserId(request);
			String hostCustomerEmail = ValidateSession.getUserEmail(request);
			
			if(planId!=null && planId.length() > 0 && planAndBenefitService.isValidPlanId(planId)) {
				
				int getPlanPriceBreakUpId = planAndBenefitService.getPriceBreakUpId(hostCustomerId, planId);
				if(getPlanPriceBreakUpId == 0) {
					throw new UserExceptionInvalidData("Looks like there is no invoice present for :"+ planId);
				}
				
				PlanInvoiceEmailBean planInvoiceBean = planAndBenefitService.getCleanPlanInvoiceDetailsForEmail(hostCustomerId,
																					null,
																					planId,
																					getPlanPriceBreakUpId+"",
																					hostCustomerEmail);
				logger.info("***********************"+planInvoiceBean.toString());
				model.addAttribute("planInvoiceBean",planInvoiceBean);
				model.addAttribute("getPlanPriceBreakUpId",getPlanPriceBreakUpId);
				model.addAttribute("invoiceId",Utils.getInvoiceIdFromPlan(planId));
				
				return "user/purchase_invoice_Plan";

			 }else{
					//actionStatus = MessageConstant.USER_FAILURE_STATUS;
				 message = "Invalid Plan ID, please log out and re login.";
				 throw new UserExceptionInvalidData(message);
			 }

		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/purchaseHistory?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while Fetching the ticket history. Call Customer Service.";
			return "redirect:/user/purchaseHistory?status="+actionStatus+"&message="+message;
		}
	
	}
	
	
	
	/**
	 * 
	 * @param model
	 * @param planId
	 * @param request
	 * @return
	 * 
	 * This method is for generating the Invoice for the Purchased Plan.
	 * 
	 */
	@RequestMapping(path ="/user/servicePurchaseHistory/{ticketId}", method = RequestMethod.GET)
	public String servicePurchaseHistory(Model model,
									@PathVariable String ticketId,
									HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
		try {					
			ValidateSession.isValidSession(request);
			String hostCustomerId = ValidateSession.getUserId(request);
			String hostCustomerEmail = ValidateSession.getUserEmail(request);
			
			if(ticketId!=null && ticketId.length() > 0 && serviceRequestService.isValidTicket(ticketId)) {
				
				
				
				ServiceInvoiceBean serviceInvoiceBean = serviceRequestService.getDataForServiceInvoice(hostCustomerId, hostCustomerEmail, ticketId);
				
				
				logger.info("*********XXXXXX********"+serviceInvoiceBean.toString());
				model.addAttribute("serviceInvoiceBean",serviceInvoiceBean);
				model.addAttribute("invoiceId",Utils.getInvoiceIdFromTicket(ticketId));
				
				return "user/purchase_invoice_service";

			 }else{
					//actionStatus = MessageConstant.USER_FAILURE_STATUS;
				 message = "Invalid Ticket Number, please log out and re login.";
				 throw new UserExceptionInvalidData(message);
			 }

		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/purchaseHistory?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while Fetching the ticket history. Call Customer Service.";
			return "redirect:/user/purchaseHistory?status="+actionStatus+"&message="+message;
		}
	
	}
	
	
	
}

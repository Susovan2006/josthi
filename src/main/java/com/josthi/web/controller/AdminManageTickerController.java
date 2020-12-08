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

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;


@Controller
public class AdminManageTickerController {

	private static final Logger logger = LoggerFactory.getLogger(AdminManageTickerController.class);


	
	@Autowired
	ServiceRequestService serviceRequestService;
	
	@GetMapping("/admin/viewTicketRequest")
	public String agentAdminViewRequest(Model model, 
								@RequestParam (name="status", required = false, defaultValue = "") String status,
							    @RequestParam (name="message", required = false, defaultValue = "") String message,
								HttpServletRequest request) {
			
			
		try {
			ValidateSession.isValidSession(request);
			
			String userId = ValidateSession.getUserId(request);
			String userRole = ValidateSession.getUserRole(request);
			
			//Pulling all the active Tickets to be displayed on the Screen.
			List<ServiceRequestBean> adminServiceRequestBeanList= serviceRequestService.getServiceRequestList(userId, userRole);
			model.addAttribute("adminServiceRequestBeanList",adminServiceRequestBeanList);
			model.addAttribute("serviceRequestBean", new ServiceRequestBean());
			
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			return MappingConstant.ADMIN_MANAGE_TICKET;
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());			
			model.addAttribute("agentDetailsfromDb",new UserDetailsBean());
			return MappingConstant.ADMIN_MANAGE_TICKET;
		
    	}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			status = MessageConstant.USER_FAILURE_STATUS;
			message =  ex.getMessage();
			return "redirect:/login?status="+status+"&message="+message;
		
    	}catch(Exception ex) {
    		logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			model.addAttribute("agentDetailsfromDb",new UserDetailsBean());
						
			return MappingConstant.ADMIN_MANAGE_TICKET;
		}
	}
	
	
	
	
	@RequestMapping(path ="/admin/searchTicket/{custId}", method = RequestMethod.POST)
	public String searchTicket(Model model, ServiceRequestBean serviceRequestBean,
															@PathVariable String custId,
															HttpServletRequest request) {
		
		logger.info("serviceRequestBean before Update:"+serviceRequestBean.toString());
		
		String actionStatus = "";
		String message = "";
		try {		
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, custId.trim());
			
			logger.info("serviceRequestBean :"+serviceRequestBean.toString());	
				
				
			return "redirect:/admin/viewTicketRequest?status="+actionStatus+"&message="+message;
			
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/admin/viewTicketRequest?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while saving the Request. Call Customer Service.";
			return "redirect:/admin/viewTicketRequest?status="+actionStatus+"&message="+message;
		}
		
		
	}
	


}

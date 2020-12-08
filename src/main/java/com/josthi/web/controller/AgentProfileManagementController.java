package com.josthi.web.controller;

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
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.ValidateSession;


@Controller
public class AgentProfileManagementController {

	private static final Logger logger = LoggerFactory.getLogger(AgentProfileManagementController.class);
	
	@Autowired
    private UserDetailService userDetailService;
	
	
	
	@GetMapping("/admin/profile")
	public String getAgentProfile(Model model, 
							@RequestParam (name="status", required = false, defaultValue = "") String status,
						    @RequestParam (name="message", required = false, defaultValue = "") String message,
							HttpServletRequest request) {
		
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
        	logger.info("******"+userSessionBean.toString()); 
        	UserDetailsBean agentDetailsfromDb = userDetailService.getAgentAdminProfileDetails(userSessionBean.getCustomerId());
    		
        	model.addAttribute("agentDetailsfromDb",agentDetailsfromDb);
        	logger.info("agentDetailsfromDb:"+agentDetailsfromDb.toString());
        	if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}

        	return MappingConstant.ADMIN_PROFILE;
        	
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());			
			model.addAttribute("agentDetailsfromDb",new UserDetailsBean());
			return MappingConstant.ADMIN_PROFILE;
		
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
						
			return MappingConstant.ADMIN_PROFILE;
		}
    	
    }
	
	
	
	// 
	
    @RequestMapping(path ="/admin/profileUpdate/{custId}", method = RequestMethod.POST)
	public String agentProfileUpdate(Model model, UserDetailsBean userDetailsBean,  
									@PathVariable String custId,
									HttpServletRequest request) {
    	
		String actionStatus = "";
		String message = "";
    	try {
    		
    		ValidateSession.isValidSession(request);
    		ValidateSession.isValidUser(request, custId.trim());
    		
    		//logger.info("Data before Save @@@@@@@@@@"+userDetailsBean.toString());
    		logger.info("Data before Save @@@@@@@@@@"+userDetailsBean.getUid());
    		
    		boolean updateStatus = userDetailService.updateAgentAdminProfile(userDetailsBean);
    		
    		actionStatus = MessageConstant.USER_SUCCESS_STATUS;
    		message = "Data Updated Successfully";
    		return "redirect:/admin/profile?status="+actionStatus+"&message="+message;
    		
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/admin/profile?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while saving the Request. Call Customer Service.";
			return "redirect:/admin/profile?status="+actionStatus+"&message="+message;
		}
    	
    	

    }
	
	
	
}

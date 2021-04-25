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

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.ValidateSession;
import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.DropDownBean;

@Controller
public class AdminActionController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminActionController.class);

	
	@Autowired
	UserDetailService userDetailService;
	
	@GetMapping("/admin/adminActionAssignAgents")
	public String assignAgents(Model model, 
								@RequestParam (name="status", required = false, defaultValue = "") String status,
							    @RequestParam (name="message", required = false, defaultValue = "") String message,
								HttpServletRequest request) {
			
			
		try {
			ValidateSession.isValidSession(request);
			
			String userId = ValidateSession.getUserId(request);
			String userRole = ValidateSession.getUserRole(request);
			
			logger.info("%$%$%$%$%$"+userId+"/"+userRole);
			//TODO: Need to be Redirected to some error Page.
			if (!(userRole.equalsIgnoreCase(Constant.USER_TYPE_ADMIN))) {
				throw new UserException("You are not Authorised for this Action.");
			}
			//Pulling all the active Tickets to be displayed on the Screen.
			List<AgentAssignmentBean> agentAssignmentBeanList= userDetailService.getBeneficiaryAgentDetailsList();
			model.addAttribute("agentAssignmentBeanList",agentAssignmentBeanList);
			model.addAttribute("serviceRequestBean", new ServiceRequestBean());
			
			List<DropDownBean> agentList = userDetailService.getAgentListForDropDown();
	    	 model.addAttribute("agentList",agentList);
			
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			return MappingConstant.ADMIN_ASSIGN_AGENTS;
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());			
			model.addAttribute("agentDetailsfromDb",new UserDetailsBean());
			return MappingConstant.ADMIN_ASSIGN_AGENTS;
		
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
						
			return MappingConstant.ADMIN_ASSIGN_AGENTS;
		}
	}
}

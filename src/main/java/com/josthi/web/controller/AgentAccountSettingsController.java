package com.josthi.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@Controller
public class AgentAccountSettingsController {

	
	private static final Logger logger = LoggerFactory.getLogger(AgentAccountSettingsController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired 
	EmailDbBean emailDbBean;

		@GetMapping("/agentAdmin/settings")
		public String agentAdminSettings(Model model,
				@RequestParam (name="status", required = false, defaultValue = "") String status,
			    @RequestParam (name="message", required = false, defaultValue = "") String message,
				HttpServletRequest request) {
			
			try {
				ValidateSession.isValidSession(request);
				PasswordResetBean passwordResetBean = new PasswordResetBean();
				
				if(status!=null && status.length() > 0) {
			    	 model.addAttribute("status", status);
					 model.addAttribute("message", message);
		   	 	}
				
				model.addAttribute("passwordResetBean", passwordResetBean);
				
				return MappingConstant.ADMIN_ACCOUNT_SETTINGS;
				
			}catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", ex.getMessage());			
				model.addAttribute("passwordResetBean",new PasswordResetBean());
				return MappingConstant.ADMIN_ACCOUNT_SETTINGS;
			
	    	}catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				status = MessageConstant.USER_FAILURE_STATUS;
				message =  ex.getMessage();
				return "redirect:/login?status="+status+"&message="+message;
			
	    	}catch(Exception ex) {
	    		logger.error(ex.getMessage(), ex);
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", ex.getMessage());
				model.addAttribute("passwordResetBean",new PasswordResetBean());
							
				return MappingConstant.ADMIN_ACCOUNT_SETTINGS;
			}
			
		}
	
		//==============================================================================
		//======================== PASSWORD RESET ======================================
		//==============================================================================
		@RequestMapping(path ="/agentAdmin/changePassword/{custId}", method = RequestMethod.POST)
		public String changePassword(Model model, PasswordResetBean passwordResetBean,
										@PathVariable String custId,
										HttpServletRequest request) {
			logger.info("*****************passwordResetBean :"+passwordResetBean.toString());
			
			String actionStatus = "";
			String message = "";
			try {			
				ValidateSession.isValidSession(request);
				ValidateSession.isValidUser(request, custId);
				
				passwordResetBean.setUserID(custId);
				
				if(passwordResetBean.getOldPassword().trim().length() == 0) {
					throw new UserExceptionInvalidData("The existing password field can't be blank");
				}else if(StringUtils.isEmpty(passwordResetBean.getNewPassword().trim()) ||
						 StringUtils.isEmpty(passwordResetBean.getNewConfirmPassword().trim())) {
					throw new UserExceptionInvalidData("new Password Fields can't be blank");
				}else if(!(passwordResetBean.getNewPassword().trim().equals(passwordResetBean.getNewConfirmPassword().trim()))) {
					throw new UserExceptionInvalidData("The New Password and Confirm Password values are not matching. Please try with same value.");
				}else if(!(userAuthService.isValidPassword(passwordResetBean.getEmailId(),
														Security.encrypt(passwordResetBean.getOldPassword().trim())))){
					throw new UserExceptionInvalidData("The existing password is not matching with the supplied one. Please re-enter the correct password");
				}else if(!Utils.PasswordValidation(passwordResetBean.getNewConfirmPassword().trim())) {
					throw new UserExceptionInvalidData("Password should be alteast 8 byte, Should have atleast 1 Letter and 1 Number, Should have alteast 1 special char out of ! @ # $ % * + - ");
					
				}else{
					
					boolean status = userAuthService.updatePassword(passwordResetBean);
					actionStatus = MessageConstant.USER_SUCCESS_STATUS;
					message = "Password Changed Successfully.";
					return "redirect:/agentAdmin/settings?status="+actionStatus+"&message="+message;
				}
				
				
			}catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				actionStatus = MessageConstant.USER_FAILURE_STATUS;
				message = ex.getMessage();
				return "redirect:/agentAdmin/settings?status="+actionStatus+"&message="+message;
			}catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				actionStatus = MessageConstant.USER_FAILURE_STATUS;
				message =  ex.getMessage();
				return "redirect:/login?status="+actionStatus+"&message="+message;
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				actionStatus = MessageConstant.USER_FAILURE_STATUS;
				message = "Exception Occured while updating the New Password, Please try later or contact the Customer Service.";
				return "redirect:/agentAdmin/settings?status="+actionStatus+"&message="+message;
			}
			
			
		}
}

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

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserAccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired 
	EmailDbBean emailDbBean;
	
	
	@GetMapping("/user/loginAndsecurity")
	public String userLoginAndsecurity(Model model,
										@RequestParam (name="status", required = false, defaultValue = "") String status,
										@RequestParam (name="message", required = false, defaultValue = "") String message
										) {
		
		PasswordResetBean passwordResetBean = new PasswordResetBean();
		
		if(status!=null && status.length() > 0) {
	    	 model.addAttribute("status", status);
			 model.addAttribute("message", message);
   	 	}
		
		model.addAttribute("passwordResetBean", passwordResetBean);
		return "user/login_and_security_user";
	}
	
	
	@RequestMapping(path ="/user/changePassword/{custId}", method = RequestMethod.POST)
	public String savebeneficiaryDetails(Model model, PasswordResetBean passwordResetBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		logger.info("*****************passwordResetBean :"+passwordResetBean.toString());
		
		String actionStatus = "";
		String message = "";
		try {			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, passwordResetBean.getUserID());
			
			if(passwordResetBean.getOldPassword().trim().length() == 0) {
				throw new UserExceptionInvalidData("The existing password field can't be blank");
			}else if(StringUtils.isEmpty(passwordResetBean.getNewPassword().trim()) ||
					 StringUtils.isEmpty(passwordResetBean.getNewConfirmPassword().trim())) {
				throw new UserExceptionInvalidData("new Password Fields can't be blank");
			}else if(!(passwordResetBean.getNewPassword().trim().equals(passwordResetBean.getNewConfirmPassword().trim()))) {
				throw new UserExceptionInvalidData("The New Password & Confirm Password values are not matching. Please try with save value.");
			}else if(!(userAuthService.isValidPassword(passwordResetBean.getEmailId(),
													Security.encrypt(passwordResetBean.getOldPassword().trim())))){
				throw new UserExceptionInvalidData("The existing password is not matching with the supplied one. Please re-enter the correct password");
			}else {
				
				boolean status = userAuthService.updatePassword(passwordResetBean);
				actionStatus = MessageConstant.USER_SUCCESS_STATUS;
				message = "Password Changed Successfully.";
				return "redirect:/user/loginAndsecurity?status="+actionStatus+"&message="+message;
			}
			
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/loginAndsecurity?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/loginAndsecurity?status="+actionStatus+"&message="+message;
		}
		
		
	}
	
	
	
	//==============================================================================
	//======================== PASSWORD RESET ======================================
	//==============================================================================

}

package com.josthi.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserRegistrationService;
import com.josthi.web.utils.OTPGen;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;


@Controller
public class UserRegistrationController {

	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	EmailService emailService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
	
	    //user_personal_details
		@GetMapping("/userSignup")
		public String userSignup(UserRegistrationBean userRegistrationBean) {
			return "User_Registration";
		}
		
		
		
		@RequestMapping(path ="/registerGeneralUser", method = RequestMethod.POST)
		public String registerGeneralUser(UserAuthBo userAuthBo, UserRegistrationBean userRegistrationBean,
											HttpSession session, 
											Model model) throws Exception {
			
			
			//Basic Validation
			if(!(userRegistrationBean.getWordApp().trim().equals(userRegistrationBean.getConfirmWordApp().trim()))) {
				model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED_UNMATCH_PASSWORD);
				return MappingConstant.USER_REGISTRATION;
			}
			
			//All the field should have the values.
			if(StringUtils.isEmpty(userRegistrationBean.getFirstName()) || 
					StringUtils.isEmpty(userRegistrationBean.getLastName())	||
					StringUtils.isEmpty(userRegistrationBean.getValidEmailId()) ||
					StringUtils.isEmpty(userRegistrationBean.getWordApp()) ||
					StringUtils.isEmpty(userRegistrationBean.getConfirmWordApp())
					){
				model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED_REQUIRED_FIELDS);
				return MappingConstant.USER_REGISTRATION;
			}
			
			
			
			//Not an existing User.
			if(!(isValidUserIDOnly(userRegistrationBean.getValidEmailId().trim()))) {
				int getNextID = userAuthService.getNextID();
				String customerID = Utils.getNextCustomerID(Constant.USER_TYPE_REG_USER,getNextID);
				System.out.println(customerID);
				
				userRegistrationBean.setCustomerID(customerID);
				
				//Here we are encrypting the Password and storing in the Database also the OTP.
				String otp = OTPGen.generateRandomNumber(6);
				userRegistrationBean.setOtp(otp);
				
				boolean status = userRegistrationService.registerNewUser(userRegistrationBean, getNextID);
				if(status) {
					System.out.println("---- SUCCESS ------");
					//email section
					//Customer ID
					// F & L Name.
					Map<String, String> map = new HashMap<String, String>();
			        map.put("name", userRegistrationBean.getFirstName()+" "+userRegistrationBean.getLastName());
			        map.put("custID", customerID);			       
			        
			        //Welcome Email
			        EmailDbBean emailDbBean = Utils.getEmailBeanForWelcome(userRegistrationBean.getValidEmailId().trim(), Utils.mapToString(map));
			        
			        //OTP email 
			        Map<String, String> otpMap = new HashMap<String, String>();
			        otpMap.put("name", userRegistrationBean.getFirstName()+" "+userRegistrationBean.getLastName());
			        otpMap.put("otp",otp);
			        EmailDbBean emailDbBeanForOtp = Utils.getEmailBeanForOTP(userRegistrationBean.getValidEmailId().trim(), Utils.mapToString(otpMap));
			        
			        System.out.println(emailDbBean);
					boolean emailQueueStatus = emailService.queueEmail(emailDbBean);
					boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForOtp);
					
					if(emailQueueStatus && otpQueueStatus) {				
						model.addAttribute("successMessage", MessageConstant.NEW_REGISTRATION_SUCCESSFUL);
						EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
						return MappingConstant.LOGIN_PAGE;
					}else{
						model.addAttribute("successMessage", MessageConstant.NEW_REGISTRATION_SUCCESSFUL_CONDITIONAL);
						return MappingConstant.LOGIN_PAGE;
					}
				
				}else {
					model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED);
					return MappingConstant.USER_REGISTRATION;
				}
			
				
			}else {
				model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_DUPLICATE_USER_ID);
				return MappingConstant.USER_REGISTRATION;
			}
			
			
		}
		
		
		
		
		
		
		private boolean isValidUserIDOnly(String emailID) {
			
			 boolean result = false; 
			 int count = userAuthService.isValidUserID(emailID);
			 if (count > 0) { result = true; }
			 return result;
		}
	
}

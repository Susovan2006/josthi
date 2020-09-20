package com.josthi.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserRegistrationService;
import com.josthi.web.utils.Utils;


@Controller
public class UserRegistrationController {

	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
	
	    //user_personal_details
		@GetMapping("/userSignup")
		public String userSignup(UserRegistrationBean userRegistrationBean) {
			return "josthi_signup";
		}
		
		
		
		@RequestMapping(path ="/registerGeneralUser", method = RequestMethod.POST)
		public String registerGeneralUser(UserRegistrationBean userRegistrationBean,
											HttpSession session, 
											Model model) throws Exception {
			
			//System.out.println(userRegistrationBean.toString()+"--"+(isValidUserIDOnly(userRegistrationBean.getValidEmailId().trim())));
			//Not an existing User.
			if(!(isValidUserIDOnly(userRegistrationBean.getValidEmailId().trim()))) {
				int getNextID = userAuthService.getNextID();
				String customerID = Utils.getNextCustomerID("RegUser",getNextID);
				System.out.println(customerID);
				
				userRegistrationBean.setCustomerID(customerID);
				boolean status = userRegistrationService.registerNewUser(userRegistrationBean, getNextID);
				if(status) {
					System.out.println("---- SUCCESS ------");
				}
			
				
			}else {
				model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_DUPLICATE_USER_ID);
			}
			
			return "josthi_signup";
		}
		
		
		
		
		
		
		private boolean isValidUserIDOnly(String emailID) {
			
			 boolean result = false; 
			 int count = userAuthService.isValidUserID(emailID);
			 if (count > 0) { result = true; }
			 return result;
		}
	
}

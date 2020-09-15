package com.josthi.web.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.springconfig.SpringConfig;

@Controller
public class UserAuthController {

	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	/**
	 * This is the basic method where the user will put the
	 * User ID and Password to login. here it will open only the 
	 * login UI.
	 * @param model
	 * @return
	 */
	@GetMapping("/login")
	public String login(UserAuthBo userAuthBo, Model model) {
		logger.info("Login Service Called.");
		//model.addAttribute("errorMessage", "Hello");
		return "login_simple";
	}
	
	
	
	/**
	 * Here we are validation the User ID and the Password, Based on the Role, it will navigate to 
	 * different homePage.
	 * @param model
	 * @return
	 */
	@RequestMapping(path ="/validateAuthenticity", method = RequestMethod.POST)
	public String validateAuthenticity(UserAuthBo userAuthBo, HttpSession session, Model model) {
		try {
			logger.info("validateAuthenticity Service Called.");
			UserAuthBo userDetails = userAuthService.getValidUser(userAuthBo.getUseridEmail().trim(), 
										 						  userAuthBo.getWordapp().trim());
			logger.info(userDetails.toString());
			//A Valid user should have the CustomerID + Role + Status
			if((userDetails.getCustomerId()!=null && userDetails.getCustomerId().length() > 0) &&
					(userDetails.getRole()!=null && userDetails.getRole().length() > 0) &&
					(userDetails.getStatus()!=null && userDetails.getStatus().length() > 0 && userDetails.getStatus().equalsIgnoreCase(Constant.USER_STATUS_ACTIVE)) &&
					(userDetails.getTemporaryLockEnabled()!=null && userDetails.getTemporaryLockEnabled().equalsIgnoreCase("NO"))) {
				
				userDetails.setLoginStatus("ONLINE");
				userDetails.setLoginTime(new Timestamp(System.currentTimeMillis()));
				userDetails.setLoginRetryCount(0);
				userDetails.setTemporaryLockEnabled("NO");
				
				if(userAuthService.updateLoginStatusOnSuccess(userDetails)) {
					//Set Environment Variable.
					logger.info("LOGIN Successful");
					return "user_personal_details";

				}else {
					//Message, error occurred, please try again.
					logger.info("LOGIN Valid, but Update error Occured");
					model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_ON_DATABASE_UPDATE_FAILURE);
					return "login_simple";
				}
				

			//Error Condition.
			//In case The Login is Valid, but the Account is deactivated.	
			}else if(userDetails.getStatus() !=null && userDetails.getStatus().length() > 0 && 
					 !(userDetails.getStatus().equalsIgnoreCase(Constant.USER_STATUS_ACTIVE))) {
				     logger.info("Account is deactivated, Please contact the Customer Service Executive.");
				     model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_ON_ACCOUNT_DEACTIVATED);
				     return "login_simple";
		    //In case the account is temporarily Locked.
			}else if(userDetails.getTemporaryLockEnabled()!=null && !(userDetails.getTemporaryLockEnabled().contentEquals("NO"))) {
				 //TODO : Send Error Message.
				logger.info("Looks like the the account is temporarily deactivated due to multiple incorrect retries. Try after 1 Hours.");
				model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_ACCOUNT_TEMP_LOCKED);
			     return "login_simple";
			     
			//If the Role and Cust ID is blank, that means invalid UserID & Password.     
			}else if((userDetails.getCustomerId()!=null && userDetails.getCustomerId().length() == 0) ||
					(userDetails.getRole()!=null && userDetails.getRole().length() == 0)) {
				logger.info("Looks like the user ID and the password is incorrect, retry with valid user ID and password. or click Forget password.");
				model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_INCORECT_USER_ID_PASSWORD);
				return "login_simple";
				
			//In case the userID is correct, but the password is incorrect.
			}else if(isValidUserIDOnly(userAuthBo.getUseridEmail().trim())) {
				UserAuthBo userDetailsOnUid = userAuthService.getValidUser(userAuthBo.getUseridEmail().trim());
				
				int retryCount = userDetailsOnUid.getLoginRetryCount();
				
				    //re-try is allowed till 3 times, then the account will be temporarily disabled.
					if(retryCount < 4) {
						userDetailsOnUid.setLoginRetryCount(retryCount+1);
						boolean updateStatus = userAuthService.updateLoginStatus(userDetailsOnUid);
						if(updateStatus) {
							logger.info("Looks like you entered an invalid password, please try again."+
							"you will get to try 3 attempts, then your ID will be locked. This is your attemp no : "  + userDetailsOnUid.getLoginRetryCount());
							
							model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_INCORRECT_PASSWORD_WITH_ATEMPT+userDetailsOnUid.getLoginRetryCount());
						}else {
							//Try again, Invalid Password. You can try max 3 times.
							model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_INVALID_PASSWORD);
						}

				    //If the retry count exceed 3+ times, the account will be disabled. and will have to wait for 1 hour.
					}else {
						userDetailsOnUid.setTemporaryLockEnabled("YES");
						if(userAuthService.updateLoginStatus(userDetailsOnUid)) {
							logger.info("Max attempt exceed, the account is temporary locked, Please wait for an hour to relogin.");
							model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_MAX_TRY_EXCEEDED);
						}	
					}
					return "login_simple";
			}else {
				logger.info("Invalid User ID and Password, please try again, you will get max 3 attempts, then the password will be locked.");
				model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_GENERAL);
				return "login_simple";
			}
			
		}catch(Exception ex) {
			return "login_simple";
		}
		
	}



	private boolean isValidUserIDOnly(String emailID) {
		
		 boolean result = false; 
		 int count = userAuthService.isValidUserID(emailID);
		 if (count > 0) { result = true; }
		 return result;
	}
}


/* TODO :1 Need to implement the Max Retry Count.
 * if Max retry count is 3, the account will be locked.
 * There should be some automated Task to Unlock the Account.
 * 
 * LOGIN_RETRY_COUNT / TEMPORARY_LOCK_ENABLED
 * 
 * 
 * 
 * 
 */

package com.josthi.web.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.EmailConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.mail.EmailController;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
//import com.josthi.web.mail.SendMailApplication;
//import com.josthi.web.mail.SendEmailTrigger;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.springconfig.SpringConfig;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

@Controller
public class UserAuthController {

	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired 
	EmailDbBean emailDbBean;

	
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
			
			if(userAuthBo.getUseridEmail().trim().length()==0 || userAuthBo.getWordapp().trim().length()==0) {
				model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_VALIDATION);
				return "login_simple";
			}
			
			UserAuthBo userDetails = userAuthService.getValidUser(userAuthBo.getUseridEmail().trim(), 
										 						  Security.encrypt(userAuthBo.getWordapp().trim()));
			logger.info("USER DETAILS = "+userDetails.toString());
			
			//A Valid user should have the CustomerID + Role + Status
			if((userDetails.getCustomerId()!=null && userDetails.getCustomerId().length() > 0) &&
					(userDetails.getRole()!=null && userDetails.getRole().length() > 0) &&
					
					(userDetails.getStatus()!=null && userDetails.getStatus().length() > 0 && 
					 	!(userDetails.getStatus().equalsIgnoreCase(Constant.USER_STATUS_DISABLED))	&&
					(userDetails.getTemporaryLockEnabled()!=null && 
						userDetails.getTemporaryLockEnabled().equalsIgnoreCase("NO")))) {
				
					userDetails.setLoginStatus(Constant.USER_ONLINE_STATUS);
					userDetails.setLoginTime(new Timestamp(System.currentTimeMillis()));
					userDetails.setLoginRetryCount(0);
					userDetails.setTemporaryLockEnabled(Constant.USER_TEMPORARY_LOCK_NO);
				
					if(userAuthService.updateLoginStatusOnSuccess(userDetails)) {
						//TODO: Set Session Variable.
						String userFirstAndLastName = userAuthService.getUserDetails(userDetails.getCustomerId());
						
						
						UserSessionBean userSessionBean = new UserSessionBean (userFirstAndLastName,              	//First Name & Last Name 
								   userDetails.getRole(),			  	// Role
								   userAuthBo.getUseridEmail().trim(), 	// email ID
								   "",									//Image Path
								   Constant.USER_ONLINE_STATUS,			// ONLINE/OFFLINE
								   userDetails.getVerifiedUser(),		// VERIFIED USER. (N by default)
								   true,								// Session Active = true.
								   userDetails.getCustomerId());		// Customer ID						
						
						session.setAttribute(Constant.USER_SESSION_OBJ_KEY, userSessionBean);
						session.setAttribute(Constant.USER_SESSION_PROFILE_PICTURE_KEY, Constant.DEFAULT_PROFILE_PICTURE);
						
						
						if(userDetails.getRole().equalsIgnoreCase(Constant.USER_TYPE_REG_USER)) {		
							logger.info("User LOGIN Successful");
							return "user/home_user";
						}else if(userDetails.getRole().equalsIgnoreCase(Constant.USER_TYPE_AGENT)) {
							logger.info("Agent LOGIN Successful");
							return "agentAdmin/index";
						}else {
							return "login_simple";
						}
					}else {
						//Message, error occurred, please try again.
						logger.info("LOGIN Valid, but Update error Occured");
						model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_ON_DATABASE_UPDATE_FAILURE);
						return "login_simple";
					}
				

			//Error Condition.
			//In case The Login is Valid, but the Account is deactivated.	
			}else if(userDetails.getStatus() !=null && userDetails.getStatus().length() > 0 && 
					 (userDetails.getStatus().equalsIgnoreCase(Constant.USER_STATUS_DISABLED))) {
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
					if(retryCount < Constant.LOGIN_RETRY_COUNT) {
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
							
							//Send Email, Your ID locked. Click here to Unlock.
							String userFirstAndLastName = userAuthService.getUserDetails(userDetailsOnUid.getCustomerId());
							Map<String, String> map = new HashMap<String, String>();
					        map.put("name", userFirstAndLastName);
					        map.put("message", EmailConstant.ACCOUNT_UNLOCK_MESSAGE);
					        map.put("unlockLink", Utils.generateAccountUnlockUrl(userAuthBo.getUseridEmail().trim()));
					        
					        EmailDbBean emailDbBean = Utils.getEmailBeanForAccountLock(userAuthBo.getUseridEmail().trim(), Utils.mapToString(map));
					        if(emailService.queueEmail(emailDbBean)) {
					        	model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_MAX_TRY_EXCEEDED);
					        }else {
					        	model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_MAX_TRY_EXCEEDED_NO_EMAIL);
					        }
							
							
						}	
					}
					return "login_simple";
			}else {
				logger.info("Invalid User ID and Password, please try again, you will get max 3 attempts, then the password will be locked.");
				model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_GENERAL);
				return "login_simple";
			}
			
		}catch(UserExceptionInvalidData ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			return "login_simple";
		}catch(Exception ex) {
			model.addAttribute("errorMessage", MessageConstant.LOGIN_ERROR_GENERAL);
			return "login_simple";
		}
		
	}

	/**
	 * Account Unlock
	 * @param emailID
	 * @return
	 */

	
	@GetMapping("/unlock/{emailID}")
	public String hello0(@PathVariable("emailID") String encryptedEmailID, UserAuthBo userAuthBo, Model model)
	{
		try {
			String decryptedEmailID = Security.decrypt(encryptedEmailID);
			UserAuthBo userDetailsOnUid = userAuthService.getValidUser(decryptedEmailID);
			userDetailsOnUid.setTemporaryLockEnabled("NO");
			userDetailsOnUid.setLoginRetryCount(0);
			if(userAuthService.updateLoginStatus(userDetailsOnUid)) {
				model.addAttribute("errorMessage", MessageConstant.ACCOUNT_UNLOCK_SUCCESS);
				EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
			}else {
				model.addAttribute("errorMessage", MessageConstant.ACCOUNT_UNLOCK_FAILED);
			}
		}catch( Exception ex) {
			model.addAttribute("errorMessage", MessageConstant.ACCOUNT_UNLOCK_FAILED);
		}
		
	    return "login_simple";
	}
	

	private boolean isValidUserIDOnly(String emailID) {
		
		 boolean result = false; 
		 int count = userAuthService.isValidUserID(emailID);
		 if (count > 0) { result = true; }
		 return result;
	}



//=============================== Forget Password ===========================
//===========================================================================
	@GetMapping("/accountRecovery")
	public String accountRecovery(Model model) {
		//model.addAttribute("message","HelloWorld");
		return "account_recovery";
	}


	@GetMapping("/accountRecoveryController")
	public String accountRecovery(@RequestParam(value = "signinSrEmail", required = true) String signinSrEmail,
												Model model) throws Exception {
		System.out.println("Email:"+signinSrEmail);
		if(isValidUserIDOnly(signinSrEmail.trim())) {
			
			UserAuthBo userDetailsOnUid = userAuthService.getValidUser(signinSrEmail.trim());
			
			String userFirstAndLastName = userAuthService.getUserDetails(userDetailsOnUid.getCustomerId());
			
			Map<String, String> map = new HashMap<String, String>();
	        map.put("name", userFirstAndLastName);
	        map.put("password", Security.decrypt(userDetailsOnUid.getWordapp()));
	        
	        EmailDbBean emailDbBean = Utils.getEmailBeanForPasswordRecovery(signinSrEmail.trim(), Utils.mapToString(map));
	        System.out.println(emailDbBean);
			boolean status = emailService.queueEmail(emailDbBean);
			if(status) {
				model.addAttribute("errorMessage", MessageConstant.ACCOUNT_RECOVERY_SUCCESS);
				EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
			}else{
				model.addAttribute("errorMessage", MessageConstant.ACCOUNT_RECOVERY_ERROR);
			}
			
		}else {
			model.addAttribute("errorMessage", MessageConstant.ACCOUNT_RECOVERY_ERROR);
		}
		return "account_recovery";
	}
	
	
//=========================== User Login Module ===============================
//=============================================================================
	
	

	
	
	
	
	
	
	
	
	
	
} //END OF Main method.

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

package com.josthi.web.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.OTPGen;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@RestController
public class ReSendOtpEmail {
	
	private static final Logger logger = LoggerFactory.getLogger(ReSendOtpEmail.class);
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	
	//@CrossOrigin("*") //TODO : need to remove before prod deployment.
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/resendOTP", method = RequestMethod.POST)
    public ResponseEntity<String> resendOTP(
    		@RequestParam("customerID") String customerID,
    		@RequestParam("emailId") String emailId,
    		HttpServletRequest request) {
    		
    	logger.info("OTP request for :"+customerID +"Email : "+emailId);
        try { 
        	ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerID);
			if(StringUtils.isEmpty(emailId.trim())) {
				emailId = ValidateSession.getUserEmail(request);
			}
        	        	
        	if(isValidUserIDOnly(emailId.trim())) {
    			
    			UserAuthBo userDetailsOnUid = userAuthService.getValidUser(emailId.trim());
    			
    			String userFirstAndLastName = userAuthService.getUserDetails(userDetailsOnUid.getCustomerId());
    			
    			if(!(userAuthService.isOtpFrequencyMatching(customerID))) {
    				throw new UserExceptionInvalidData("Info : OTP already sent few moments back, please wait for atleast 2 min.");
    			}
    			
    			String otp = OTPGen.generateRandomNumber(4);
    			
    			//Database Update
    			userAuthService.updateOtp(customerID,emailId, otp);
    			
            	//OTP email 
    	        Map<String, String> otpMap = new HashMap<String, String>();
    	        otpMap.put("name", userFirstAndLastName);
    	        otpMap.put("otp",otp);
    	        EmailDbBean emailDbBeanForOtp = Utils.getEmailBeanForOTP(emailId, Utils.mapToString(otpMap));
    	        boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForOtp);
    			
    			if(otpQueueStatus) {
    				EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
    	        	String status = "Success : OTP mailed to "+emailId+ ". Please check your email and enter the OTP here. You should get the email in 1-2 min.";        	    	 
    	        	return new ResponseEntity<String>(status , HttpStatus.OK);
    			}else{
    				throw new UserExceptionInvalidData("Error : Error Occurred while triggering the email, please re-login and try");
    			}
    			
    		}else {
    			throw new UserExceptionInvalidData("Error : Looks like the email ID is invalid, Please re-login and try again.");
    		}
        	        	
        }catch(UserExceptionInvalidData ex) {
        	logger.error(ex.getMessage(), ex);
        	//return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        	return new ResponseEntity<String>(ex.getMessage() , HttpStatus.OK);
        }catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        	return new ResponseEntity<String>("Exception Occurred while sending OTP, Try again later",HttpStatus.BAD_REQUEST);
        }
    
    }
    
    
    
    private boolean isValidUserIDOnly(String emailID) {
		
		 boolean result = false; 
		 int count = userAuthService.isValidUserID(emailID);
		 if (count > 0) { result = true; }
		 return result;
	}

}

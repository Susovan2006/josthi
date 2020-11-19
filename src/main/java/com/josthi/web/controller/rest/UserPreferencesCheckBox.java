package com.josthi.web.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.OTPGen;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@RestController
public class UserPreferencesCheckBox {
	
	private static final Logger logger = LoggerFactory.getLogger(UserPreferencesCheckBox.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveUserPreferences", method = RequestMethod.POST)
    public ResponseEntity<String> saveUserPreferences(
    		@RequestParam("customerID") String customerID,
    		@RequestParam("emailId") String emailId,
    		@RequestParam("alertType") String alertType,
    		@RequestParam("value") String value,
    		HttpServletRequest request) {
    		
    	logger.info("User Pref request for :"+customerID +"Email : "+emailId + " for "+alertType);
        try { 
        	ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerID);
        	        	
        	if(isValidUserIDOnly(emailId.trim())) {
        		
        		
        		if(userAuthService.saveUserPref(customerID, alertType, value)) {
    	        	String status = "Success : Saved";        	    	 
    	        	return new ResponseEntity<String>(status , HttpStatus.OK);
    			}else{
    				throw new UserExceptionInvalidData("Error : Error Occured while updateing the database, please re-login and try");
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
        	return new ResponseEntity<String>("Exception Occured while saving the preferences",HttpStatus.BAD_REQUEST);
        }
    
    }
	
	
	private boolean isValidUserIDOnly(String emailID) {
		
		 boolean result = false; 
		 int count = userAuthService.isValidUserID(emailID);
		 if (count > 0) { result = true; }
		 return result;
	}

}

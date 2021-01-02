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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@RestController
public class UserGeneralQueryController {

	private static final Logger logger = LoggerFactory.getLogger(UserGeneralQueryController.class);
	///addUserQuery
	
	@Autowired
	EmailService emailService;
	
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addUserQuery", method = RequestMethod.POST)
    public ResponseEntity<String> addUserQuery(
    		@RequestParam("name") String name,
    		@RequestParam("email") String email,
    		@RequestParam("userNotes") String userNotes,
    		HttpServletRequest request) {
    		
    	logger.info("---> Add Notes request for :"+name +"email : "+email+"  Notes:"+userNotes);
        try { 

        		if(StringUtils.isEmpty(name.trim()) || StringUtils.isEmpty(email.trim()) || StringUtils.isEmpty(userNotes.trim())) {
        			throw new UserExceptionInvalidData("All the fields are required. Please fill all the details and try again");
        		}
        		
        		if(!Utils.isValidEmailAddress(email.trim())) {
        			throw new UserExceptionInvalidData("Invalid Email address. Please re-validate the details.");
        		}
        		boolean updateStatus = false;
    			updateStatus = cacheConfigService.addUserQuery(name,
    														   email,
    														   userNotes);
    			//Database Update
    			logger.info("Database Updated Successfully.");
    			
    			
                	//Service email 
        	        Map<String, String> serviceEmailMap = new HashMap<String, String>();
        	        serviceEmailMap.put("name", name);
        	        serviceEmailMap.put("email", email);
        	        serviceEmailMap.put("userNotes", userNotes);       	        

        	        String emailId = "susovan2006@gmail.com";
        	        EmailDbBean emailDbBeanForService = Utils.getEmailBeanForCustomerEnquery(emailId, Utils.mapToString(serviceEmailMap));
        	        boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForService);
        	        if(otpQueueStatus) {
        	        	EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
        	        }

    			
    			if(updateStatus) {
    	        	String enqueryStatus = "Success : Customer Query registered, we will reply Back soon.";        	    	 
    	        	return new ResponseEntity<String>(enqueryStatus , HttpStatus.OK);
    			}else{
    				throw new UserExceptionInvalidData("Error : Error Occurred while processing the enquire request, Try again later.");
    			}
    			
        	        	
        }catch(UserExceptionInvalidData ex) {
        	logger.error(ex.getMessage(), ex);
        	//return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        	return new ResponseEntity<String>(ex.getMessage() , HttpStatus.OK);
        }catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		}catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        	return new ResponseEntity<String>("Exception Occurred while processing the enquire request, Try again later.",HttpStatus.BAD_REQUEST);
        }
    
    }
}

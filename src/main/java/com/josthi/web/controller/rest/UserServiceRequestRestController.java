package com.josthi.web.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.OTPGen;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@RestController
public class UserServiceRequestRestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceRequestRestController.class);

	@Autowired
	EmailService emailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	ServiceRequestService serviceRequestService;
	
	
	//@CrossOrigin("*")
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/addNotesToTicket", method = RequestMethod.POST)
    public ResponseEntity<String> addNotesToTicket(
    		@RequestParam("customerId") String customerId,
    		@RequestParam("notes") String notes,
    		@RequestParam("urgency") String urgency,
    		@RequestParam("status") String status,
    		@RequestParam("ticketNo") String ticketNo,
    		@RequestParam("emailId") String emailId,
    		HttpServletRequest request) {
    		
    	logger.info("---> Add Notes request for :"+customerId +"Ticket No : "+ticketNo);
        try { 
        	ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerId);
        	        	
        	if(isValidUserIDOnly(emailId.trim())) {
        		boolean updateStatus = false;
    			updateStatus = serviceRequestService.updateUserNotes(customerId,
																    notes,
																    urgency,
																    status,
																    ticketNo);
    			//Database Update
    			logger.info("Database Updated Successfully.");
    			ServiceRequestBean serviceRequestBean = serviceRequestService.getServiceRequestDetailsOnTicketNumber(customerId,ticketNo);
    			if(serviceRequestBean!=null) {
                	//Service email 
        	        Map<String, String> serviceEmailMap = new HashMap<String, String>();
        	        serviceEmailMap.put("ticketNumber", serviceRequestBean.getTicketNo());
        	        serviceEmailMap.put("createDate", serviceRequestBean.getRequestedDate());
        	        serviceEmailMap.put("requestedFor", serviceRequestBean.getRequestedFor());       	        
        	        serviceEmailMap.put("requestedBy", serviceRequestBean.getRequestedBy());
        	        serviceEmailMap.put("customerId", serviceRequestBean.getRequesterId());
        	        serviceEmailMap.put("beneficiaryId", serviceRequestBean.getBeneficiaryId());
        	        serviceEmailMap.put("urgency", serviceRequestBean.getServiceUrgency());
        	        serviceEmailMap.put("status", serviceRequestBean.getServiceStatus());
        	        serviceEmailMap.put("serviceCharge", "0$");
        	        serviceEmailMap.put("paymentStatus","Covered by Plan");
        	        serviceEmailMap.put("serviceType",serviceRequestBean.getServiceType());
        	        serviceEmailMap.put("serviceDesc",serviceRequestBean.getServiceReqDescription());
        	        serviceEmailMap.put("serviceLastUpdateUser",serviceRequestBean.getLastUpdatedBy());
        	        serviceEmailMap.put("serviceLastUpdateComments",serviceRequestBean.getLastCommentsNotes());
        	        
        	        EmailDbBean emailDbBeanForService = Utils.getEmailBeanForServiceTicket(emailId, serviceRequestBean.getTicketNo(), Utils.mapToString(serviceEmailMap));
        	        boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForService);
    			}

    			
    			if(updateStatus) {
    				EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
    	        	String ticketStatus = "Success : Ticket updated successfully.";        	    	 
    	        	return new ResponseEntity<String>(ticketStatus , HttpStatus.OK);
    			}else{
    				throw new UserExceptionInvalidData("Error : Error Occured while processing the service request, , Try again later.");
    			}
    			
    		}else {
    			throw new UserExceptionInvalidData("Error : Looks like the email ID/User is invalid, Please re-login and try again.");
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
        	return new ResponseEntity<String>("Exception Occured while processing the service request, Try again later.",HttpStatus.BAD_REQUEST);
        }
    
    }
    
    
    
    private boolean isValidUserIDOnly(String emailID) {
		
		 boolean result = false; 
		 int count = userAuthService.isValidUserID(emailID);
		 if (count > 0) { result = true; }
		 return result;
	}
	
	
	
	
}

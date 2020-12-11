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

import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@RestController
public class AssignAgentByAdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AssignAgentByAdminController.class);
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAgentForBeneficiary", method = RequestMethod.POST)
    public ResponseEntity<String> addNotesToTicket(
    		@RequestParam("relationId") String relationId,
    		@RequestParam("newAgentId") String newAgentId,
    		@RequestParam("beneficiaryName") String beneficiaryName,
    		@RequestParam("beneficiaryId") String beneficiaryId,
    		@RequestParam("hostUserId") String hostUserId,
    		@RequestParam("adminId") String adminId,
    		@RequestParam("adminEmailId") String adminEmailId,   //bad, better not to use the email ID.
    		HttpServletRequest request) {
    		
    	logger.info("---> Agent Change request for :"+beneficiaryName +"relationId  : "+relationId);
        try { 
        	ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, adminId);
        	        	
        	if(isValidUserIDOnly(adminEmailId.trim())) {
        		boolean updateStatus = false;
    			updateStatus = userDetailService.updateAgentForBeneficiary(relationId,
    																newAgentId,
    																beneficiaryName,
    																hostUserId,
    																adminId);
    			//Database Update
    			logger.info("Database Updated Successfully.");
    			
    			//EMAIL SECTION
    			AgentAssignmentBean agentAssignmentBean = userDetailService.getBeneficiaryAgentDetail(relationId);
    			String email = userAuthService.getEmailId(hostUserId);
    			if(agentAssignmentBean!=null && email !=null) {
                	//Service email 
        	        Map<String, String> agentAssignmentEmailMap = new HashMap<String, String>();
        	        agentAssignmentEmailMap.put("hostUserName", Utils.toCamelCase(agentAssignmentBean.getHostUserName()));
        	        agentAssignmentEmailMap.put("hostUserId", agentAssignmentBean.getHostUserId());
        	        agentAssignmentEmailMap.put("hostUserEmail", agentAssignmentBean.getHostUserEmail());       	              	        
        	        agentAssignmentEmailMap.put("lastUpdateDate", agentAssignmentBean.getLastUpdateDate());
        	        
        	        String beneficiaryNameForEmail = Utils.toCamelCase(agentAssignmentBean.getBeneficiaryName());
        	        String agentName = Utils.toCamelCase(agentAssignmentBean.getAgentName());
        	        String agentId = agentAssignmentBean.getAgentId();
        	        
        	        String emailBody = "Hope you are doing fine. This email is just to let you know, your Benefeciary, "
        	        					+ beneficiaryNameForEmail + " is recently assigned to Agent "+ agentName + "("
        	        					+ agentId +") . From now on  " + agentName +  " will be your point of Contact "
        	        					+ " and will handle your Service and Request. For further details, please contact the Customer Service.";
        	        
        	        agentAssignmentEmailMap.put("message", emailBody);
        	        
        	        EmailDbBean emailDbBeanForService = Utils.getEmailBeanForAgentAssignment(email, 
        	        																		Utils.mapToString(agentAssignmentEmailMap));
        	        boolean otpQueueStatus = emailService.queueEmail(emailDbBeanForService);
    			}

    			
    			if(updateStatus) {
    				EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
    	        	String agentAssignmentStatus = "Success : Agent Assigned successfully."; 
    	        	logger.info("");
    	        	return new ResponseEntity<String>(agentAssignmentStatus , HttpStatus.OK);
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

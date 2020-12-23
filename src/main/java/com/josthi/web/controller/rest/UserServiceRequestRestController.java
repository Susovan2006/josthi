package com.josthi.web.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.bo.AjaxResponsePrice;
import com.josthi.web.bo.AjaxRestResponse;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.constants.Constant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.PlanAndBenefitService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.OTPGen;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

import com.google.gson.*;


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
	
	@Autowired
	PlanAndBenefitService planAndBenefitService;
	
	
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
        	        if(otpQueueStatus) {
        	        	EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
        	        }
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
    
    
    //############################################### DEC 14 , 20202 #################################################
    // Populate the dropdown for the service Type.
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/populateServiceTypeForBeneficiary1", method =
	 * RequestMethod.POST) public void populateServiceTypeForBeneficiary1(
	 * 
	 * @RequestParam("customerId") String customerId,
	 * 
	 * @RequestParam("beneficiaryId") String beneficiaryId,
	 * 
	 * @RequestParam("beneficiaryName") String beneficiaryName, HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * logger.info("---> Add Notes request for :"+beneficiaryName
	 * +"Ticket No : "+beneficiaryId); try {
	 * ValidateSession.isValidSession(request); ValidateSession.isValidUser(request,
	 * customerId);
	 * 
	 * JsonObject json_response=new JsonObject(); JsonArray data_json=new
	 * JsonArray();
	 * 
	 * JsonObject json=new JsonObject(); json.addProperty("value", "Susovan Value");
	 * json.addProperty("text", "Susovan Key");
	 * 
	 * JsonObject json1=new JsonObject(); json1.addProperty("value",
	 * "Gumtya Value"); json1.addProperty("text", "Gumtya Key");
	 * 
	 * data_json.add(json); data_json.add(json1);
	 * 
	 * json_response.add("aaData", data_json);
	 * 
	 * response.setContentType("application/Json");
	 * 
	 * response.getWriter().write(json_response.toString());
	 * 
	 * 
	 * 
	 * }catch(UserExceptionInvalidData ex) { logger.error(ex.getMessage(), ex);
	 * //return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	 * //return new ResponseEntity<String>(ex.getMessage() , HttpStatus.OK);
	 * }catch(UserException ex) { logger.error(ex.getMessage(), ex); //return new
	 * ResponseEntity<String>(ex.getMessage(), HttpStatus.OK); }catch(Exception ex)
	 * { logger.error(ex.getMessage(), ex); //return new ResponseEntity<String>
	 * ("Exception Occured while processing the service request, Try again later."
	 * ,HttpStatus.BAD_REQUEST); }
	 * 
	 * }
	 */
    
    
    
    @RequestMapping(value = "/populateServiceTypeForBeneficiary", 
				    		method = RequestMethod.POST,
				    		produces = MediaType.APPLICATION_JSON_VALUE, 
				    		consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxRestResponse populateServiceTypeForBeneficiary(@RequestBody String jsonString,
   		    		 												HttpServletRequest request) {	
    	logger.info("---> Ajax request for :"+jsonString);
    	try {

			  Gson gson = new Gson(); JsonObject jsonObject = gson.fromJson( jsonString, JsonObject.class);
			  
			    //Extracting the data from the JSON String.
			    String customerId = (jsonObject.get("customerId").toString()).replace("\"","");
			    String beneficiaryId = (jsonObject.get("beneficiaryId").toString()).replace("\"","");

			  	//Basic Session and User validation.
	        	ValidateSession.isValidSession(request);
				ValidateSession.isValidUser(request, customerId);
				
								
				//Fetching the Plan the Beneficiary is enrolled to. If the 
				// beneficiary is not enrolled to any Plan, the Plan Name will be default. 
				String userEnrolledPlan = planAndBenefitService.getEnrolledPlan(customerId, beneficiaryId);
				
				//Initializing the list that will have the dropdown values.
				List<DropDownBean> serviceTypeList = new ArrayList<DropDownBean>();
				List<DropDownBean> serviceList = new ArrayList<DropDownBean>();
				
				
				//In case the Plan is Default, it will show only the Ondemand ServiceTypes and the OnDemand Categories..
				if(userEnrolledPlan.equalsIgnoreCase(Constant.SERVICE_DEFAILT)) {
					serviceTypeList = planAndBenefitService.getServiceTypeListBasedOnPlan(Constant.SERVICE_DEFAILT); //Default.
					serviceList = planAndBenefitService.getServiceDetailList(Constant.SERVICE_DEFAILT);
					
				}else if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_BASIC)) {
					serviceTypeList = planAndBenefitService.getServiceTypeListBasedOnPlan(Constant.PLAN_BASIC); //Basic.
					serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_BASIC);
				
				}else if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_SILVER)) {
					serviceTypeList = planAndBenefitService.getServiceTypeListBasedOnPlan(Constant.PLAN_SILVER); //Silver.
					serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_SILVER);
				
				}else if(userEnrolledPlan.equalsIgnoreCase(Constant.PLAN_GOLD)) {
					serviceTypeList = planAndBenefitService.getServiceTypeListBasedOnPlan(Constant.PLAN_GOLD); //Gold.
					serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_GOLD);
				}
				


		 	AjaxRestResponse ajaxRestResponse = new AjaxRestResponse(Constant.AJAX_SUCCESS,"sucessfully fetched data.",null, serviceTypeList, serviceList);
		 	logger.info(ajaxRestResponse.toString());
		 	return ajaxRestResponse;
			
    	}catch(UserExceptionInvalidData ex) {
        	logger.error(ex.getMessage(), ex);
        	return new AjaxRestResponse(Constant.AJAX_EXCEPTION,ex.getMessage(),null, null, null);
        }catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return new AjaxRestResponse(Constant.AJAX_EXCEPTION,ex.getMessage(),null, null, null);			
		}catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        	return new AjaxRestResponse(Constant.AJAX_EXCEPTION,"Error Occured, please logout and try again.",null, null, null);
        }
		
    	
    }
	
	
    
    ///populateServiceListOnServiceType
    //-----------------------------------------------------------------------------------------------------------------------------------
    // Here we will populate the Service dropdown, based on the service Type.
    // serviceType can be ODBASIC/ODEMERGENCY/ODGENERAL/PLANBASIC/PLANSILVER/PLANGOLD
    //-----------------------------------------------------------------------------------------------------------------------------------
    
    @RequestMapping(value = "/populateServiceListOnServiceType", 
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE, 
    		consumes = MediaType.APPLICATION_JSON_VALUE)

		public @ResponseBody AjaxRestResponse populateServiceListOnServiceType(@RequestBody String jsonString,
				 															HttpServletRequest request) {	
		logger.info("---> Ajax request for :"+jsonString);
		try {
			
			Gson gson = new Gson(); JsonObject jsonObject = gson.fromJson( jsonString,
			JsonObject.class);
			
			
			String customerId = (jsonObject.get("customerId").toString()).replace("\"","");
			String serviceType = (jsonObject.get("serviceType").toString()).replace("\"","");
	
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerId);
			
			/*if(serviceType!=null) {
				serviceType = serviceType.toUpperCase();
			}else {
				throw new UserExceptionInvalidData("Invalid selection, Service Type can't be blank.");
			}*/
			
			if(serviceType == null || serviceType.isEmpty()) {
				throw new UserExceptionInvalidData("Invalid selection, Service Type can't be blank.");
			}
			
			List<DropDownBean> serviceList = new ArrayList<DropDownBean>();
			
			logger.info("serviceType :"+ serviceType);
			if(StringUtils.equals(serviceType, Constant.SERVICE_MATCH_BASIC)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.SERVICE_BASIC_SERVICE);
				
			}else if(StringUtils.equals(serviceType, Constant.SERVICE_MATCH_EMERGENCY)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.SERVICE_EMERGENCY_SERVICE);
				
			}else if(StringUtils.equals(serviceType, Constant.SERVICE_MATCH_GENERAL)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.SERVICE_GENERAL_SERVICE);
				
			}else if(StringUtils.equals(serviceType, Constant.PLAN_MATCH_BASIC)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_BASIC);
			
			}else if(StringUtils.equals(serviceType, Constant.PLAN_MATCH_SILVER)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_SILVER);
			
			}else if(StringUtils.equals(serviceType, Constant.PLAN_MATCH_GOLD)) {
				serviceList = planAndBenefitService.getServiceDetailList(Constant.PLAN_GOLD);
			}else {
				throw new UserExceptionInvalidData("Invalid selection, Looks like these is some issue in the selection, please try later or contact the service desk");
			}
			
			
			if(serviceList!=null && serviceList.size()>0) {
				AjaxRestResponse ajaxRestResponse = new AjaxRestResponse(Constant.AJAX_SUCCESS,"sucessfully fetched data.",null, serviceList);
				logger.info(ajaxRestResponse.toString());
				return ajaxRestResponse;
			}else {
				AjaxRestResponse ajaxRestResponse = new AjaxRestResponse(Constant.AJAX_INFO,"There is no service available for this Service Type. Select a different option.",null, serviceList);
				logger.info(ajaxRestResponse.toString());
				return ajaxRestResponse;
			}
			
			
			
			}catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponse(Constant.AJAX_EXCEPTION,ex.getMessage(),null, null, null);
			}catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponse(Constant.AJAX_EXCEPTION,ex.getMessage(),null, null, null);			
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxRestResponse(Constant.AJAX_EXCEPTION,"Error Occured, please logout and try again.",null, null, null);
			}
		
		
		}
    
    
    ///getServicePriceOnSelectedService
    //-----------------------------------------------------------------------------------------------------------------------------------
    // Here we will populate the Service dropdown, based on the service Type.
    // Service Type can be OnDemand Basic/Emergency/General
    // Or it can be Basic Plan/ Silver Plan / Gold Plan.
    //-----------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getServicePriceOnSelectedService", 
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE, 
    		consumes = MediaType.APPLICATION_JSON_VALUE)

		public @ResponseBody AjaxResponsePrice getServicePriceOnSelectedService(@RequestBody String jsonString,
				 															HttpServletRequest request) {	
		logger.info("---> Ajax request for :"+jsonString);
		try {
			
			Gson gson = new Gson(); JsonObject jsonObject = gson.fromJson( jsonString,
			JsonObject.class);
			
			
			String customerId = (jsonObject.get("customerId").toString()).replace("\"","");
			String serviceType = (jsonObject.get("serviceType").toString()).replace("\"","");
			String beneficiaryId = (jsonObject.get("beneficiaryId").toString()).replace("\"","");
			String serviceCategoryCode = (jsonObject.get("serviceCategoryId").toString()).replace("\"","");
			
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, customerId);
			
			//ServicaType Validation.
			if(serviceType!=null) {
				serviceType = serviceType.toUpperCase();
			}else {
				throw new UserExceptionInvalidData("Invalid selection, Service Type can't be blank.");
			}
			
			if(beneficiaryId == null || beneficiaryId.isEmpty()) {
				throw new UserExceptionInvalidData("Invalid Beneficiary selected. Please try again after some time.");
			}
			
			
			String userEnrolledPlan = planAndBenefitService.getEnrolledPlan(customerId, beneficiaryId);
			
			logger.info("User Plan :"+ userEnrolledPlan);
			
			AjaxResponsePrice ajaxResponsePrice = planAndBenefitService.getServicePrice(serviceCategoryCode,null);
			
			//Incase the service is covered by Plan, the Price will be void.
			if(planAndBenefitService.isServiceCoveredByPlan(serviceCategoryCode, userEnrolledPlan)) {
				ajaxResponsePrice.setStatus(Constant.AJAX_SUCCESS);
				ajaxResponsePrice.setMessage("Ajax Call Successful");
				ajaxResponsePrice.setComments("0.00 &#8377 (Covered By Plan)");
				ajaxResponsePrice.setPriceInInr("0.00");
				ajaxResponsePrice.setPriceInUsd("0.00");
			}
			
			logger.info(ajaxResponsePrice.toString());
			return ajaxResponsePrice;
			
			}catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxResponsePrice(Constant.AJAX_EXCEPTION,ex.getMessage());
			}catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxResponsePrice(Constant.AJAX_EXCEPTION,ex.getMessage());			
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
				return new AjaxResponsePrice(Constant.AJAX_EXCEPTION,"Error Occured, please logout and try again.");
			}
		
		
		}
    
    
    
}

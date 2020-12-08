package com.josthi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserServiceRequestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceRequestController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	ServiceRequestService serviceRequestService;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("/user/requestService")
	public String userRequestService(Model model,
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message,
			@RequestParam (name="statusForTable", required = false, defaultValue = "") String statusForTable,
			@RequestParam (name="messageForTable", required = false, defaultValue = "") String messageForTable,
			@RequestParam (name="gotoPoint", required = false, defaultValue = "") String gotoPoint,
			HttpServletRequest request) throws Exception {
		
			String actionStatus = "";
			String displayMsg = "";
		try {
			
			ValidateSession.isValidSession(request);
			
			//Used for CARD 1
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			//Used for CARD 2
			if(statusForTable!=null && statusForTable.length() > 0) {
		    	 model.addAttribute("statusForTable", statusForTable);
				 model.addAttribute("messageForTable", messageForTable);
	   	 	}
			
			//Getting the Customer ID / role from Session.
			String userId = ValidateSession.getUserId(request);
			String userRole = ValidateSession.getUserRole(request);
			
			//Initializing a new Bean so that it can be passed to the UI for the users to create the Ticket.
			ServiceRequestBean serviceRequestBean = new ServiceRequestBean();
			
			//Pulling all the active Tickets to be displayed on the Screen.
			List<ServiceRequestBean> serviceRequestBeanList= serviceRequestService.getServiceRequestList(userId, userRole);
			
			List<DropDownBean> beneficiaryList = cacheConfigService.getBeneficiaryList(userId);
			
			if(beneficiaryList.size() == 0) {
				message = "Looks like there is no beneficiary setup. please set-up the beneficiary with complete details,"
						+ " then you will be able to put the request.";
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", message);
			}
			
			List<DropDownBean> serviceTypeList = cacheConfigService.getServiceTypeList(userId);
			List<DropDownBean> servicaCategoryList = cacheConfigService.getServicaCategoryList(userId);
			List<DropDownBean> onDemandServicaList = cacheConfigService.getOnDemandServicaList();
			List<DropDownBean> urgencyTypeList = cacheConfigService.getUrgencyTypeList();
			
			//logger.info(userId+"##############"+beneficiaryList.toString());
			
			model.addAttribute("beneficiaryList",beneficiaryList);
			model.addAttribute("serviceTypeList",serviceTypeList);
			model.addAttribute("servicaCategoryList",servicaCategoryList);
			model.addAttribute("onDemandServicaList",onDemandServicaList);
			model.addAttribute("urgencyTypeList",urgencyTypeList);
			
			model.addAttribute("serviceRequestBean",serviceRequestBean);
			model.addAttribute("serviceRequestBeanList",serviceRequestBeanList);
			
			
			if(gotoPoint!=null && gotoPoint.length()>0) {
				model.addAttribute("gotoPoint",gotoPoint);
			}
			
			return "user/request_service_user";
			
			
		} catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			
			model.addAttribute("beneficiaryList",cacheConfigService.getDefaultList());
			model.addAttribute("serviceTypeList",cacheConfigService.getDefaultList());
			model.addAttribute("servicaCategoryList",cacheConfigService.getDefaultList());
			model.addAttribute("onDemandServicaList",cacheConfigService.getDefaultList());
			model.addAttribute("urgencyTypeList",cacheConfigService.getDefaultList());
			model.addAttribute("serviceRequestBean",new ServiceRequestBean());
			
			
			return "user/request_service_user";
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message =  ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			model.addAttribute("beneficiaryList",cacheConfigService.getDefaultList());
			model.addAttribute("serviceTypeList",cacheConfigService.getDefaultList());
			model.addAttribute("servicaCategoryList",cacheConfigService.getDefaultList());
			model.addAttribute("onDemandServicaList",cacheConfigService.getDefaultList());
			model.addAttribute("urgencyTypeList",cacheConfigService.getDefaultList());
			model.addAttribute("serviceRequestBean",new ServiceRequestBean());
						
			return "user/request_service_user";
		}
			
	}
	
	
	
	@RequestMapping(path ="/user/createServiceTicket/{custId}", method = RequestMethod.POST)
	public String createServiceTicket(Model model, ServiceRequestBean serviceRequestBean,
															@PathVariable String custId,
															HttpServletRequest request) {
		
		logger.info("serviceRequestBean before Update:"+serviceRequestBean.toString());
		
		String actionStatus = "";
		String message = "";
		try {		
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, custId.trim());
			
				String ticketNumber = serviceRequestService.createTicket(serviceRequestBean, custId.trim());
				
				if(ticketNumber!=null && ticketNumber.length() > 0) {
					actionStatus = MessageConstant.USER_SUCCESS_STATUS;
					message = "Ticket created Successfully. Here is the Ticket no :"+ticketNumber;
					ServiceRequestBean serviceRequestBeanForEmail = serviceRequestService.getServiceRequestDetailsOnTicketNumber(custId.trim(),ticketNumber);
	    			if(serviceRequestBean!=null) {
	    				
	    				UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
	    				String sessionEmailId = userSessionBean.getUserEmailId();
	    				
	                	//Service email 
	        	        Map<String, String> serviceEmailMap = new HashMap<String, String>();
	        	        serviceEmailMap.put("ticketNumber", serviceRequestBeanForEmail.getTicketNo());
	        	        serviceEmailMap.put("createDate", serviceRequestBeanForEmail.getRequestedDate());
	        	        serviceEmailMap.put("requestedFor", serviceRequestBeanForEmail.getRequestedFor());       	        
	        	        serviceEmailMap.put("requestedBy", serviceRequestBeanForEmail.getRequestedBy());
	        	        serviceEmailMap.put("customerId", serviceRequestBeanForEmail.getRequesterId());
	        	        serviceEmailMap.put("beneficiaryId", serviceRequestBeanForEmail.getBeneficiaryId());
	        	        serviceEmailMap.put("urgency", serviceRequestBeanForEmail.getServiceUrgency());
	        	        serviceEmailMap.put("status", serviceRequestBeanForEmail.getServiceStatus());
	        	        serviceEmailMap.put("serviceCharge", "0$");
	        	        serviceEmailMap.put("paymentStatus","Covered by Plan");
	        	        serviceEmailMap.put("serviceType",serviceRequestBeanForEmail.getServiceType());
	        	        serviceEmailMap.put("serviceDesc",serviceRequestBeanForEmail.getServiceReqDescription());
	        	        serviceEmailMap.put("serviceLastUpdateUser",serviceRequestBeanForEmail.getLastUpdatedBy());
	        	        serviceEmailMap.put("serviceLastUpdateComments",serviceRequestBeanForEmail.getLastCommentsNotes());
	        	        
	        	        EmailDbBean emailDbBeanForService = Utils.getEmailBeanForServiceTicket(sessionEmailId, serviceRequestBeanForEmail.getTicketNo(), Utils.mapToString(serviceEmailMap));
	        	        boolean serviceQueueStatus = emailService.queueEmail(emailDbBeanForService);
	    			}
				}else{
					actionStatus = MessageConstant.USER_FAILURE_STATUS;
					message = "Ticket creation Failed.";
				}
				
				
				return "redirect:/user/requestService?status="+actionStatus+"&message="+message;
			
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/requestService?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while saving the Request. Call Customer Service.";
			return "redirect:/user/requestService?status="+actionStatus+"&message="+message;
		}
		
		
	}
	
	
	
	
	@RequestMapping(path ="/user/ticketHistory/{ticket}", method = RequestMethod.GET)
	public String ticketHistory(Model model,
									@PathVariable String ticket,
									HttpServletRequest request) {
		
		
		String actionStatus = "";
		String message = "";
		try {					
			ValidateSession.isValidSession(request);

			if(ticket!=null && ticket.length() > 0 && serviceRequestService.isValidTicket(ticket)) {
				
				List<ServiceRequestHistoryBean> serviceRequestHistoryBeanList= serviceRequestService.getServiceRequestHistoryBeanList(ticket);
				//logger.info("***********************"+serviceRequestHistoryBeanList.toString());
				model.addAttribute("serviceRequestHistoryBeanList",serviceRequestHistoryBeanList);
				model.addAttribute("ticket",ticket);
				return "user/ticket_history";

			 }else{
					//actionStatus = MessageConstant.USER_FAILURE_STATUS;
				 message = "Invalid Ticket Number, please log out and re login.";
				 throw new UserExceptionInvalidData(message);
			 }

		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/requestService?statusForTable="+actionStatus+"&messageForTable="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while Fetching the ticket history. Call Customer Service.";
			return "redirect:/user/requestService?statusForTable="+actionStatus+"&messageForTable="+message;
		}
		
		
		
		
		
	}
	
	
	

}

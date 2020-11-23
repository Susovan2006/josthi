package com.josthi.web.controller;

import java.util.List;

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
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.ServiceRequestService;
import com.josthi.web.service.UserAuthService;
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
	
	@GetMapping("/user/requestService")
	public String userRequestService(Model model,
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message,
			HttpServletRequest request) throws Exception {
		
			String actionStatus = "";
			String displayMsg = "";
		try {
			
			ValidateSession.isValidSession(request);
			
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			String userId = userSessionBean.getCustomerId();
			

			ServiceRequestBean serviceRequestBean = new ServiceRequestBean();
			
			//Pulling all the active Tickets to be displayed on the Screen.
			List<ServiceRequestBean> serviceRequestBeanList= serviceRequestService.getServiceRequestList(userId);
			
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
			return "redirect:/login";
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
					//Send email.
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
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = "System Error Occured while saving the Request. Call Customer Service.";
			return "redirect:/user/requestService?status="+actionStatus+"&message="+message;
		}
		
		
	}
	
	
	

}

package com.josthi.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.josthi.web.bo.CustomerQueryBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.controller.CacheConfigDataController;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.CacheConfigPO;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.EmailService;
import com.josthi.web.utils.Utils;


@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("/")
	public String hello(Model model) {
		//model.addAttribute("message","HelloWorld");
		return "Landing_page_josthi";
	}
	
	
	@GetMapping("/howItWork")
	public String testHtml(Model model) {
		return "how_it_works";
	}
	
	
	@GetMapping("/knowUs")
	public String forAgents(Model model) {
		return "know_Us";
	}
	

	@GetMapping("/comingSoon")
	public String login(Model model,
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message	) {
		
		
		model.addAttribute("customerQueryBean", new CustomerQueryBean());
		if(status!=null && status.length() > 0) {
	    	 model.addAttribute("status", status);
			 model.addAttribute("message", message);
   	    }
		return "coming-soon";
	}
	
	
	@RequestMapping(path ="/saveEmail", method = RequestMethod.POST)
	public String saveEmail(Model model, CustomerQueryBean customerQueryBean,
									HttpServletRequest request) {
		
    		//logger.debug("Customer ID from Session:"+custId);
    		
    		String actionStatus = "";
    		String message = "";  		
    		try { 
    			
    			if(StringUtils.isEmpty(customerQueryBean.getCustomerEmail().trim())) {
        			throw new UserExceptionInvalidData("Email is a required field.");
        		}
        		
        		if(!Utils.isValidEmailAddress(customerQueryBean.getCustomerEmail().trim())) {
        			throw new UserExceptionInvalidData("Invalid Email address. Please re-validate the details.");
        		}
        		boolean updateStatus = false;
    			updateStatus = cacheConfigService.addUserQuery("UNKNOWN",
    															customerQueryBean.getCustomerEmail(),
    														   "Email Subscribe.");
    			
    			return "redirect:/comingSoon?status="+MessageConstant.USER_SUCCESS_STATUS+"&message=Thanks for Your Interest!! We will let you know all the updates we have";
    			
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/comingSoon?status="+actionStatus+"&message="+message;
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/comingSoon?status="+actionStatus+"&message="+message;
    		}catch(Exception ex) {
    			logger.error("Error :", ex);
    		    return "redirect:/comingSoon?status="+MessageConstant.USER_FAILURE_STATUS+"&message=Error Occured, please try later";
    		}
    		
	}
	
	
	
	
	//account_recovery
	@GetMapping("/contactUs")
	public String contactUs(Model model,
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message) {
		
			model.addAttribute("customerQueryBean", new CustomerQueryBean());
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	    }
			
			return "contact_us";
	}
	
	
	@RequestMapping(path ="/saveCustomerQuery", method = RequestMethod.POST)
	public String saveCustomerQuery(Model model, CustomerQueryBean customerQueryBean,
									HttpServletRequest request) {
		
    		//logger.debug("Customer ID from Session:"+custId);
    		
    		String actionStatus = "";
    		String message = "";  		
    		try { 
    			
    			String name = customerQueryBean.getCustomerFirstName().trim() + " " + customerQueryBean.getCustomerLastName().trim();
    			String email = customerQueryBean.getCustomerEmail().trim();
    			String userNotes = customerQueryBean.getCustomerNotes().trim();
    			
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
    	        	return "redirect:/contactUs?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="+enqueryStatus;
    			}else{
    				throw new UserExceptionInvalidData("Error : Error Occured while processing the enquire request, Try again later.");
    			}
    			
    			
    			
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/contactUs?status="+actionStatus+"&message="+message;
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/contactUs?status="+actionStatus+"&message="+message;
    		}catch(Exception ex) {
    			logger.error("Error :", ex);
    		    return "redirect:/contactUs?status="+MessageConstant.USER_FAILURE_STATUS+"&message=Error Occured, please try later";
    		}
    		
	}
	
	
	
	//search_lawyers
	@GetMapping("/searchLawyers")
	public String searchLawyers(Model model) {
			model.addAttribute("message","HelloWorld");
			return "search_lawyers";
	}
	
	
	//form-layouts-filters
	/*
	 * @GetMapping("/test") public String test(Model model) {
	 * model.addAttribute("message","HelloWorld"); return "form-layouts-filters"; }
	 */
	
	//josthi_pricing
	@GetMapping("/pricing")
	public String pricing(Model model) {
			model.addAttribute("message","HelloWorld");
			
			
			model.addAttribute("planAndBenefitBeanList",CacheConfigDataController.planAndBenefitBeanList);
			model.addAttribute("planPrice",CacheConfigDataController.priceMonthlyAndYearly);
			return "josthi_pricing";
	}
	
	//OnDemand Service
	@GetMapping("/onDemandService")
	public String onDemandService(Model model) {
		model.addAttribute("onDemandServiceList",CacheConfigDataController.onDemandServiceBeanList);
		return "onDemand_Service";
	}
	
	
	
	//service_details.html
	@GetMapping("/planDetails")
	public String planDetails(Model model) {
		
		
		List<ServiceDetailsBean> serviceDetailListToDisplay = CacheConfigDataController.planListtoDisplay;
		
		List<ServiceDetailsBean> basicServiceListToDisplay = new ArrayList<ServiceDetailsBean>();
		List<ServiceDetailsBean> emergencyServiceListToDisplay = new ArrayList<ServiceDetailsBean>();
		List<ServiceDetailsBean> generalServiceListToDisplay = new ArrayList<ServiceDetailsBean>();
		
		for( ServiceDetailsBean serviceDetailsBean : serviceDetailListToDisplay) {
			if(serviceDetailsBean.getServiceType().equalsIgnoreCase(Constant.SERVICE_BASIC_SERVICE)) {
				basicServiceListToDisplay.add(serviceDetailsBean);
			}else if(serviceDetailsBean.getServiceType().equalsIgnoreCase(Constant.SERVICE_EMERGENCY_SERVICE)) {
				emergencyServiceListToDisplay.add(serviceDetailsBean);
			}else if(serviceDetailsBean.getServiceType().equalsIgnoreCase(Constant.SERVICE_GENERAL_SERVICE)) {
				generalServiceListToDisplay.add(serviceDetailsBean);
			}
		}
		model.addAttribute("basicServiceListToDisplay", basicServiceListToDisplay);
		model.addAttribute("emergencyServiceListToDisplay", emergencyServiceListToDisplay);
		model.addAttribute("generalServiceListToDisplay", generalServiceListToDisplay);
		
		
			
		//Here we are getting the section to be displayed on html Screen.
	    //The Value is coming from the Static Variable that we fetch from the database.
		//List<CacheConfigPO> planDetailList = CacheConfigDataController.configMap.get(MappingConstant.VERBIAGE_HTML_PLAN_DETAILS);
		//System.out.println("@@@@@@@@@@@@"+planDetailList.toString());	
		
		
		/*List<CacheConfigPO> basicServiceList = new ArrayList<CacheConfigPO>();
		List<CacheConfigPO> emergencyServiceList = new ArrayList<CacheConfigPO>();
		List<CacheConfigPO> generalServiceList = new ArrayList<CacheConfigPO>();
		
		for( CacheConfigPO serviceDetails : planDetailList) {
			if(serviceDetails.getScreenSection().equalsIgnoreCase(MappingConstant.VERBIAGE_HTML_PLAN_DETAILS_SECTION_BASIC)) {
				basicServiceList.add(serviceDetails);
			}else if(serviceDetails.getScreenSection().equalsIgnoreCase(MappingConstant.VERBIAGE_HTML_PLAN_DETAILS_SECTION_EMERGENCY)) {
				emergencyServiceList.add(serviceDetails);
			}else if(serviceDetails.getScreenSection().equalsIgnoreCase(MappingConstant.VERBIAGE_HTML_PLAN_DETAILS_SECTION_GENERAL)) {
				generalServiceList.add(serviceDetails);
			}
		}
		model.addAttribute("basicServiceList", basicServiceList);
		model.addAttribute("emergencyServiceList", emergencyServiceList);
		model.addAttribute("generalServiceList", generalServiceList);*/
			return "service_details";
	}
	
	//faq
	@GetMapping("/faq")
	public String faq(Model model) {
				model.addAttribute("message","HelloWorld");
				return "faq";
	}
	
	//faq
	@GetMapping("/terms")
	public String terms(Model model) {
				model.addAttribute("message","HelloWorld");
				return "terms";
	}
	
	//privacy
	@GetMapping("/privacy")
	public String privacy(Model model) {
				model.addAttribute("message","HelloWorld");
				return "privacy";
	}
	
	//user_home_page
	@GetMapping("/userHomePage")
	public String userHomePage(Model model) {
			model.addAttribute("message","HelloWorld");
			return "user_home_page";
	}
	
	
	//user_personal_details
	@GetMapping("/userPersonalDetails")
	public String userPersonalDetails(Model model) {
			model.addAttribute("message","HelloWorld");
			return "user_personal_details";
	}
	
	/*
	 * @GetMapping(value = "/image") public @ResponseBody byte[] getImage() throws
	 * IOException { InputStream in = getClass()
	 * .getResourceAsStream("/com/josthi/web/IE_icon.png"); return
	 * IOUtils.toByteArray(in); }
	 */
	
	
	/**
	 * ============================================================================================
	 * ==================== U S E R    D E T A I L S ==============================================
	 * ============================================================================================
	 */
	
	/*
	 * @GetMapping("/user/home") public String userHome(Model model) { return
	 * "user/home_user"; }
	 */
	
	/*
	 * @GetMapping("/user/profile") public String userProfile(Model model) { return
	 * "user/profile_user"; }
	 */
	
	//Beneficiary Details
	/*
	 * @GetMapping("/user/beneficiaryDetails") public String
	 * userBeneficiaryDetails(Model model) { return
	 * "user/beneficiary_details_users"; }
	 */
	
	//Emergency Contacts
	/*@GetMapping("/user/emergencyContacts")
	public String userEmergencyContacts(Model model) {
			return "user/emergency_contact_user";
	}*/
	
	//Login &amp; security
	/*
	 * @GetMapping("/user/loginAndsecurity") public String
	 * userLoginAndsecurity(Model model) { return "user/login_and_security_user"; }
	 */
	
	
	//Preferences
	/*
	 * @GetMapping("/user/preferences") public String userPreferences(Model model) {
	 * return "user/preferences_user"; }
	 */
	
	//Subscribe a Plan
	/*
	 * @GetMapping("/user/subscribePlan") public String userSubscribePlan(Model
	 * model) { return "user/subscribe_a_plan_user"; }
	 */
	
	//Purchase History
	/*@GetMapping("/user/purchaseHistory")
	public String userPurchaseHistory(Model model) {
			return "user/purchase_history_user";
	}*/
	
	//RequestService
	/*
	 * @GetMapping("/user/requestService") public String userRequestService(Model
	 * model) { return "user/request_service_user"; }
	 */
	
	//Reports
	@GetMapping("/user/reports")
	public String userReports(Model model) {
			return "user/report_user";
			//return "user/purchase_invoice_Plan";
	}
	
	//Documents
	@GetMapping("/user/documents")
	public String userDocuments(Model model) {
			return "user/documents_users";
	}
	
	//Feedback
	/*@GetMapping("/user/feedback")
	public String userFeedback(Model model) {
			return "user/feedback_from_user";
	}*/
	
	@GetMapping("/user/ticketHistory")
	public String ticketHistory(Model model) {
		return "user/ticket_history";
	}
	
	/**
	 * ============================================================================================
	 * ================== A G E N T    D E T A I L S ==============================================
	 * ============================================================================================
	 */
	
	@GetMapping("/admin/home")
	public String agentAdminHome(Model model) {
			return "/admin/home_admin";
	}
	
	/*
	 * @GetMapping("/agentAdmin/profile") public String agentAdminProfile(Model
	 * model) { return "/agentAdmin/profile_agent_admin"; }
	 */
	
	/*
	 * @GetMapping("/agentAdmin/settings") public String agentAdminSettings(Model
	 * model) { return "/agentAdmin/account_settings"; }
	 */
	
	@GetMapping("/agentAdmin/contactRepo")
	public String agentAdminContactRepo(Model model) {
			return "/agentAdmin/build_service_repository";
	}
	
	/*
	 * @GetMapping("/admin/viewTicketRequest") public String
	 * agentAdminViewRequest(Model model) { return
	 * "/admin/manage_service_request_admin"; }
	 */
	
	@GetMapping("/agentAdmin/searchUser")
	public String agentAdminSearchUser(Model model) {
			return "/agentAdmin/search_user_agent";
	}
	
	/*
	 * @GetMapping("/common/viewProfile") public String viewProfile(Model model) {
	 * return "/common/user_profile_view_in_frame"; }
	 */

}

package com.josthi.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.josthi.web.constants.MappingConstant;
import com.josthi.web.controller.CacheConfigDataController;
import com.josthi.web.po.CacheConfigPO;


@Controller
public class MainController {
	
	@GetMapping("/")
	public String hello(Model model) {
		//model.addAttribute("message","HelloWorld");
		return "Landing_page_josthi";
	}
	
	
	
	/*@GetMapping("/forAgents")
	public String forAgents(Model model) {
		model.addAttribute("message","HelloWorld");
		return "for_agents";
	}*/
	
	//login_simple
	/*@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("message","HelloWorld");
		return "login_simple";
	}*/
	
	
	//josthi_signup.html
	//@GetMapping("/userSignup")
	//public String userSignup(Model model) {
	//	model.addAttribute("message","HelloWorld");
	//	return "josthi_signup";
	//}
	
	//account_recovery
	/*
	 * @GetMapping("/accountRecovery") public String accountRecovery(Model model) {
	 * model.addAttribute("message","HelloWorld"); return "account_recovery"; }
	 */
	
	
	//account_recovery
	@GetMapping("/contactUs")
	public String contactUs(Model model) {
			model.addAttribute("message","HelloWorld");
			return "contact_us";
	}
	
	
	//search_lawyers
	@GetMapping("/searchLawyers")
	public String searchLawyers(Model model) {
			model.addAttribute("message","HelloWorld");
			return "search_lawyers";
	}
	
	
	//form-layouts-filters
	@GetMapping("/test")
	public String test(Model model) {
			model.addAttribute("message","HelloWorld");
			return "form-layouts-filters";
	}
	
	//josthi_pricing
	@GetMapping("/pricing")
	public String pricing(Model model) {
			model.addAttribute("message","HelloWorld");
			return "josthi_pricing";
	}
	
	
	
	
	//service_details.html
	@GetMapping("/planDetails")
	public String planDetails(Model model) {
			
		//Here we are getting the section to be displayed on html Screen.
	    //The Value is coming from the Static Variable that we fetch from the database.
		List<CacheConfigPO> planDetailList = CacheConfigDataController.configMap.get(MappingConstant.VERBIAGE_HTML_PLAN_DETAILS);
		//System.out.println("@@@@@@@@@@@@"+planDetailList.toString());	
		
		
		List<CacheConfigPO> basicServiceList = new ArrayList<CacheConfigPO>();
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
		model.addAttribute("generalServiceList", generalServiceList);
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
	
	@GetMapping("/user/home")
	public String userHome(Model model) {
			return "user/home_user";
	}
	
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
	@GetMapping("/user/subscribePlan")
	public String userSubscribePlan(Model model) {
			return "user/subscribe_a_plan_user";
	}
	
	//Purchase History
	@GetMapping("/user/purchaseHistory")
	public String userPurchaseHistory(Model model) {
			return "user/purchase_history_user";
	}
	
	//RequestService
	/*
	 * @GetMapping("/user/requestService") public String userRequestService(Model
	 * model) { return "user/request_service_user"; }
	 */
	
	//Reports
	@GetMapping("/user/reports")
	public String userReports(Model model) {
			return "user/report_user";
	}
	
	//Documents
	@GetMapping("/user/documents")
	public String userDocuments(Model model) {
			return "user/documents_users";
	}
	
	//Feedback
	@GetMapping("/user/feedback")
	public String userFeedback(Model model) {
			return "user/feedback_from_user";
	}
	
	@GetMapping("/user/ticketHistory")
	public String ticketHistory(Model model) {
		return "user/ticket_history";
	}
	
	/**
	 * ============================================================================================
	 * ================== A G E N T    D E T A I L S ==============================================
	 * ============================================================================================
	 */
	
	@GetMapping("/agentAdmin/home")
	public String agentAdminHome(Model model) {
			return "/agentAdmin/index";
	}
	
	/*
	 * @GetMapping("/agentAdmin/profile") public String agentAdminProfile(Model
	 * model) { return "/agentAdmin/profile_agent_admin"; }
	 */
	
	@GetMapping("/agentAdmin/settings")
	public String agentAdminSettings(Model model) {
			return "/agentAdmin/account_settings";
	}
	
	@GetMapping("/agentAdmin/contactRepo")
	public String agentAdminContactRepo(Model model) {
			return "/agentAdmin/build_service_repository";
	}
	
	@GetMapping("/agentAdmin/viewRequest")
	public String agentAdminViewRequest(Model model) {
			return "/agentAdmin/view_request_agent";
	}
	
	@GetMapping("/agentAdmin/searchUser")
	public String agentAdminSearchUser(Model model) {
			return "/agentAdmin/search_user_agent";
	}

}

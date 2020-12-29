package com.josthi.web.constants;

public interface MappingConstant {
	
	String LOGIN_PAGE = "login_simple";
	String USER_REGISTRATION = "User_Registration";
	String AGENT_REGISTRATION = "for_agents";
	
	String USER_PROFILE_DETAILS = "user/profile_user";
	String USER_PROFILE_UPDATE = "user/userProfileUpdate";
	String USER_EMERGENCY_CONTACT_DETAILS = "user/emergency_contact_user";
	String USER_BENEFICIARY_DETAILS = "user/beneficiary_details_users";
	String USER_LOGIN_AND_SECURITY = "user/login_and_security_user";
	String USER_SUBSCRIBE_PLAN = "user/subscribe_a_plan_user";
	String USER_SUBSCRIBE_PLAN_REDIRECT = "redirect:/user/subscribePlan";
	String USER_PURCHASE_HISTORY = "user/purchase_history_user";
	String USER_FEEDBACK ="user/feedback_from_user";
	
	String VERBIAGE_HTML_PLAN_DETAILS = "service_details.html";
	
	String VERBIAGE_HTML_PLAN_DETAILS_SECTION_BASIC = "BasicService";
	String VERBIAGE_HTML_PLAN_DETAILS_SECTION_EMERGENCY = "EmergencyService";
	String VERBIAGE_HTML_PLAN_DETAILS_SECTION_GENERAL = "GeneralService";
	
	
	//----------------AGENTS AND ADMIN----------------
	String ADMIN_PROFILE = "admin/profile_admin";  //*profile_agent_admin";
	String ADMIN_ACCOUNT_SETTINGS = "agentAdmin/account_settings";
	String ADMIN_MANAGE_TICKET = "/admin/manage_service_request_admin";
	String ADMIN_ASSIGN_AGENTS = "/admin/admin_action_assign_agent_simple";

}

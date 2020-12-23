package com.josthi.web.constants;

public interface Constant {
	
	//String DATE_FORMAT_FOR_DOB = "yyyy-MM-dd";
	String DATE_FORMAT_FOR_DOB = "MM/dd/yyyy";
	String DATE_FORMAT_FOR_TICKET_COMPLETION_DATE = "yyyy-MM-dd";
	String DATE_FORMAT_FOR_TICKET_HISTORY ="d MMM yyyy hh:mm aaa";
	//https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	
	String USER_STATUS_ACTIVE = "A";
	String USER_STATUS_DISABLED = "D";
	String USER_STATUS_VALIDATION_PENDING = "P";
	String USER_ONLINE_STATUS = "ONLINE";
	String USER_OFFLINE_STATUS = "OFFLINE";
	String USER_TYPE_REG_USER = "USER";
	String USER_TYPE_ADMIN = "ADMIN";
	String USER_TYPE_AGENT = "AGENT";
	String USER_TYPE_SUB_AGENT = "SUBAGENT";
	String USER_TYPE_BENEFICIARY ="BENUSER";
	String REQUEST_TICKET = "TICKET";
	String REQUEST_PLAN = "PLAN";
	String REQUEST_INCIDENT = "INCIDENT";
	String USER_TEMPORARY_LOCK_YES = "YES";
	String USER_TEMPORARY_LOCK_NO = "NO";
	
	String BENEFICIARY_TYPE_PRIMARY = "PRIMARY";
	String BENEFICIARY_TYPE_SECONDARY = "SECONDARY";
	
	String STATUS_ACTIVE = "ACTIVE";
	String STATUS_DEACTIVE = "DEACTIVE";
	//The User Can try max 3 times with incorrect Password.
	int LOGIN_RETRY_COUNT = 3;
	
	String ENQUERY_STATUS_INITIATED = "INITIATED";
	String ENQUERY_STATUS_INPROGRESS = "WIP";
	String ENQUERY_STATUS_RESOLVED = "RESOLVED";
	String ENQUERY_STATUS_CLOSED = "CLOSED";
	String ENQUERY_STATUS_OPEN = "OPEN";
	
	
	//TICKET STATUS
	//These status should be in sync in the database
	String TICKET_STATUS_PENDING_PAYMENT = "PENDING PAYMENT";
	String TICKET_STATUS_INITIATED = "INITIATED";   			//00AAS
	String TICKET_STATUS_INPROGRESS = "WORK_IN_PROGRESS"; 		//00AAS
	String TICKET_STATUS_RESOLVED = "RESOLVED";			  		//00AAS
	String TICKET_STATUS_OPEN = "OPEN";					  		//00AAS
	String TICKET_STATUS_REOPEN = "REOPEN";				  		//00AAS
	String TICKET_STATUS_WAITING_FOR_INFO = "WAITING_FOR_INFO"; //UBAAS
	String TICKET_STATUS_COMPLETED = "COMPLETED";				//UBAAS
	String TICKET_STATUS_CLOSED = "CLOSED";						//UBAAS
	String TICKET_STATUS_ADD_NOTES = "ADD_NOTES";				//UBAAS
	String TICKET_STATUS_CANCEL = "CANCEL";						//UBAAS
	
	
	//Session Objects
	String USER_SESSION_OBJ_KEY = "USER_SESSION_OBJ_KEY";
	String USER_SESSION_PROFILE_PICTURE_KEY = "USER_SESSION_PROFILE_PICTURE_KEY";
	String DEFAULT_PROFILE_PICTURE = "/images/default_user.png";
	
	
	//DROPDOWN TYPE
	String BLOOD_GROUP = "BLOOD_GROUP";
	String LANGUAGE = "Language";
	String TIME_ZONE = "TimeZone";
	
	String SERVICE_TYPE = "ServiceTypeOnDemand";
	String PAID_SERVICE = "PaidServicaCategory";
	String ON_DEMAND_SERVICE = "OnDemandServicaCategory";
	String URGENCY_TYPE = "UrgencyType";
	String PLAN_TYPE = "FamailyPlanType";
	
	
	//AJAX MESSAGE
	
	String AJAX_SUCCESS = "SUCCESS";
	String AJAX_EXCEPTION = "EXCEPTION";
	String AJAX_ERROR = "ERROR";
	String AJAX_WARNING = "WARNING";
	String AJAX_INFO = "INFO";
	
	//SERVICE TYPE
	String SERVICE_DEFAILT = "Default";
	String SERVICE_BASIC_SERVICE = "BasicService";
	String SERVICE_EMERGENCY_SERVICE = "EmergencyService";
	String SERVICE_GENERAL_SERVICE = "GeneralService";
	
	//PLAN OFFERED
	String PLAN_BASIC = "Basic";
	String PLAN_SILVER = "Silver";
	String PLAN_GOLD = "Gold";
	
	//These variable are used to match the String we are getting in the Ajax Call
	// This is only used when the user select the service Type and based on that 
	//Service dropdown is populated.
	String PLAN_MATCH_BASIC = "PLANBASIC";
	String PLAN_MATCH_SILVER = "PLANSILVER";
	String PLAN_MATCH_GOLD = "PLANGOLD";
	String SERVICE_MATCH_BASIC ="ODBASIC";
	String SERVICE_MATCH_EMERGENCY ="ODEMERGENCY";
	String SERVICE_MATCH_GENERAL ="ODGENERAL";
	
	String ONE_MONTH = "30DAY";
	String THREE_MONTH = "90DAY";
	String SIX_MONTH = "180DAY";
	String ONE_YEAR = "365DAY";
	
	String ONE_BENEFICIARY = "1BEN";
	String TWO_BENEFICIARY = "2BEN";
	String THREE_BENEFICIARY = "3BEN";
	
	
	//JOSTHI Address
	String JOSTHI_ADDRESS_LINE_1 = "15C/1 Anupama Housing";
	String JOSTHI_ADDRESS_LINE_2 = "VIP ROAD";
	String JOSTHI_CITY = "Kolkata";
	String JOSTHI_PIN = "700052";
	String JOSTHI_EMAIL = "MyJosthi@gmail.com";
	String JOSTHI_CONTACT ="+91 9836548561";
	
	

}

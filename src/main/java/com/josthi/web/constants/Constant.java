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
	String TICKET_STATUS_INITIATED = "INITIATED";
	String TICKET_STATUS_INPROGRESS = "WORK_IN_PROGRESS";
	String TICKET_STATUS_RESOLVED = "RESOLVED";
	
	String TICKET_STATUS_OPEN = "OPEN";
	String TICKET_STATUS_REOPEN = "REOPEN";
	String TICKET_STATUS_WAITING_FOR_INFO = "WAITING_FOR_INFO";
	String TICKET_STATUS_COMPLETED = "COMPLETED";
	String TICKET_STATUS_CLOSED = "CLOSED";
	
	
	//Session Objects
	String USER_SESSION_OBJ_KEY = "USER_SESSION_OBJ_KEY";
	String USER_SESSION_PROFILE_PICTURE_KEY = "USER_SESSION_PROFILE_PICTURE_KEY";
	String DEFAULT_PROFILE_PICTURE = "/images/default_user.png";
	
	
	//DROPDOWN TYPE
	String BLOOD_GROUP = "BLOOD_GROUP";
	String LANGUAGE = "Language";
	String TIME_ZONE = "TimeZone";
	
	String SERVICE_TYPE = "ServiceType";
	String PAID_SERVICE = "PaidServicaCategory";
	String ON_DEMAND_SERVICE = "OnDemandServicaCategory";
	String URGENCY_TYPE = "UrgencyType";
	
	
	

}

package com.josthi.web.constants;

public interface EmailConstant {
	
	
	//Password recovery
	String EMAIL_FROM_FOR_PASSWORD_RECOVERY = "account@josthi.com";
	String SUBJECT_FROM_FOR_PASSWORD_RECOVERY = "Josthi.com | Account Recovery details";

	//Welcome
	String EMAIL_FROM_FOR_WELCOME = "welcome@josthi.com";
	String SUBJECT_FROM_FOR_WELCOME = "Josthi.com | Welcome to josthi.com";
	
	//OTP
	String EMAIL_FROM_FOR_OTP = "account@josthi.com";
	String SUBJECT_FROM_FOR_OTP = "Josthi.com | One time password(OTP)";
	
	//SERVICE REQUEST
	String EMAIL_FROM_FOR_SERVICE = "service@josthi.com";
	String SUBJECT_FROM_FOR_SERVICE = "Josthi.com | serviceRequest";
	
	//AGENT ASSIGNMENT / UPDATE Request
	String EMAIL_FROM_FOR_AGENT_UPDATE = "service@josthi.com";
	String SUBJECT_FROM_FOR_AGENT_UPDATE = "Josthi.com | Agent Update";
	
	//Password Lock
	String EMAIL_FROM_FOR_ACCOUNT_UN_LOCK = "welcome@josthi.com";
	String SUBJECT_FROM_FOR_ACCOUNT_UN_LOCK = "Josthi.com | Account unlock";
	String ACCOUNT_UNLOCK_MESSAGE = "Looks like you exceeded the max number of retry, so your Password is locked. Please click the below link to unlock your Account.";
	
	//PLAN INVOIVE
	String EMAIL_FROM_FOR_PLAN = "invoice@josthi.com";
	String SUBJECT_FROM_FOR_PLAN_INVOIVE = "Josthi.com | purchase invoice";
	
	
	//Generic Status
	String LOAD_STATUS = "LOADED";
	String RETRY_STATUS = "RETRY";
	String SUCCESS_STATUS = "SUCCESS";
	String LOADED_EMAIL_DELIVARY_STATUS = "LOADED";
	String FAILED_EMAIL_DELIVARY_STATUS = "FAILED";
	String DELEVERED_EMAIL_DELIVARY_STATUS = "DELEVERED";
	
	//email Templates
	String TEMPLATE_FORM_FOR_PASSWORD_RECOVERY = "email_account_recovery";
	String TEMPLATE_FORM_FOR_ACCOUNT_UNLOCK = "email_account_unlock";
	String TEMPLATE_FORM_FOR_WELCOME = "email_welcome_josthi";
	String TEMPLATE_FORM_FOR_OTP = "email_validate_account_otp";
	//https://beefree.io/editor/?template=your-visit-to-the-spa-invoice# (open in Firefox)
	String TEMPLATE_FORM_FOR_SERVICE_REQUEST_UPDATE = "email_ticket_update";
	String TEMPLATE_FORM_FOR_AGENT_ASSIGNMENT_UPDATE = "email_Agent_Assignment";
	String TEMPLATE_FORM_FOR_PURCHASE_INVOICE = "email_invoice";
	
	
	
	
}

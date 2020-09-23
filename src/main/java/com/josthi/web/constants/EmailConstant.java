package com.josthi.web.constants;

public interface EmailConstant {
	
	
	//Password recovery
	String EMAIL_FROM_FOR_PASSWORD_RECOVERY = "account@josthi.com";
	String SUBJECT_FROM_FOR_PASSWORD_RECOVERY = "Josthi.com | Account Recovery details";

	//Welcome
	String EMAIL_FROM_FOR_WELCOME = "welcome@josthi.com";
	String SUBJECT_FROM_FOR_WELCOME = "Josthi.com | Welcome to josthi.com";
	
	
	//Generic Status
	String LOAD_STATUS = "LOADED";
	String RETRY_STATUS = "RETRY";
	String SUCCESS_STATUS = "SUCCESS";
	String LOADED_EMAIL_DELIVARY_STATUS = "LOADED";
	String FAILED_EMAIL_DELIVARY_STATUS = "FAILED";
	String DELEVERED_EMAIL_DELIVARY_STATUS = "DELEVERED";
	
	//email Templates
	String TEMPLATE_FROM_FOR_PASSWORD_RECOVERY = "email_account_recovery";
	String TEMPLATE_FROM_FOR_WELCOME = "email_welcome_josthi";
	
	
	
}

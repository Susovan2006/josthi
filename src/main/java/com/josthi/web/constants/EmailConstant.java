package com.josthi.web.constants;

public interface EmailConstant {
	String EMAIL_FROM_FOR_PASSWORD_RECOVERY = "account@josthi.com";
	String SUBJECT_FROM_FOR_PASSWORD_RECOVERY = "Josthi.com | Account Recovery details";
	String TEMPLATE_FROM_FOR_PASSWORD_RECOVERY = "email_Account_Recovery";
	String LOAD_STATUS_FROM_FOR_PASSWORD_RECOVERY = "LOADED";
	String RETRY_STATUS_FROM_FOR_PASSWORD_RECOVERY = "RETRY";
	String SUCCESS_STATUS_FROM_FOR_PASSWORD_RECOVERY = "SUCCESS";
	String LOADED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY = "LOADED";
	String FAILED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY = "FAILED";
	String DELEVERED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY = "DELEVERED";
}
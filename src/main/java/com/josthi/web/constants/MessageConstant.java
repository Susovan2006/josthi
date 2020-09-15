package com.josthi.web.constants;

public interface MessageConstant {
	
	
	String LOGIN_ERROR_ON_DATABASE_UPDATE_FAILURE = "LOGIN Valid, but Update error Occured, Please try again after some time.";
	String LOGIN_ERROR_ON_ACCOUNT_DEACTIVATED = "Account is deactivated, Please contact the Customer Service Executive.";
	String LOGIN_ERROR_ACCOUNT_TEMP_LOCKED = "Looks like the the account is temporarily deactivated due to multiple incorrect retries. Try after 1 Hours.";
	String LOGIN_ERROR_INCORECT_USER_ID_PASSWORD = "Looks like the user ID and the password is incorrect, retry with valid user ID and password. or click Forget password.";
	String LOGIN_ERROR_INCORRECT_PASSWORD_WITH_ATEMPT = "Looks like you entered an invalid password, please try again. you will get to try 3 attempts, then your ID will be locked. This is your attemp no : ";
	String LOGIN_ERROR_INVALID_PASSWORD ="Try again, Invalid Password. You can try max 3 times.";
	String LOGIN_ERROR_MAX_TRY_EXCEEDED = "Max attempt exceed, the account is temporary locked, Please wait for an hour to relogin.";
	String LOGIN_ERROR_GENERAL = "Invalid User ID and Password, please try again, you will get max 3 attempts, then the password will be locked.";


}

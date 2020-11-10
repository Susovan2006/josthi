package com.josthi.web.constants;

public interface MessageConstant {
	
	String LOGIN_ERROR_VALIDATION = "UserD/emailID or Password can't be blank";
	String LOGIN_ERROR_ON_DATABASE_UPDATE_FAILURE = "LOGIN Valid, but Update error Occured, Please try again after some time.";
	String LOGIN_ERROR_ON_ACCOUNT_DEACTIVATED = "Account is deactivated, Please contact the Customer Service Executive.";
	String LOGIN_ERROR_ACCOUNT_TEMP_LOCKED = "Looks like the the account is temporarily deactivated due to multiple incorrect retries. Try after 1 Hours.";
	String LOGIN_ERROR_INCORECT_USER_ID_PASSWORD = "Looks like the user ID and the password is incorrect, retry with valid user ID and password. or click Forget password.";
	String LOGIN_ERROR_INCORRECT_PASSWORD_WITH_ATEMPT = "Looks like you entered an invalid password, please try again. you will get to try 3 attempts, then your ID will be locked. This is your attemp no : ";
	String LOGIN_ERROR_INVALID_PASSWORD ="Try again, Invalid Password. You can try max 3 times.";
	String LOGIN_ERROR_MAX_TRY_EXCEEDED = "Max login attempt exceed, the account is temporary locked, Please wait for 24 hour to relogin or check your email for unlock instructions.";
	String LOGIN_ERROR_GENERAL = "Invalid User ID and Password, please try again, you will get max 3 attempts, then the password will be locked.";
	String LOGIN_ERROR_MAX_TRY_EXCEEDED_NO_EMAIL = "Max login attempt exceed, the account is temporary locked, Please wait for 24 hour to relogin or contact customer Service.";

	
	String ACCOUNT_RECOVERY_SUCCESS = "Please check your email. Password reset email will be sent in few min.";
	String ACCOUNT_RECOVERY_ERROR = "Looks like the userID/email is not registered in this app, please try with a correct email ID that was used during the registration";
	String ACCOUNT_RECOVERY_EXCEPTION = "Error occured while recovering the password, please try again later or open a Ticket.";

	String NEW_REGISTRATION_DUPLICATE_USER_ID = "Looks like you are already registered, you can directly login, if you forgot the password, click on the forget password link and reveive it over email.";
	String NEW_REGISTRATION_FAILED = "User Registration failed, Please try after sometime or contact the Customer Service";
	String NEW_REGISTRATION_SUCCESSFUL = "Registration Process successful, please login and complete the details to start the service. Please check your email for more details.";
	String NEW_REGISTRATION_SUCCESSFUL_CONDITIONAL = "Your account got created, but the welcome email delivery failed, Check if you are able to login, else contact contact customer service";
	String NEW_REGISTRATION_FAILED_UNMATCH_PASSWORD = "Looks like the password and the confirm password field has different value. Please retry with same value";
	String NEW_REGISTRATION_FAILED_REQUIRED_FIELDS = "All the Fields are required fields, Please fill with proper values.";
	
	String ACCOUNT_UNLOCK_SUCCESS = "Your Account is unlocked successfully, now you can try to login";
	String ACCOUNT_UNLOCK_FAILED = "System failed to unlock your account. Please wait for 24 hours or contact the Customer Service.";

	
	
	
	//***************************** User Profile********************************
	String USER_SUCCESS_STATUS = "Success";
	String USER_WARNING_STATUS = "Warning";
	String USER_FAILURE_STATUS = "Failure";
	String USER_STATUS_NULL_STATUS = null;
	
	String USER_PROFILE_UPDATE_SUCCESS_MESSAGE = "User Profile Updated Successfully!!";
	String USER_PROFILE_UPDATE_ERROR_MESSAGE = "Exception Occured while updating the user Profile. Try again later or Contact the Customer Service.";
	String USER_PROFILE_UPDATE_VALIDATION_ERROR_MESSAGE = "Looks like there is some issue with the Customer ID, please log out and relogin.";
	
	
	String USER_EMERGENCY_GENERIC_ERROR = "Error Occured while performing backend operation, please try again later or call Customer Service.";
	String USER_EMERGENCY_SAVE_SUCCESS_MESSAGE = "Emergency details saved Successfully!!";
	String USER_EMERGENCY_UPDATE_SUCCESS_MESSAGE = "Emergency details Updated Successfully!!";
	String USER_EMERGENCY_SAVE_ERROR_MESSAGE = "Exception Occured while saving emergency contact details. Try again later or Contact the Customer Service.";
	String USER_EMERGENCY_SAVE_VALIDATION_ERROR_MESSAGE = "Looks like there is some issue with the Customer ID, please log out and relogin.";
	String USER_SESSION_VALIDATION_ERROR_MESSAGE = "Looks like there is some issue with the Authorization, please log out and relogin.";
	String USER_BENEFECIARY_SAVE_SUCCESS_MESSAGE = "Benefeciary details saved Successfully!!";
	String USER_BENEFECIARY_SAVE_ERROR_MESSAGE = "Exception Occured while saving Benefeciary details. Try again later or Contact the Customer Service.";
	
	String USER_EMERGENCY_DELETE_SUCCESS_MESSAGE = "Contact Deleted";
}

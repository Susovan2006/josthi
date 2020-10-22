package com.josthi.web.constants;

public interface Constant {
	
	String USER_STATUS_ACTIVE = "A";
	String USER_ONLINE_STATUS = "ONLINE";
	String USER_OFFLINE_STATUS = "OFFLINE";
	String USER_TYPE_REG_USER = "USER";
	String USER_TYPE_ADMIN = "ADMIN";
	String USER_TYPE_AGENT = "AGENT";
	String USER_TYPE_BENEFICIARY ="BENUSER";
	String USER_TEMPORARY_LOCK_YES = "YES";
	String USER_TEMPORARY_LOCK_NO = "NO";
	
	String STATUS_ACTIVE = "ACTIVE";
	String STATUS_DEACTIVE = "DEACTIVE";
	//The User Can try max 3 times with incorrect Password.
	int LOGIN_RETRY_COUNT = 3;
	
	
	
	
	//Session Objects
	String USER_SESSION_OBJ_KEY = "USER_SESSION_OBJ_KEY";
	String USER_SESSION_PROFILE_PICTURE_KEY = "USER_SESSION_PROFILE_PICTURE_KEY";
	String DEFAULT_PROFILE_PICTURE = "/images/default_user.png";
	
	
	

}

package com.josthi.web.constants;

public interface Constant {
	
	String USER_STATUS_ACTIVE = "A";
	String USER_ONLINE_STATUS = "ONLINE";
	String USER_OFFLINE_STATUS = "OFFLINE";
	String USER_TYPE_REG_USER = "RegUser";
	String USER_TYPE_ADMIN = "Admin";
	String USER_TYPE_AGENT = "Agent";
	String USER_TYPE_BENEFICIARY ="Beneficiary";
	String USER_TEMPORARY_LOCK_YES = "YES";
	String USER_TEMPORARY_LOCK_NO = "NO";
	
	String STATUS_ACTIVE = "ACTIVE";
	String STATUS_DEACTIVE = "DEACTIVE";
	//The User Can try max 3 times with incorrect Password.
	int LOGIN_RETRY_COUNT = 3;
	
	
	

}

package com.josthi.web.service;

import com.josthi.web.bo.SchedulerTimerBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserAuthService {
	
	
	public UserAuthBo getValidUser(String uid, String password);

	public int isValidUserID(String emailID);

	public UserAuthBo getValidUser(String emailID);

	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

	public int getNextID();

	public String getUserDetails(String customerId);

	public boolean resetUserIdGenTable();

	//public void updateSchedulerDetails(SchedulerTimerBean schedulerTimerBean);

	//public boolean registerNewUser(UserRegistrationBean userRegistrationBean);

}

package com.josthi.web.service;

import com.josthi.web.bo.UserAuthBo;

public interface UserAuthService {
	
	
	public UserAuthBo getValidUser(String uid, String password);

	public int isValidUserID(String emailID);

	public UserAuthBo getValidUser(String emailID);

	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

}

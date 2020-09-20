package com.josthi.web.dao;

import com.josthi.web.bo.UserAuthBo;

public interface UserAuthDao {

	UserAuthBo getValidUser(String uid, String password);

	UserAuthBo getValidUser(String emailID);

	int getValidUserCount(String emailID);

	boolean updateLoginStatus(UserAuthBo userDetailsOnUid);

	boolean updateLoginStatusOnSuccess(UserAuthBo userDetails);

	int getNectID();

}

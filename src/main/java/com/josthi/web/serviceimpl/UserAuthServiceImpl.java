package com.josthi.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.service.UserAuthService;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService{

	@Autowired
	public UserAuthDao userAuthDao;
	
	@Override
	public UserAuthBo getValidUser(String uid, String password) {
		UserAuthBo userAuthBo = userAuthDao.getValidUser(uid,password);		
		return userAuthBo;
	}

	@Override
	public int isValidUserID(String emailID) {
		return userAuthDao.getValidUserCount(emailID);
	}

	@Override
	public UserAuthBo getValidUser(String emailID) {
		return userAuthDao.getValidUser(emailID);
	}

	@Override
	public boolean updateLoginStatus(UserAuthBo userDetailsOnUid) {
		return userAuthDao.updateLoginStatus(userDetailsOnUid);		
	}

	@Override
	public boolean updateLoginStatusOnSuccess(UserAuthBo userDetails) {
		return userAuthDao.updateLoginStatusOnSuccess(userDetails);
	}

	@Override
	public int getNextID() {
		return userAuthDao.getNectID();
	}
	
	
	/*
	 * public UserRegForm getValidUser(String uid, String password){ UserRegForm
	 * userRegForm = logindao.getValidUser(uid,password); return userRegForm; }
	 */

}

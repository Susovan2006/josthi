package com.josthi.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.dao.UserAuthDao;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;

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

	@Override
	public String getUserDetails(String customerId) {
		return Utils.convertToCamelCase(userAuthDao.getUserFirstAndLastName(customerId));
	}

	@Override
	public boolean resetUserIdGenTable() {
		return userAuthDao.resetUserIdGenTable();
	}

	@Override
	public boolean removeTempLockFromUserAccount() {
		// TODO Auto-generated method stub
		return userAuthDao.removeTempLockFromUserAccount();
	}

	@Override
	public boolean isValidPassword(String emailId, String wordApp) throws Exception {
		try {
			UserAuthBo userAuthBo = userAuthDao.getValidUser(emailId,wordApp);	
			if(userAuthBo.getWordapp().length()>0) {
				return true;
			}else {
				throw new UserExceptionInvalidData("Looks like you entered incorrect password in the current Password filed.");
			}
		}catch(Exception ex) {
			throw new UserExceptionInvalidData("Looks like you entered incorrect password in the current Password filed.");
		}
	}

	@Override
	public boolean updatePassword(PasswordResetBean passwordResetBean) throws Exception {
		passwordResetBean.setOldPassword(Security.encrypt(passwordResetBean.getOldPassword()));
		passwordResetBean.setNewPassword(Security.encrypt(passwordResetBean.getNewPassword()));
		passwordResetBean.setNewConfirmPassword(Security.encrypt(passwordResetBean.getNewConfirmPassword()));
		return userAuthDao.updatePassword(passwordResetBean);
	}
	
	
	/*
	 * public UserRegForm getValidUser(String uid, String password){ UserRegForm
	 * userRegForm = logindao.getValidUser(uid,password); return userRegForm; }
	 */

}

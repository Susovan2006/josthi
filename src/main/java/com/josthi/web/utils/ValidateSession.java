package com.josthi.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.exception.UserException;

public class ValidateSession {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ValidateSession.class);
	
	public static String getUserId(HttpServletRequest request) throws Exception{
		try {
			isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			return userSessionBean.getCustomerId();
			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public static String getUserRole(HttpServletRequest request) throws Exception{
		try {
			isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			return userSessionBean.getUserRole();			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public static String getUserName(HttpServletRequest request) throws Exception{
		try {
			isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			return userSessionBean.getUserName();			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public static String getUserEmail(HttpServletRequest request) throws Exception{
		try {
			isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			return userSessionBean.getUserEmailId();			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	
	public static boolean isValidSession(HttpServletRequest request) throws Exception {	
		try {
			
			HttpSession session = request.getSession(false);
			if(session == null) {
				throw new UserException("Session has expired, please relogin");
			}else {
	    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
	    		if(userSessionBean!=null) {
		    		if(!(userSessionBean.isUserSessionValid())){
		    			throw new UserException("Session has expired, please relogin");
		    		}else {
		    			return true;
		    		}
	    		}else {
	    			throw new UserException("Session has expired, please relogin");
	    		}
			}
			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	
	
	public static boolean isValidUser(HttpServletRequest request, String uid) throws Exception {	
		try {
			if(isValidSession(request)) {
				UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
				String sessionCustomerId = userSessionBean.getCustomerId();
				logger.info("sessionCustomerId :"+ sessionCustomerId);
				
				if(uid.equalsIgnoreCase(sessionCustomerId)) {
					return true;
				}else {
					throw new UserException("Invalid user ID or Session has expired. Please relogin");
				}
				
			}else {
				throw new UserException("Session has expired. Please relogin");
			}
			
		}catch(UserException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ex;
		}
	}

}

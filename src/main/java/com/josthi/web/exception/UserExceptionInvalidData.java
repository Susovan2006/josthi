package com.josthi.web.exception;

import java.util.Date;

public class UserExceptionInvalidData extends Exception{
	
	private String appMessage = "";
	private String appCategory = "";
	private Date errorDate = null;
	
	


	public UserExceptionInvalidData(String appMessage, String appCategory) {
		super();
		this.appMessage = appMessage;
		this.appCategory = appCategory;
		//errorDate=new Date();
	}
	
	public UserExceptionInvalidData(String appMessage) {
		super();
		this.appMessage = appMessage;
		this.appCategory = "ERROR";
		errorDate=new Date();
	}
	
	
	/*
	 * public String getMessage() { return appCategory+ ":" +appMessage; }
	 */
	
	public String getMessage() {
		return appMessage;
	}
	
	public String getTime() {
		return errorDate.toString();
	}
	
	
	
	public String getAppMessage() {
		return appMessage;
	}


	public void setAppMessage(String appMessage) {
		this.appMessage = appMessage;
	}


	public String getAppCategory() {
		return appCategory;
	}


	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}


	public Date getErrorDate() {
		return errorDate;
	}


	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}
	
	
	

}

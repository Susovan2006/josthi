package com.josthi.web.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AjaxResponsePrice {
	  private String status;
	  private String message;
	  
	  private String comments;
	  private String priceInInr;
	  private String priceInUsd;
	  private String disclaimer; 
	  
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPriceInInr() {
		return priceInInr;
	}
	public void setPriceInInr(String priceInInr) {
		this.priceInInr = priceInInr;
	}
	public String getPriceInUsd() {
		return priceInUsd;
	}
	public void setPriceInUsd(String priceInUsd) {
		this.priceInUsd = priceInUsd;
	}
	
	
	
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public AjaxResponsePrice() {
		super();
	}
	  
	public AjaxResponsePrice(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    } 
	  
}

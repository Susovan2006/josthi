package com.josthi.web.bo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AjaxRestResponse {
	  private String status;
	  private Object data;
	  private String message;
	  private List<DropDownBean> list1;
	  private List<DropDownBean> list2;
	  
	  public AjaxRestResponse(){
	    
	  }
	  
	  public AjaxRestResponse(String status, Object data){
	    this.status = status;
	    this.data = data;
	  }
	 
	  
	  
	  public AjaxRestResponse(String status, String message, Object data, List<DropDownBean> list1) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.list1 = list1;
	}

	public AjaxRestResponse(String status, String message, Object data, List<DropDownBean> list1, List<DropDownBean> list2) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.list1 = list1;
		this.list2 = list2;
	}

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

	public Object getData() {
	    return data;
	  }
	 
	  public void setData(Object data) {
	    this.data = data;
	  }

	public List<DropDownBean> getList1() {
		return list1;
	}

	public void setList1(List<DropDownBean> list1) {
		this.list1 = list1;
	}

	public List<DropDownBean> getList2() {
		return list2;
	}

	public void setList2(List<DropDownBean> list2) {
		this.list2 = list2;
	}
	  
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	  
	}

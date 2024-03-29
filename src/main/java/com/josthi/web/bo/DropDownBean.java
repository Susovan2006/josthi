package com.josthi.web.bo;

public class DropDownBean {
	private int tid;
	private String dropDownType;
	private String keyId;
	private String value;
	private String active;
	
	
	public DropDownBean() {
		super();
	}
	
	public DropDownBean(String keyId, String value) {
		super();
		this.keyId = keyId;
		this.value = value;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getDropDownType() {
		return dropDownType;
	}
	public void setDropDownType(String dropDownType) {
		this.dropDownType = dropDownType;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "DropDownBean [tid=" + tid + ", dropDownType=" + dropDownType + ", keyId=" + keyId + ", value=" + value
				+ ", active=" + active + "]";
	}
	
	
}

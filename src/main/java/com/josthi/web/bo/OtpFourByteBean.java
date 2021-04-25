package com.josthi.web.bo;

public class OtpFourByteBean {
	
	private String firstByte;
	private String secondByte;
	private String thirdByte;
	private String forthByte;
	public String getFirstByte() {
		return firstByte;
	}
	public void setFirstByte(String firstByte) {
		this.firstByte = firstByte;
	}
	public String getSecondByte() {
		return secondByte;
	}
	public void setSecondByte(String secondByte) {
		this.secondByte = secondByte;
	}
	public String getThirdByte() {
		return thirdByte;
	}
	public void setThirdByte(String thirdByte) {
		this.thirdByte = thirdByte;
	}
	public String getForthByte() {
		return forthByte;
	}
	public void setForthByte(String forthByte) {
		this.forthByte = forthByte;
	}
	@Override
	public String toString() {
		return "OtpFourByteBean [firstByte=" + firstByte + ", secondByte=" + secondByte + ", thirdByte=" + thirdByte
				+ ", forthByte=" + forthByte + "]";
	}
	
	
	
	

}


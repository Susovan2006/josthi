package com.josthi.web.bo ;

import java.sql.Timestamp ;
import java.util.List;


public class UserDetailsBean {
	private String uid ;
	private String firstName ;
	private String middleName ;
	private String lastName ;
	
	//Address
	private String userAddressFirstLine ;
	private String userAddressSecondLine ;
	private String cityTown ;
	private String state ;
	private String countyDistrict ;
	private String country ;
	private String zipPin; //Added New. Oct 2020.
	//Contact
	private String mobileNo1 ;
	private String mobileNo2 ;
	private String whatsappNo ;
	private String landLineNo ; //Added New. Oct 2020.
	private String faxNo ; //Added New. Oct 2020.
	private String officePhNo; //Added New. Oct 2020.
	//emails and Social Media links
	private String secondaryEmail ;
	private String website ;
	private String facebookLink ;
	private String instagramLink ;
	
	private String emergencyContact ;
	private Integer beneficiaryCount ;
	private String photoId ;
	private  String photoPassportSize ;
	private String planTypeName ;
	private String planActive ;
	private Timestamp planSubscribeDate ;
	private String monthlyYearlyPlan ;
	private Timestamp planRenueDate ;
	private String reminderEnabled ;
	private String comments ;
	private String userStatus ;

	
	public String getUid(){
		return uid ;
	}
	public void setUid(String uid){
		this.uid = uid ;
	}
	public String getFirstName(){
		return firstName ;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName ;
	}
	public String getMiddleName(){
		return middleName ;
	}
	public void setMiddleName(String middleName){
		this.middleName = middleName ;
	}
	public String getLastName(){
		return lastName ;
	}
	public void setLastName(String lastName){
		this.lastName = lastName ;
	}
	public String getUserAddressFirstLine(){
		return userAddressFirstLine ;
	}
	public void setUserAddressFirstLine(String userAddressFirstLine){
		this.userAddressFirstLine = userAddressFirstLine ;
	}
	public String getUserAddressSecondLine(){
		return userAddressSecondLine ;
	}
	public void setUserAddressSecondLine(String userAddressSecondLine){
		this.userAddressSecondLine = userAddressSecondLine ;
	}
	public String getCityTown(){
		return cityTown ;
	}
	public void setCityTown(String cityTown){
		this.cityTown = cityTown ;
	}
	public String getState(){
		return state ;
	}
	public void setState(String state){
		this.state = state ;
	}
	public String getCountyDistrict(){
		return countyDistrict ;
	}
	public void setCountyDistrict(String countyDistrict){
		this.countyDistrict = countyDistrict ;
	}
	public String getCountry(){
		return country ;
	}
	public void setCountry(String country){
		this.country = country ;
	}
	public String getMobileNo1(){
		return mobileNo1 ;
	}
	public void setMobileNo1(String mobileNo1){
		this.mobileNo1 = mobileNo1 ;
	}
	public String getMobileNo2(){
		return mobileNo2 ;
	}
	public void setMobileNo2(String mobileNo2){
		this.mobileNo2 = mobileNo2 ;
	}
	public String getWhatsappNo(){
		return whatsappNo ;
	}
	public void setWhatsappNo(String whatsappNo){
		this.whatsappNo = whatsappNo ;
	}
	public String getSecondaryEmail(){
		return secondaryEmail ;
	}
	public void setSecondaryEmail(String secondaryEmail){
		this.secondaryEmail = secondaryEmail ;
	}
	public String getEmergencyContact(){
		return emergencyContact ;
	}
	public void setEmergencyContact(String emergencyContact){
		this.emergencyContact = emergencyContact ;
	}
	public String getWebsite(){
		return website ;
	}
	public void setWebsite(String website){
		this.website = website ;
	}
	public String getFacebookLink(){
		return facebookLink ;
	}
	public void setFacebookLink(String facebookLink){
		this.facebookLink = facebookLink ;
	}
	public String getInstagramLink(){
		return instagramLink ;
	}
	public void setInstagramLink(String instagramLink){
		this.instagramLink = instagramLink ;
	}
	public Integer getBeneficiaryCount(){
		return beneficiaryCount ;
	}
	public void setBeneficiaryCount(Integer beneficiaryCount){
		this.beneficiaryCount = beneficiaryCount ;
	}
	public String getPhotoId(){
		return photoId ;
	}
	public void setPhotoId(String photoId){
		this.photoId = photoId ;
	}
	public String getPhotoPassportSize(){
		return photoPassportSize ;
	}
	public void setPhotoPassportSize(String photoPassportSize){
		this.photoPassportSize = photoPassportSize ;
	}
	public String getPlanTypeName(){
		return planTypeName ;
	}
	public void setPlanTypeName(String planTypeName){
		this.planTypeName = planTypeName ;
	}
	public String getPlanActive(){
		return planActive ;
	}
	public void setPlanActive(String planActive){
		this.planActive = planActive ;
	}
	public Timestamp getPlanSubscribeDate(){
		return planSubscribeDate ;
	}
	public void setPlanSubscribeDate(Timestamp planSubscribeDate){
		this.planSubscribeDate = planSubscribeDate ;
	}
	public String getMonthlyYearlyPlan(){
		return monthlyYearlyPlan ;
	}
	public void setMonthlyYearlyPlan(String monthlyYearlyPlan){
		this.monthlyYearlyPlan = monthlyYearlyPlan ;
	}
	public Timestamp getPlanRenueDate(){
		return planRenueDate ;
	}
	public void setPlanRenueDate(Timestamp planRenueDate){
		this.planRenueDate = planRenueDate ;
	}
	public String getReminderEnabled(){
		return reminderEnabled ;
	}
	public void setReminderEnabled(String reminderEnabled){
		this.reminderEnabled = reminderEnabled ;
	}
	public String getComments(){
		return comments ;
	}
	public void setComments(String comments){
		this.comments = comments ;
	}
	public String getUserStatus(){
		return userStatus ;
	}
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus ;
	}

	}

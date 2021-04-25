package com.josthi.web.bo ;

import java.sql.Timestamp ;
import java.util.List;


public class UserDetailsBean {
	private String uid ;
	private String firstName ;
	private String middleName ;
	private String lastName ;
	private String gender ; //Added New. Oct 2020.
	
	//Address
	private String userAddressFirstLine ;	
	private String userAddressSecondLine ;
	private String userAdditionalAddressLine;    //ADDITIONAL_ADDRESS_LINE added Oct 2020
	private String policeStation;                //POLICE_STATION added on Oct 2020
	private String postOffice;					 //POST_OFFICE added on Oct 2020.
	private String nearestLandMark;				 //NEAREST_LAND_MARK Added on Oct 2020
	private String coverageArea;				 //COVERAGE_AREA added on Oct 2020 - Mainly for Agents.
	
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
	
	//Some of the agent details
	private String agencyName; 					//AGENCY_NAME Added on Oct 2020.
	private String agencyDescription;			//AGENCY_DESCRIPTION
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

	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getZipPin() {
		return zipPin;
	}
	public void setZipPin(String zipPin) {
		this.zipPin = zipPin;
	}
	public String getLandLineNo() {
		return landLineNo;
	}
	public void setLandLineNo(String landLineNo) {
		this.landLineNo = landLineNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getOfficePhNo() {
		return officePhNo;
	}
	public void setOfficePhNo(String officePhNo) {
		this.officePhNo = officePhNo;
	}
	public String getUserAdditionalAddressLine() {
		return userAdditionalAddressLine;
	}
	public void setUserAdditionalAddressLine(String userAdditionalAddressLine) {
		this.userAdditionalAddressLine = userAdditionalAddressLine;
	}
	public String getPoliceStation() {
		return policeStation;
	}
	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}
	public String getPostOffice() {
		return postOffice;
	}
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}
	public String getNearestLandMark() {
		return nearestLandMark;
	}
	public void setNearestLandMark(String nearestLandMark) {
		this.nearestLandMark = nearestLandMark;
	}
	public String getCoverageArea() {
		return coverageArea;
	}
	public void setCoverageArea(String coverageArea) {
		this.coverageArea = coverageArea;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyDescription() {
		return agencyDescription;
	}
	public void setAgencyDescription(String agencyDescription) {
		this.agencyDescription = agencyDescription;
	}
	@Override
	public String toString() {
		return "UserDetailsBean [uid=" + uid + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", gender=" + gender + ", userAddressFirstLine=" + userAddressFirstLine
				+ ", userAddressSecondLine=" + userAddressSecondLine + ", userAdditionalAddressLine="
				+ userAdditionalAddressLine + ", policeStation=" + policeStation + ", postOffice=" + postOffice
				+ ", nearestLandMark=" + nearestLandMark + ", coverageArea=" + coverageArea + ", cityTown=" + cityTown
				+ ", state=" + state + ", countyDistrict=" + countyDistrict + ", country=" + country + ", zipPin="
				+ zipPin + ", mobileNo1=" + mobileNo1 + ", mobileNo2=" + mobileNo2 + ", whatsappNo=" + whatsappNo
				+ ", landLineNo=" + landLineNo + ", faxNo=" + faxNo + ", officePhNo=" + officePhNo + ", secondaryEmail="
				+ secondaryEmail + ", website=" + website + ", facebookLink=" + facebookLink + ", instagramLink="
				+ instagramLink + ", emergencyContact=" + emergencyContact + ", beneficiaryCount=" + beneficiaryCount
				+ ", photoId=" + photoId + ", photoPassportSize=" + photoPassportSize + ", planTypeName=" + planTypeName
				+ ", planActive=" + planActive + ", planSubscribeDate=" + planSubscribeDate + ", monthlyYearlyPlan="
				+ monthlyYearlyPlan + ", planRenueDate=" + planRenueDate + ", reminderEnabled=" + reminderEnabled
				+ ", comments=" + comments + ", agencyName=" + agencyName + ", agencyDescription=" + agencyDescription
				+ ", userStatus=" + userStatus + "]";
	}
	
	
	
	
	
	
	
	}

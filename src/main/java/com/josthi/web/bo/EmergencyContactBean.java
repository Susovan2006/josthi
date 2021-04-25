package com.josthi.web.bo;

import java.sql.Timestamp ;

public class EmergencyContactBean {
	private Integer contactId ;
	private String primaryUid ;
	private String benId ;
	private String emergencyContactName ;
	//private String emergencyContactNameInitials ;
	private String emergencyContactNumber ;
	private String emergencyContactStayLocation ;
	private String relation ;
	private String contactNoValidated ;
	private Timestamp insertDate ;
	private Timestamp updateDate ;
	private String notes ;
	private String editLinks ;



	public Integer getContactId(){
		return contactId ;
	}
	public void setContactId(Integer contactId){
		this.contactId = contactId ;
	}
	public String getPrimaryUid(){
		return primaryUid ;
	}
	public void setPrimaryUid(String primaryUid){
		this.primaryUid = primaryUid ;
	}
	public String getBenId(){
		return benId ;
	}
	public void setBenId(String benId){
		this.benId = benId ;
	}
	public String getEmergencyContactName(){
		return emergencyContactName ;
	}
	public void setEmergencyContactName(String emergencyContactName){
		this.emergencyContactName = emergencyContactName ;
	}
	
	
	public String getEmergencyContactNumber(){
		return emergencyContactNumber ;
	}
	public void setEmergencyContactNumber(String emergencyContactNumber){
		this.emergencyContactNumber = emergencyContactNumber ;
	}
	public String getEmergencyContactStayLocation(){
		return emergencyContactStayLocation ;
	}
	public void setEmergencyContactStayLocation(String emergencyContactStayLocation){
		this.emergencyContactStayLocation = emergencyContactStayLocation ;
	}
	public String getRelation(){
		return relation ;
	}
	public void setRelation(String relation){
		this.relation = relation ;
	}
	public String getContactNoValidated(){
		return contactNoValidated ;
	}
	public void setContactNoValidated(String contactNoValidated){
		this.contactNoValidated = contactNoValidated ;
	}
	public Timestamp getInsertDate(){
		return insertDate ;
	}
	public void setInsertDate(Timestamp insertDate){
		this.insertDate = insertDate ;
	}
	public Timestamp getUpdateDate(){
		return updateDate ;
	}
	public void setUpdateDate(Timestamp updateDate){
		this.updateDate = updateDate ;
	}
	public String getNotes(){
		return notes ;
	}
	public void setNotes(String notes){
		this.notes = notes ;
	}	
	public String getEditLinks() {
		return editLinks;
	}
	public void setEditLinks(String editLinks) {
		this.editLinks = editLinks;
	}
	@Override
	public String toString() {
		return "EmergencyContactBean [contactId=" + contactId + ", primaryUid=" + primaryUid + ", benId=" + benId
				+ ", emergencyContactName=" + emergencyContactName + ", emergencyContactNumber="
				+ emergencyContactNumber + ", emergencyContactStayLocation=" + emergencyContactStayLocation
				+ ", relation=" + relation + ", contactNoValidated=" + contactNoValidated + ", insertDate=" + insertDate
				+ ", updateDate=" + updateDate + ", notes=" + notes + ", editLinks=" + editLinks + "]";
	}
	
	
}

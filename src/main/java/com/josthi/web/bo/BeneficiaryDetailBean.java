package com.josthi.web.bo;

import java.sql.Timestamp;

public class BeneficiaryDetailBean extends UserDetailsBean{
	
	
	//private String uid ;
	//private String firstName ;
	//private String middleName ;
	//private String lastName ;
	//private String gender ; 
	
	//Address
	//private String userAddressFirstLine ;	
	//private String userAddressSecondLine ;
	//private String cityTown ;
	//private String state ;
	//private String countyDistrict ;
	//private String country ;
	//private String zipPin; //Added New. Oct 2020.
	
	//Contact
	//private String mobileNo1 ;
	//private String whatsappNo ;
	//private String secondaryEmail ;
	//private String userStatus ;
	
	private String tid;
	private String customerID;
	private String beneficiaryID;
	private String relationWithCustomer;
	private String dateOfBirth;
	private Timestamp dateOfBirthInTimeStamp;
	private String age;
	private String height;
	private String weight;
	private String bloodGroup;
	private String prefferedHospital;
	private String mediclameInsuranceName;
	private String insuranceRelatedNotes;
	private String healthCondition;
	private String medicalChallenges;
	
	//private UserDetailsBean userDetailsBean;
	


	
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getBeneficiaryID() {
		return beneficiaryID;
	}
	public void setBeneficiaryID(String beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}
	public String getRelationWithCustomer() {
		return relationWithCustomer;
	}
	public void setRelationWithCustomer(String relationWithCustomer) {
		this.relationWithCustomer = relationWithCustomer;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Timestamp getDateOfBirthInTimeStamp() {
		return dateOfBirthInTimeStamp;
	}
	public void setDateOfBirthInTimeStamp(Timestamp dateOfBirthInTimeStamp) {
		this.dateOfBirthInTimeStamp = dateOfBirthInTimeStamp;
	}
	
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPrefferedHospital() {
		return prefferedHospital;
	}
	public void setPrefferedHospital(String prefferedHospital) {
		this.prefferedHospital = prefferedHospital;
	}
	public String getMediclameInsuranceName() {
		return mediclameInsuranceName;
	}
	public void setMediclameInsuranceName(String mediclameInsuranceName) {
		this.mediclameInsuranceName = mediclameInsuranceName;
	}
	public String getInsuranceRelatedNotes() {
		return insuranceRelatedNotes;
	}
	public void setInsuranceRelatedNotes(String insuranceRelatedNotes) {
		this.insuranceRelatedNotes = insuranceRelatedNotes;
	}
	public String getHealthCondition() {
		return healthCondition;
	}
	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition;
	}
	public String getMedicalChallenges() {
		return medicalChallenges;
	}
	public void setMedicalChallenges(String medicalChallenges) {
		this.medicalChallenges = medicalChallenges;
	}
	@Override
	public String toString() {
		return "BeneficiaryDetailBean [tid=" + tid + ", customerID=" + customerID + ", beneficiaryID=" + beneficiaryID
				+ ", relationWithCustomer=" + relationWithCustomer + ", dateOfBirth=" + dateOfBirth + ", height="
				+ height + ", weight=" + weight + ", bloodGroup=" + bloodGroup + ", prefferedHospital="
				+ prefferedHospital + ", mediclameInsuranceName=" + mediclameInsuranceName + ", insuranceRelatedNotes="
				+ insuranceRelatedNotes + ", healthCondition=" + healthCondition + ", medicalChallenges="
				+ medicalChallenges + "]";
	}
	
	

}

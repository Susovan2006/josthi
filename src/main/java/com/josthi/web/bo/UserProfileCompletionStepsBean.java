package com.josthi.web.bo;

import java.util.List;

public class UserProfileCompletionStepsBean {
	
	private String isUserIdCreated;
	private String isEmailValidated;
	private String isBeneficiaryRegistered;
	private String isAgentAssigned;
	
	private String isPlanPurchased;
	
	private List<UserDetailsBean> agentDetailsBeanList; //Actually this is the AgentBean.
	private List<String> userAlert;
	

	
	

	public List<String> getUserAlert() {
		return userAlert;
	}
	public void setUserAlert(List<String> userAlert) {
		this.userAlert = userAlert;
	}
	public List<UserDetailsBean> getAgentDetailsBeanList() {
		return agentDetailsBeanList;
	}
	public void setAgentDetailsBeanList(List<UserDetailsBean> agentDetailsBeanList) {
		this.agentDetailsBeanList = agentDetailsBeanList;
	}
	public String getIsUserIdCreated() {
		return isUserIdCreated;
	}
	public void setIsUserIdCreated(String isUserIdCreated) {
		this.isUserIdCreated = isUserIdCreated;
	}
	public String getIsEmailValidated() {
		return isEmailValidated;
	}
	public void setIsEmailValidated(String isEmailValidated) {
		this.isEmailValidated = isEmailValidated;
	}
	public String getIsAgentAssigned() {
		return isAgentAssigned;
	}
	public void setIsAgentAssigned(String isAgentAssigned) {
		this.isAgentAssigned = isAgentAssigned;
	}
	public String getIsBeneficiaryRegistered() {
		return isBeneficiaryRegistered;
	}
	public void setIsBeneficiaryRegistered(String isBeneficiaryRegistered) {
		this.isBeneficiaryRegistered = isBeneficiaryRegistered;
	}
	public String getIsPlanPurchased() {
		return isPlanPurchased;
	}
	public void setIsPlanPurchased(String isPlanPurchased) {
		this.isPlanPurchased = isPlanPurchased;
	}
	@Override
	public String toString() {
		return "UserProfileCompletionStepsBean [isUserIdCreated=" + isUserIdCreated + ", isEmailValidated="
				+ isEmailValidated + ", isBeneficiaryRegistered=" + isBeneficiaryRegistered + ", isAgentAssigned="
				+ isAgentAssigned + ", isPlanPurchased=" + isPlanPurchased + ", agentDetailsBeanList="
				+ agentDetailsBeanList + "]";
	}

	
	
	

	

	
	

	
	
	

}

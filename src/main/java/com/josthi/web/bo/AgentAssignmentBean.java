package com.josthi.web.bo;

public class AgentAssignmentBean {
	
	private String relationId;
	private String hostUserName;
	private String hostUserId;
	private String hostUserEmail;
	private String beneficiaryName;
	private String beneficiaryId;
	private String beneficiaryPinCode;
	private String agentName;
	private String agentId;
	private String agentPinCode;
	private String lastUpdateDate;
	private String insertDate;
	
	
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getHostUserName() {
		return hostUserName;
	}
	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}
	public String getHostUserId() {
		return hostUserId;
	}
	public void setHostUserId(String hostUserId) {
		this.hostUserId = hostUserId;
	}
	
	
	public String getHostUserEmail() {
		return hostUserEmail;
	}
	public void setHostUserEmail(String hostUserEmail) {
		this.hostUserEmail = hostUserEmail;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public String getBeneficiaryPinCode() {
		return beneficiaryPinCode;
	}
	public void setBeneficiaryPinCode(String beneficiaryPinCode) {
		this.beneficiaryPinCode = beneficiaryPinCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentPinCode() {
		return agentPinCode;
	}
	public void setAgentPinCode(String agentPinCode) {
		this.agentPinCode = agentPinCode;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	@Override
	public String toString() {
		return "AgentAssignmentBean [hostUserName=" + hostUserName + ", hostUserId=" + hostUserId + ", beneficiaryName="
				+ beneficiaryName + ", beneficiaryId=" + beneficiaryId + ", beneficiaryPinCode=" + beneficiaryPinCode
				+ ", agentName=" + agentName + ", agentId=" + agentId + ", agentPinCode=" + agentPinCode
				+ ", lastUpdateDate=" + lastUpdateDate + ", insertDate=" + insertDate + "]";
	}
	
	
	
	
	

}

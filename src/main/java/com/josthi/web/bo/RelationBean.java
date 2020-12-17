package com.josthi.web.bo;

import java.sql.Timestamp;

public class RelationBean {
	
	private String relationId;
	private String customerId;
	private String beneficiaryId;
	private String agentId;
	private Timestamp insertDate;
	private String insertDateStr;
	private Timestamp updateDate;
	private String updateDateStr;
	private String planName;
	private Timestamp planExpireDate;
	private String planExpireDateStr;
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public Timestamp getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
	public String getInsertDateStr() {
		return insertDateStr;
	}
	public void setInsertDateStr(String insertDateStr) {
		this.insertDateStr = insertDateStr;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateDateStr() {
		return updateDateStr;
	}
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Timestamp getPlanExpireDate() {
		return planExpireDate;
	}
	public void setPlanExpireDate(Timestamp planExpireDate) {
		this.planExpireDate = planExpireDate;
	}
	public String getPlanExpireDateStr() {
		return planExpireDateStr;
	}
	public void setPlanExpireDateStr(String planExpireDateStr) {
		this.planExpireDateStr = planExpireDateStr;
	}
	@Override
	public String toString() {
		return "RelationBean [relationId=" + relationId + ", customerId=" + customerId + ", beneficiaryId="
				+ beneficiaryId + ", agentId=" + agentId + ", insertDate=" + insertDate + ", insertDateStr="
				+ insertDateStr + ", updateDate=" + updateDate + ", updateDateStr=" + updateDateStr + ", planName="
				+ planName + ", planExpireDate=" + planExpireDate + ", planExpireDateStr=" + planExpireDateStr + "]";
	}
	

	
}

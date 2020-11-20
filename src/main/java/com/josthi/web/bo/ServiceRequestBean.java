package com.josthi.web.bo;

import java.sql.Timestamp;

public class ServiceRequestBean {
	private Integer uid ;
	private String ticketNo ;
	private String requestedBy ;
	private String requesterId ;
	private String requestedFor ;
	private String requestedVia ;
	private String assignedTo ;
	private Timestamp requestedOn ;
	private Timestamp toBeCompletedBy ;
	private String serviceType ;
	private String serviceCategory ;
	private String serviceReqDescription ;
	private String serviceUrgency ;
	private String serviceStatus ;
	private Timestamp lastUpdate ;
	private String comments ;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getRequesterId() {
		return requesterId;
	}
	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public String getRequestedVia() {
		return requestedVia;
	}
	public void setRequestedVia(String requestedVia) {
		this.requestedVia = requestedVia;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Timestamp getRequestedOn() {
		return requestedOn;
	}
	public void setRequestedOn(Timestamp requestedOn) {
		this.requestedOn = requestedOn;
	}
	public Timestamp getToBeCompletedBy() {
		return toBeCompletedBy;
	}
	public void setToBeCompletedBy(Timestamp toBeCompletedBy) {
		this.toBeCompletedBy = toBeCompletedBy;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getServiceReqDescription() {
		return serviceReqDescription;
	}
	public void setServiceReqDescription(String serviceReqDescription) {
		this.serviceReqDescription = serviceReqDescription;
	}
	public String getServiceUrgency() {
		return serviceUrgency;
	}
	public void setServiceUrgency(String serviceUrgency) {
		this.serviceUrgency = serviceUrgency;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "ServiceRequestBean [uid=" + uid + ", ticketNo=" + ticketNo + ", requestedBy=" + requestedBy
				+ ", requesterId=" + requesterId + ", requestedFor=" + requestedFor + ", requestedVia=" + requestedVia
				+ ", assignedTo=" + assignedTo + ", requestedOn=" + requestedOn + ", toBeCompletedBy=" + toBeCompletedBy
				+ ", serviceType=" + serviceType + ", serviceCategory=" + serviceCategory + ", serviceReqDescription="
				+ serviceReqDescription + ", serviceUrgency=" + serviceUrgency + ", serviceStatus=" + serviceStatus
				+ ", lastUpdate=" + lastUpdate + ", comments=" + comments + "]";
	}
	
	
	

}

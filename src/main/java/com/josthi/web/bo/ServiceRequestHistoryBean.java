package com.josthi.web.bo;

import java.sql.Timestamp;

public class ServiceRequestHistoryBean {

	private Integer historyId ;
	private String ticketNumber ;
	private String status ;
	private String comments ;
	private Timestamp updateTimestamp ;
	private String formattedUpdateTimestamp;
	private String updatedByName ;
	private String updatedById ;
	private int counter;
	
	
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	
	
	
	public String getFormattedUpdateTimestamp() {
		return formattedUpdateTimestamp;
	}
	public void setFormattedUpdateTimestamp(String formattedUpdateTimestamp) {
		this.formattedUpdateTimestamp = formattedUpdateTimestamp;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public String getUpdatedById() {
		return updatedById;
	}
	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	@Override
	public String toString() {
		return "ServiceRequestHistoryBean [historyId=" + historyId + ", ticketNumber=" + ticketNumber + ", status="
				+ status + ", comments=" + comments + ", updateTimestamp=" + updateTimestamp + ", updatedByName="
				+ updatedByName + ", updatedById=" + updatedById + ", counter=" + counter + "]";
	}
	
	
	
	
	

}

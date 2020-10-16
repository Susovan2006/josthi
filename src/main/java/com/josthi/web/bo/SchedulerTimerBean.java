package com.josthi.web.bo;

import java.sql.Timestamp;

public class SchedulerTimerBean {
	
	private String taskId;
	private String taskName;
	private String cronExpresion;
	private Timestamp lastRunTime;
	private Timestamp nextRunTime;
	private String status;
	private String errorMessage;
	private String comments;
	
	
	@Override
	public String toString() {
		return "SchedulerTimerBean [taskId=" + taskId + ", taskName=" + taskName + ", cronExpresion=" + cronExpresion
				+ ", lastRunTime=" + lastRunTime + ", nextRunTime=" + nextRunTime + ", status=" + status
				+ ", errorMessage=" + errorMessage + ", comments=" + comments + "]";
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCronExpresion() {
		return cronExpresion;
	}
	public void setCronExpresion(String cronExpresion) {
		this.cronExpresion = cronExpresion;
	}
	public Timestamp getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(Timestamp lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	public Timestamp getNextRunTime() {
		return nextRunTime;
	}
	public void setNextRunTime(Timestamp nextRunTime) {
		this.nextRunTime = nextRunTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	

}

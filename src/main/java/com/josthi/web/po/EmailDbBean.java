package com.josthi.web.po ;
import java.sql.Timestamp ;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("emailDbBean")
public class EmailDbBean {
	private Integer emailUid ;
	private String sentTo ;
	private String sentFrom ;
	private String subject ;
	private String jsonString ;
	private String emailTemplate ;
	private String emailStatus ;
	private Timestamp emailQueuedAt ;
	private Timestamp emailSentAt ;
	private String emailDelivaryStatus ;

	@Override
	public String toString() {
		return "EmailDbBean [emailUid=" + emailUid + ", sentTo=" + sentTo + ", sentFrom=" + sentFrom + ", subject="
				+ subject + ", jsonString=" + jsonString + ", emailTemplate=" + emailTemplate + ", emailStatus="
				+ emailStatus + ", emailQueuedAt=" + emailQueuedAt + ", emailSentAt=" + emailSentAt
				+ ", emailDelivaryStatus=" + emailDelivaryStatus + "]";
	}
	public Integer getEmailUid(){
		return emailUid ;
	}
	public void setEmailUid(Integer emailUid){
		this.emailUid = emailUid ;
	}
	public String getSentTo(){
		return sentTo ;
	}
	public void setSentTo(String sentTo){
		this.sentTo = sentTo ;
	}
	public String getSentFrom(){
		return sentFrom ;
	}
	public void setSentFrom(String sentFrom){
		this.sentFrom = sentFrom ;
	}
	public String getSubject(){
		return subject ;
	}
	public void setSubject(String subject){
		this.subject = subject ;
	}
	public String getJsonString(){
		return jsonString ;
	}
	public void setJsonString(String jsonString){
		this.jsonString = jsonString ;
	}
	public String getEmailTemplate(){
		return emailTemplate ;
	}
	public void setEmailTemplate(String emailTemplate){
		this.emailTemplate = emailTemplate ;
	}
	public String getEmailStatus(){
		return emailStatus ;
	}
	public void setEmailStatus(String emailStatus){
		this.emailStatus = emailStatus ;
	}
	public Timestamp getEmailQueuedAt(){
		return emailQueuedAt ;
	}
	public void setEmailQueuedAt(Timestamp emailQueuedAt){
		this.emailQueuedAt = emailQueuedAt ;
	}
	public Timestamp getEmailSentAt(){
		return emailSentAt ;
	}
	public void setEmailSentAt(Timestamp emailSentAt){
		this.emailSentAt = emailSentAt ;
	}
	public String getEmailDelivaryStatus(){
		return emailDelivaryStatus ;
	}
	public void setEmailDelivaryStatus(String emailDelivaryStatus){
		this.emailDelivaryStatus = emailDelivaryStatus ;
	}

}

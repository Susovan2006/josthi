package com.josthi.web.bo;

public class JosthiFeedbackBean {
	
	private String feedbackBy;
	private String feedbackNotes;
	public String getFeedbackBy() {
		return feedbackBy;
	}
	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}
	public String getFeedbackNotes() {
		return feedbackNotes;
	}
	public void setFeedbackNotes(String feedbackNotes) {
		this.feedbackNotes = feedbackNotes;
	}
	@Override
	public String toString() {
		return "JosthiFeedbackBean [feedbackBy=" + feedbackBy + ", feedbackNotes=" + feedbackNotes + "]";
	}
	
	

}

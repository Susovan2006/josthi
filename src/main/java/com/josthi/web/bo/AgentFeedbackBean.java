package com.josthi.web.bo ;
import java.math.BigDecimal ;
import java.sql.Timestamp ;
import java.util.List;
public class AgentFeedbackBean {
	private Integer fid ;
	private String feedbackFor ;
	private String feedbackBy ;
	private double proactiveness ;
	private double responsibility ;
	private double availability ;
	private double behavior ;
	private double careAndHandling ;
	private double overallRating ;
	private String areaOfImprovement ;
	private String otherComments ;
	private Timestamp feedbackDate ;

	public Integer getFid(){
		return fid ;
	}
	public void setFid(Integer fid){
		this.fid = fid ;
	}
	public String getFeedbackFor(){
		return feedbackFor ;
	}
	public void setFeedbackFor(String feedbackFor){
		this.feedbackFor = feedbackFor ;
	}
	public String getFeedbackBy(){
		return feedbackBy ;
	}
	public void setFeedbackBy(String feedbackBy){
		this.feedbackBy = feedbackBy ;
	}
	public double getProactiveness() {
		return proactiveness;
	}
	public void setProactiveness(double proactiveness) {
		this.proactiveness = proactiveness;
	}
	public double getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(double responsibility) {
		this.responsibility = responsibility;
	}
	public double getAvailability() {
		return availability;
	}
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	public double getBehavior() {
		return behavior;
	}
	public void setBehavior(double behavior) {
		this.behavior = behavior;
	}
	public double getCareAndHandling() {
		return careAndHandling;
	}
	public void setCareAndHandling(double careAndHandling) {
		this.careAndHandling = careAndHandling;
	}
	public double getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	public String getAreaOfImprovement() {
		return areaOfImprovement;
	}
	public void setAreaOfImprovement(String areaOfImprovement) {
		this.areaOfImprovement = areaOfImprovement;
	}
	public String getOtherComments() {
		return otherComments;
	}
	public void setOtherComments(String otherComments) {
		this.otherComments = otherComments;
	}
	public Timestamp getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(Timestamp feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	@Override
	public String toString() {
		return "AgentFeedbackBean [fid=" + fid + ", feedbackFor=" + feedbackFor + ", feedbackBy=" + feedbackBy
				+ ", proactiveness=" + proactiveness + ", responsibility=" + responsibility + ", availability="
				+ availability + ", behavior=" + behavior + ", careAndHandling=" + careAndHandling + ", overallRating="
				+ overallRating + ", areaOfImprovement=" + areaOfImprovement + ", otherComments=" + otherComments
				+ ", feedbackDate=" + feedbackDate + "]";
	}
	
	
	
	
	

}

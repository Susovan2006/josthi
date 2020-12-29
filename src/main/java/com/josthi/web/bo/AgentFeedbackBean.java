package com.josthi.web.bo ;
import java.math.BigDecimal ;
import java.sql.Timestamp ;
import java.util.List;
public class AgentFeedbackBean {
	private Integer fid ;
	private String feedbackFor ;
	private String feedbackBy ;
	private BigDecimal proactiveness ;
	private BigDecimal responsibility ;
	private BigDecimal availability ;
	private BigDecimal behavior ;
	private BigDecimal careAndHandling ;
	private BigDecimal overallRating ;
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
	public BigDecimal getProactiveness(){
		return proactiveness ;
	}
	public void setProactiveness(BigDecimal proactiveness){
		this.proactiveness = proactiveness ;
	}
	public BigDecimal getResponsibility(){
		return responsibility ;
	}
	public void setResponsibility(BigDecimal responsibility){
		this.responsibility = responsibility ;
	}
	public BigDecimal getAvailability(){
		return availability ;
	}
	public void setAvailability(BigDecimal availability){
		this.availability = availability ;
	}
	public BigDecimal getBehavior(){
		return behavior ;
	}
	public void setBehavior(BigDecimal behavior){
		this.behavior = behavior ;
	}
	public BigDecimal getCareAndHandling(){
		return careAndHandling ;
	}
	public void setCareAndHandling(BigDecimal careAndHandling){
		this.careAndHandling = careAndHandling ;
	}
	public BigDecimal getOverallRating(){
		return overallRating ;
	}
	public void setOverallRating(BigDecimal overallRating){
		this.overallRating = overallRating ;
	}
	public String getAreaOfImprovement(){
		return areaOfImprovement ;
	}
	public void setAreaOfImprovement(String areaOfImprovement){
		this.areaOfImprovement = areaOfImprovement ;
	}
	public String getOtherComments(){
		return otherComments ;
	}
	public void setOtherComments(String otherComments){
		this.otherComments = otherComments ;
	}
	public Timestamp getFeedbackDate(){
		return feedbackDate ;
	}
	public void setFeedbackDate(Timestamp feedbackDate){
		this.feedbackDate = feedbackDate ;
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

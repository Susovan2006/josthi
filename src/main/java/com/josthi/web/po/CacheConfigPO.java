package com.josthi.web.po ;
import java.sql.Timestamp ;



public class CacheConfigPO {
	private Integer verbiageId ;
	private String screenName ;
	private String screenSection ;
	private String screenKey ;
	private String verbiageShortDesc ;
	private String verbiageDetailDesc ;
	private Timestamp lastUpdateTimeStamp ;
	private String lastUpdateUser ;
	private String comments ;
	
	
	
	public Integer getVerbiageId(){
		return verbiageId ;
	}
	public void setVerbiageId(Integer verbiageId){
		this.verbiageId = verbiageId ;
	}
	public String getScreenName(){
		return screenName ;
	}
	public void setScreenName(String screenName){
		this.screenName = screenName ;
	}
	public String getScreenSection(){
		return screenSection ;
	}
	public void setScreenSection(String screenSection){
		this.screenSection = screenSection ;
	}
	public String getScreenKey(){
		return screenKey ;
	}
	public void setScreenKey(String screenKey){
		this.screenKey = screenKey ;
	}
	public String getVerbiageShortDesc(){
		return verbiageShortDesc ;
	}
	public void setVerbiageShortDesc(String verbiageShortDesc){
		this.verbiageShortDesc = verbiageShortDesc ;
	}
	public String getVerbiageDetailDesc(){
		return verbiageDetailDesc ;
	}
	public void setVerbiageDetailDesc(String verbiageDetailDesc){
		this.verbiageDetailDesc = verbiageDetailDesc ;
	}
	public Timestamp getLastUpdateTimeStamp(){
		return lastUpdateTimeStamp ;
	}
	public void setLastUpdateTimeStamp(Timestamp lastUpdateTimeStamp){
		this.lastUpdateTimeStamp = lastUpdateTimeStamp ;
	}
	public String getLastUpdateUser(){
		return lastUpdateUser ;
	}
	public void setLastUpdateUser(String lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser ;
	}
	public String getComments(){
		return comments ;
	}
	public void setComments(String comments){
		this.comments = comments ;
	}
	@Override
	public String toString() {
		return "CacheConfigPO [verbiageId=" + verbiageId + ", screenName=" + screenName + ", screenSection="
				+ screenSection + ", screenKey=" + screenKey + ", verbiageShortDesc=" + verbiageShortDesc
				+ ", verbiageDetailDesc=" + verbiageDetailDesc + ", lastUpdateTimeStamp=" + lastUpdateTimeStamp
				+ ", lastUpdateUser=" + lastUpdateUser + ", comments=" + comments + "]";
	}
	
	
}

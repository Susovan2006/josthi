package com.josthi.web.bo;

public class UserPreferencesBean {

	
	private String userId;
	private String language;
	private String timeZone;
	private boolean planRenewalAlert;
	private boolean whatsappNotificationsAlert;
	private boolean promotionalOfferAlert;
	
	
	public UserPreferencesBean(String language, String timeZone,boolean planRenewalAlert, boolean whatsappNotificationsAlert,
			boolean promotionalOfferAlert) {
		super();
		this.language = language;
		this.timeZone = timeZone;
		this.whatsappNotificationsAlert = whatsappNotificationsAlert;
		this.promotionalOfferAlert = promotionalOfferAlert;
		this.planRenewalAlert = planRenewalAlert;
		this.whatsappNotificationsAlert = whatsappNotificationsAlert;
		this.promotionalOfferAlert = promotionalOfferAlert;
	}
	
	public UserPreferencesBean() {
		super();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public boolean isPlanRenewalAlert() {
		return planRenewalAlert;
	}
	public void setPlanRenewalAlert(boolean planRenewalAlert) {
		this.planRenewalAlert = planRenewalAlert;
	}
	public boolean isWhatsappNotificationsAlert() {
		return whatsappNotificationsAlert;
	}
	public void setWhatsappNotificationsAlert(boolean whatsappNotificationsAlert) {
		this.whatsappNotificationsAlert = whatsappNotificationsAlert;
	}
	public boolean isPromotionalOfferAlert() {
		return promotionalOfferAlert;
	}
	public void setPromotionalOfferAlert(boolean promotionalOfferAlert) {
		this.promotionalOfferAlert = promotionalOfferAlert;
	}

	@Override
	public String toString() {
		return "UserPreferencesBean [userId=" + userId + ", language=" + language + ", timeZone=" + timeZone
				+ ", planRenewalAlert=" + planRenewalAlert + ", whatsappNotificationsAlert="
				+ whatsappNotificationsAlert + ", promotionalOfferAlert=" + promotionalOfferAlert + "]";
	}
	
	
	
}

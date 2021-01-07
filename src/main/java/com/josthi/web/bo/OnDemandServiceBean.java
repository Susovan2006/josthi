package com.josthi.web.bo;

import java.math.BigDecimal;

public class OnDemandServiceBean {
	private Integer id ;
	private String serviceName ;
	private String description ;
	private String serviceCode;
	private String serviceType ;
	private String cutOutPriceStr;
	private BigDecimal onDemantPriceInr ;
	private String onDemandPriceActualStr;
	private String disclaimer ;
	private String onDemandImage;
	private float onDemandReview;
	private String onDemandInfo;
	

	public String getCutOutPriceStr() {
		return cutOutPriceStr;
	}
	public void setCutOutPriceStr(String cutOutPriceStr) {
		this.cutOutPriceStr = cutOutPriceStr;
	}
	public String getOnDemandPriceActualStr() {
		return onDemandPriceActualStr;
	}
	public void setOnDemandPriceActualStr(String onDemandPriceActualStr) {
		this.onDemandPriceActualStr = onDemandPriceActualStr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public BigDecimal getOnDemantPriceInr() {
		return onDemantPriceInr;
	}
	public void setOnDemantPriceInr(BigDecimal onDemantPriceInr) {
		this.onDemantPriceInr = onDemantPriceInr;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public String getOnDemandImage() {
		return onDemandImage;
	}
	public void setOnDemandImage(String onDemandImage) {
		this.onDemandImage = onDemandImage;
	}
	public float getOnDemandReview() {
		return onDemandReview;
	}
	public void setOnDemandReview(float onDemandReview) {
		this.onDemandReview = onDemandReview;
	}
	public String getOnDemandInfo() {
		return onDemandInfo;
	}
	public void setOnDemandInfo(String onDemandInfo) {
		this.onDemandInfo = onDemandInfo;
	}
	@Override
	public String toString() {
		return "OnDemandServiceBean [id=" + id + ", serviceName=" + serviceName + ", description=" + description
				+ ", serviceCode=" + serviceCode + ", serviceType=" + serviceType + ", onDemantPriceInr="
				+ onDemantPriceInr + ", disclaimer=" + disclaimer + ", onDemandImage=" + onDemandImage
				+ ", onDemandReview=" + onDemandReview + ", onDemandInfo=" + onDemandInfo + "]";
	}
	
	
	
}

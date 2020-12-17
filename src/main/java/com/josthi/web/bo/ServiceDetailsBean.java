package com.josthi.web.bo;


import java.math.BigDecimal ;
import java.util.List;

public class ServiceDetailsBean {
	private Integer id ;
	private String serviceName ;
	private String description ;
	private String active ;
	private String serviceType ;
	private String includedInPlan ;
	private String onDemandFlag ;
	private BigDecimal onDemantPriceInr ;
	private BigDecimal onDemantPriceUsd ;
	private String disclaimer ;
	private Integer sortOrder ;

	public Integer getId(){
		return id ;
	}
	public void setId(Integer id){
		this.id = id ;
	}
	public String getServiceName(){
		return serviceName ;
	}
	public void setServiceName(String serviceName){
		this.serviceName = serviceName ;
	}
	public String getDescription(){
		return description ;
	}
	public void setDescription(String description){
		this.description = description ;
	}
	public String getActive(){
		return active ;
	}
	public void setActive(String active){
		this.active = active ;
	}
	public String getServiceType(){
		return serviceType ;
	}
	public void setServiceType(String serviceType){
		this.serviceType = serviceType ;
	}
	public String getIncludedInPlan(){
		return includedInPlan ;
	}
	public void setIncludedInPlan(String includedInPlan){
		this.includedInPlan = includedInPlan ;
	}
	public String getOnDemandFlag(){
		return onDemandFlag ;
	}
	public void setOnDemandFlag(String onDemandFlag){
		this.onDemandFlag = onDemandFlag ;
	}
	public BigDecimal getOnDemantPriceInr(){
		return onDemantPriceInr ;
	}
	public void setOnDemantPriceInr(BigDecimal onDemantPriceInr){
		this.onDemantPriceInr = onDemantPriceInr ;
	}
	public BigDecimal getOnDemantPriceUsd(){
		return onDemantPriceUsd ;
	}
	public void setOnDemantPriceUsd(BigDecimal onDemantPriceUsd){
		this.onDemantPriceUsd = onDemantPriceUsd ;
	}
	public String getDisclaimer(){
		return disclaimer ;
	}
	public void setDisclaimer(String disclaimer){
		this.disclaimer = disclaimer ;
	}
	public Integer getSortOrder(){
		return sortOrder ;
	}
	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder ;
	}

}

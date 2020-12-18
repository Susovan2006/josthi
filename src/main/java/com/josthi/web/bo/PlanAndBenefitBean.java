package com.josthi.web.bo;

public class PlanAndBenefitBean {
	
	String serviceName;
	String serviceCode;
	String serviceDescription;
	String serviceCoveredByBasic;
	String serviceCoveredBySilver;
	String serviceCoveredByGold;
	String serviceDisclaimer;
	String ondemandService;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	public String getServiceCoveredByBasic() {
		return serviceCoveredByBasic;
	}
	public void setServiceCoveredByBasic(String serviceCoveredByBasic) {
		this.serviceCoveredByBasic = serviceCoveredByBasic;
	}
	public String getServiceCoveredBySilver() {
		return serviceCoveredBySilver;
	}
	public void setServiceCoveredBySilver(String serviceCoveredBySilver) {
		this.serviceCoveredBySilver = serviceCoveredBySilver;
	}
	public String getServiceCoveredByGold() {
		return serviceCoveredByGold;
	}
	public void setServiceCoveredByGold(String serviceCoveredByGold) {
		this.serviceCoveredByGold = serviceCoveredByGold;
	}
	
	
	public String getServiceDisclaimer() {
		return serviceDisclaimer;
	}
	public void setServiceDisclaimer(String serviceDisclaimer) {
		this.serviceDisclaimer = serviceDisclaimer;
	}
	public String getOndemandService() {
		return ondemandService;
	}
	public void setOndemandService(String ondemandService) {
		this.ondemandService = ondemandService;
	}
	@Override
	public String toString() {
		return "PlanAndBenefitBean [serviceName=" + serviceName + ", serviceCode=" + serviceCode
				+ ", serviceDescription=" + serviceDescription + ", serviceCoveredByBasic=" + serviceCoveredByBasic
				+ ", serviceCoveredBySilver=" + serviceCoveredBySilver + ", serviceCoveredByGold="
				+ serviceCoveredByGold + ", serviceDisclamer=" + serviceDisclaimer + "]";
	}
	
	

}

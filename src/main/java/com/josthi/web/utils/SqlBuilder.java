package com.josthi.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josthi.web.bo.ServiceRequestBean;

public class SqlBuilder {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SqlBuilder.class);
	
	public static String getTicketSearchCondition(ServiceRequestBean serviceRequestBean){
		String ticketNo = serviceRequestBean.getTicketNo().trim();
		String requestedBy = serviceRequestBean.getRequestedBy().trim();
		String requesterId = serviceRequestBean.getRequesterId().trim();
		String requestedFor = serviceRequestBean.getRequestedFor().trim();
		String beneficiaryId = serviceRequestBean.getBeneficiaryId().trim();
		String assignedTo = serviceRequestBean.getAssignedTo().trim();
		String serviceType = serviceRequestBean.getServiceType().trim();
		String serviceCategory = serviceRequestBean.getServiceCategory().trim();
		String serviceStatus = serviceRequestBean.getServiceStatus().trim();
		String lastUpdatedBy = serviceRequestBean.getLastUpdatedBy().trim();
		//String custType = serviceRequestBean.getCust_Type().trim();
		
		
		if(ticketNo!=null && ticketNo.length()>0){ticketNo="TICKET_NO='"+ticketNo+"' AND ";}		
		if(requestedBy!=null && requestedBy.length()>0){requestedBy="REQUESTED_BY='"+requestedBy+"' AND ";}		
		if(requesterId!=null && requesterId.length()>0){requesterId="REQUESTER_ID='"+requesterId+"' AND ";}
		if(requestedFor!=null && requestedFor.length()>0){requestedFor="REQUESTED_FOR='"+requestedFor+"' AND ";}
		if(beneficiaryId!=null && beneficiaryId.length()>0){beneficiaryId="BENEFICIARY_ID='"+beneficiaryId+"' AND ";}
		if(assignedTo!=null && assignedTo.length()>0){assignedTo="ASSIGNED_TO='"+assignedTo+"' AND ";}
		if(serviceType!=null && serviceType.length()>0){serviceType="SERVICE_TYPE='"+serviceType+"' AND ";}
		if(serviceCategory!=null && serviceCategory.length()>0){serviceCategory="SERVICE_CATEGORY='"+serviceCategory+"' AND ";}
		if(serviceStatus!=null && serviceStatus.length()>0){serviceStatus="SERVICE_STATUS='"+serviceStatus+"' AND ";}
		if(lastUpdatedBy!=null && lastUpdatedBy.length()>0){lastUpdatedBy="LAST_UPDATE_USER='"+lastUpdatedBy+"' AND ";}
		//if(custType!=null && custType.length()>0){custType="Cust_Type='"+lastUpdatedBy+"' AND ";}
		
		String searchCondition = ticketNo+requestedBy+requesterId+requestedFor+beneficiaryId+assignedTo+serviceType+serviceCategory+serviceStatus+lastUpdatedBy;
		
		if(searchCondition!=null && searchCondition.length()>0){
			searchCondition=searchCondition.substring(0, searchCondition.length()-4);
			logger.info("searchCondition:::::::::"+searchCondition);
			return "where "+searchCondition;
		}else{
			return " ";
		}
		
		//System.out.println("searchCondition ______>"+searchCondition);
		
		
	}

}

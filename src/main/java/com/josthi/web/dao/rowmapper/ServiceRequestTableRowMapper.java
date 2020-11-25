package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.utils.Utils;

public class ServiceRequestTableRowMapper implements RowMapper<ServiceRequestBean> {
	@Override
public ServiceRequestBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		ServiceRequestBean serviceRequestBean= new ServiceRequestBean() ;
		serviceRequestBean.setUid(resultSet.getInt("UID"));
		serviceRequestBean.setTicketNo(resultSet.getString("TICKET_NO"));
		serviceRequestBean.setRequestedBy(resultSet.getString("REQUESTED_BY"));
		serviceRequestBean.setRequesterId(resultSet.getString("REQUESTER_ID"));
		serviceRequestBean.setRequestedFor(resultSet.getString("REQUESTED_FOR"));
		serviceRequestBean.setBeneficiaryId(resultSet.getString("BENEFICIARY_ID"));
		serviceRequestBean.setRequestedVia(resultSet.getString("REQUESTED_VIA"));
		serviceRequestBean.setAssignedTo(resultSet.getString("ASSIGNED_TO"));
		serviceRequestBean.setRequestedOn(resultSet.getTimestamp("REQUESTED_ON"));
		//For Display Purpose.
		serviceRequestBean.setRequestedDate(Utils.timestampToFormattedString(resultSet.getTimestamp("REQUESTED_ON"), "d MMM yyyy"));
		serviceRequestBean.setToBeCompletedBy(resultSet.getTimestamp("TO_BE_COMPLETED_BY"));
		serviceRequestBean.setServiceType(resultSet.getString("SERVICE_TYPE"));
		serviceRequestBean.setServiceCategory(resultSet.getString("SERVICE_CATEGORY"));
		serviceRequestBean.setServiceReqDescription(resultSet.getString("SERVICE_REQ_DESCRIPTION"));
		serviceRequestBean.setServiceUrgency(resultSet.getString("SERVICE_URGENCY"));
		serviceRequestBean.setServiceStatus(resultSet.getString("SERVICE_STATUS"));
		serviceRequestBean.setLastUpdate(resultSet.getTimestamp("LAST_UPDATE"));
		serviceRequestBean.setFormattedLastUpdate(Utils.timestampToFormattedString(resultSet.getTimestamp("LAST_UPDATE"), "d MMM yyyy hh:mm aaa"));
		serviceRequestBean.setComments(resultSet.getString("COMMENTS"));
		serviceRequestBean.setLastCommentsNotes(resultSet.getString("LAST_UPDATE_COMMENTS"));
		serviceRequestBean.setLastUpdatedBy(resultSet.getString("LAST_UPDATE_USER"));
		return serviceRequestBean;
		}
	}

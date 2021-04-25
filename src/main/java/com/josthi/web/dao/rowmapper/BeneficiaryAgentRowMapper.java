package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.utils.Utils;

public class BeneficiaryAgentRowMapper implements RowMapper<AgentAssignmentBean> {

	@Override
	public AgentAssignmentBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		AgentAssignmentBean agentAssignmentBean = new AgentAssignmentBean();		
		agentAssignmentBean.setRelationId(resultSet.getString("RELATION_ID"));
		agentAssignmentBean.setHostUserName(resultSet.getString("HOST_NAME"));
		agentAssignmentBean.setHostUserId(resultSet.getString("CUSTOMER_ID"));
		agentAssignmentBean.setBeneficiaryName(resultSet.getString("BEN_NAME"));
		agentAssignmentBean.setBeneficiaryId(resultSet.getString("BENEFICIARY_ID"));
		agentAssignmentBean.setBeneficiaryPinCode(resultSet.getString("BEN_ZIP"));
		agentAssignmentBean.setAgentId(resultSet.getString("AGENT_ID"));
		agentAssignmentBean.setAgentName(resultSet.getString("AGENT_NAME"));
		agentAssignmentBean.setAgentPinCode(resultSet.getString("AGENT_ZIP"));
		agentAssignmentBean.setLastUpdateDate(Utils.timestampToFormattedString(resultSet.getTimestamp("UPDATE_DATE"), "d MMM yyyy"));
		agentAssignmentBean.setInsertDate(Utils.timestampToFormattedString(resultSet.getTimestamp("INSERT_DATE"), "d MMM yyyy"));
		
		return agentAssignmentBean;
	}

}

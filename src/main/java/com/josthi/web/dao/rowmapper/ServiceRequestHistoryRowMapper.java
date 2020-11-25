package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.ServiceRequestBean;
import com.josthi.web.bo.ServiceRequestHistoryBean;
import com.josthi.web.utils.Utils;

public class ServiceRequestHistoryRowMapper implements RowMapper<ServiceRequestHistoryBean> {
	@Override
public ServiceRequestHistoryBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		ServiceRequestHistoryBean serviceRequestHistoryBean= new ServiceRequestHistoryBean() ;
		serviceRequestHistoryBean.setHistoryId(resultSet.getInt("HISTORY_ID"));
		serviceRequestHistoryBean.setTicketNumber(resultSet.getString("TICKET_NUMBER"));
		serviceRequestHistoryBean.setStatus(resultSet.getString("STATUS"));
		serviceRequestHistoryBean.setComments(resultSet.getString("COMMENTS"));
		serviceRequestHistoryBean.setUpdateTimestamp(resultSet.getTimestamp("UPDATE_TIMESTAMP"));
		serviceRequestHistoryBean.setFormattedUpdateTimestamp(Utils.timestampToFormattedString(resultSet.getTimestamp("UPDATE_TIMESTAMP")));
		serviceRequestHistoryBean.setUpdatedByName(resultSet.getString("UPDATED_BY_NAME"));
		serviceRequestHistoryBean.setUpdatedById(resultSet.getString("UPDATED_BY_ID"));
		serviceRequestHistoryBean.setCounter(resultSet.getInt("COUNTER"));
		return serviceRequestHistoryBean;
		}
	}

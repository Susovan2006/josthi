package com.josthi.web.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.ServiceDetailsBean;

public class ServiceDetailRowMapper implements RowMapper<ServiceDetailsBean> {
	@Override
public ServiceDetailsBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		ServiceDetailsBean serviceDetailsBean= new ServiceDetailsBean() ;
		serviceDetailsBean.setId(resultSet.getInt("ID"));
		serviceDetailsBean.setServiceName(resultSet.getString("SERVICE_NAME"));
		serviceDetailsBean.setDescription(resultSet.getString("DESCRIPTION"));
		serviceDetailsBean.setActive(resultSet.getString("ACTIVE"));
		serviceDetailsBean.setServiceType(resultSet.getString("SERVICE_TYPE"));
		serviceDetailsBean.setIncludedInPlan(resultSet.getString("INCLUDED_IN_PLAN"));
		serviceDetailsBean.setOnDemandFlag(resultSet.getString("ON_DEMAND_FLAG"));
		serviceDetailsBean.setOnDemantPriceInr(resultSet.getBigDecimal("ON_DEMANT_PRICE_INR"));
		serviceDetailsBean.setOnDemantPriceUsd(resultSet.getBigDecimal("ON_DEMANT_PRICE_USD"));
		serviceDetailsBean.setDisclaimer(resultSet.getString("DISCLAIMER"));
		serviceDetailsBean.setSortOrder(resultSet.getInt("SORT_ORDER"));
		return serviceDetailsBean;
		}
	}

package com.josthi.web.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.ServiceDetailsBean;

public class ServicePriceDetailRowMapper implements RowMapper<ServiceDetailsBean> {
	@Override
public ServiceDetailsBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		ServiceDetailsBean serviceDetailsBean= new ServiceDetailsBean() ;
		serviceDetailsBean.setId(resultSet.getInt("ID"));
		serviceDetailsBean.setServiceName(resultSet.getString("SERVICE_NAME"));
		serviceDetailsBean.setIncludedInPlan(resultSet.getString("INCLUDED_IN_PLAN"));
		serviceDetailsBean.setOnDemantPriceInr(resultSet.getBigDecimal("ON_DEMANT_PRICE_INR"));
		serviceDetailsBean.setOnDemantPriceUsd(resultSet.getBigDecimal("ON_DEMANT_PRICE_USD"));		
		serviceDetailsBean.setDisclaimer(resultSet.getString("DISCLAIMER"));
		return serviceDetailsBean;
		}
	}

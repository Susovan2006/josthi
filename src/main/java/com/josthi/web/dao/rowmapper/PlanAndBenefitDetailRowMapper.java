package com.josthi.web.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.PlanAndBenefitBean;
import com.josthi.web.bo.ServiceDetailsBean;


public class PlanAndBenefitDetailRowMapper implements RowMapper<PlanAndBenefitBean> {
	@Override
public PlanAndBenefitBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		PlanAndBenefitBean planAndBenefitBean= new PlanAndBenefitBean() ;

		planAndBenefitBean.setServiceName(resultSet.getString("SERVICE_NAME"));
		planAndBenefitBean.setServiceDescription(resultSet.getString("DESCRIPTION"));
		planAndBenefitBean.setServiceCode(resultSet.getString("SERVICE_CODE"));
		
		planAndBenefitBean.setServiceCoveredByBasic((resultSet.getString("INCLUDED_IN_PLAN")).charAt(0)+"");
		planAndBenefitBean.setServiceCoveredBySilver((resultSet.getString("INCLUDED_IN_PLAN")).charAt(1)+"");
		planAndBenefitBean.setServiceCoveredByGold((resultSet.getString("INCLUDED_IN_PLAN")).charAt(2)+"");
		
		planAndBenefitBean.setOndemandService(resultSet.getString("ON_DEMAND_FLAG"));
		planAndBenefitBean.setServiceDisclaimer(resultSet.getString("DISCLAIMER"));
		return planAndBenefitBean;
		}
	}

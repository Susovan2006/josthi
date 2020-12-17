package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.RelationBean;
import com.josthi.web.utils.Utils;

public class RelationRowmapper implements RowMapper<RelationBean> {
	
	public RelationBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		RelationBean relationBean= new RelationBean() ;

		relationBean.setRelationId(resultSet.getString("RELATION_ID"));
		relationBean.setCustomerId(resultSet.getString("CUSTOMER_ID"));
		relationBean.setBeneficiaryId(resultSet.getString("BENEFICIARY_ID"));
		relationBean.setAgentId(resultSet.getString("AGENT_ID"));
		relationBean.setInsertDate(resultSet.getTimestamp("INSERT_DATE"));
		relationBean.setInsertDateStr(Utils.timestampToFormattedString(resultSet.getTimestamp("INSERT_DATE"),"d MMM yyyy"));
		relationBean.setUpdateDate(resultSet.getTimestamp("UPDATE_DATE"));
		relationBean.setUpdateDateStr(Utils.timestampToFormattedString(resultSet.getTimestamp("UPDATE_DATE"),"d MMM yyyy"));
		relationBean.setPlanName(resultSet.getString("PLAN_NAME"));
		relationBean.setPlanExpireDate(resultSet.getTimestamp("PLAN_EXPIRE_DATE"));
		if(relationBean.getPlanExpireDate()!=null)
		 relationBean.setPlanExpireDateStr(Utils.timestampToFormattedString(resultSet.getTimestamp("PLAN_EXPIRE_DATE"),"d MMM yyyy"));
	
		return relationBean;
		}

}

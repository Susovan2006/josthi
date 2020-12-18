package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;

public class BeneficiaryDropForPlanDownRowmapper implements RowMapper<DropDownBean> {
	
	public DropDownBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		DropDownBean dropDownBean= new DropDownBean() ;
		dropDownBean.setKeyId(resultSet.getString("BENEFICIARY_ID"));
		dropDownBean.setValue(resultSet.getString("FIRST_NAME") +" "+ resultSet.getString("LAST_NAME"));		
		return dropDownBean;
		}

}

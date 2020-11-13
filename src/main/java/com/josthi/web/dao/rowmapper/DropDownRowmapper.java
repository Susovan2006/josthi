package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;

public class DropDownRowmapper implements RowMapper<DropDownBean> {
	
	public DropDownBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		DropDownBean dropDownBean= new DropDownBean() ;
		dropDownBean.setTid(resultSet.getInt("TID"));
		dropDownBean.setDropDownType(resultSet.getString("DROPDOWN_TYPE"));
		dropDownBean.setKeyId(resultSet.getString("KEY_ID"));
		dropDownBean.setValue(resultSet.getString("DROPDOWN_VALUE"));
		dropDownBean.setActive(resultSet.getString("ACTIVE"));
		
		return dropDownBean;
		}

}

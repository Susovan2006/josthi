package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;

public class DropDownServiceDetailsRowmapper implements RowMapper<DropDownBean> {
	
	public DropDownBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		DropDownBean dropDownBean= new DropDownBean() ;
		dropDownBean.setKeyId(resultSet.getString("SERVICE_CODE"));//+"~"+resultSet.getString("SERVICE_NAME"));
		dropDownBean.setValue(resultSet.getString("SERVICE_NAME"));		
		return dropDownBean;
		}

}

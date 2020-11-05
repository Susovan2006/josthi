package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.bo.EmergencyContactBean;

public class EmergencyContactDetailsRowMapper implements RowMapper<EmergencyContactBean> {
	@Override
public EmergencyContactBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		EmergencyContactBean emergencyContactBean= new EmergencyContactBean() ;
		emergencyContactBean.setContactId(resultSet.getInt("CONTACT_ID"));
		emergencyContactBean.setPrimaryUid(resultSet.getString("PRIMARY_UID"));
		emergencyContactBean.setBenId(resultSet.getString("BEN_ID"));
		emergencyContactBean.setEmergencyContactName(resultSet.getString("EMERGENCY_CONTACT_NAME"));
		emergencyContactBean.setEmergencyContactNumber(resultSet.getString("EMERGENCY_CONTACT_NUMBER"));
		emergencyContactBean.setEmergencyContactStayLocation(resultSet.getString("EMERGENCY_CONTACT_STAY_LOCATION"));
		emergencyContactBean.setRelation(resultSet.getString("RELATION"));
		emergencyContactBean.setContactNoValidated(resultSet.getString("CONTACT_NO_VALIDATED"));
		emergencyContactBean.setInsertDate(resultSet.getTimestamp("INSERT_DATE"));
		emergencyContactBean.setUpdateDate(resultSet.getTimestamp("UPDATE_DATE"));
		emergencyContactBean.setNotes(resultSet.getString("NOTES"));
		return emergencyContactBean;
		}
	}

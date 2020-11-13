package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.po.UserDetailsPO ;
public class BeneficiaryDetailRowMapper implements RowMapper<BeneficiaryDetailBean> {

@Override
public BeneficiaryDetailBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		
		BeneficiaryDetailBean beneficiaryDetailBean= new BeneficiaryDetailBean() ;
		
		beneficiaryDetailBean.setUid(resultSet.getString("UID"));
		beneficiaryDetailBean.setFirstName(resultSet.getString("FIRST_NAME"));
		beneficiaryDetailBean.setMiddleName(resultSet.getString("MIDDLE_NAME"));
		beneficiaryDetailBean.setLastName(resultSet.getString("LAST_NAME"));
		beneficiaryDetailBean.setGender(resultSet.getString("GENDER"));	
		
		beneficiaryDetailBean.setUserAddressFirstLine(resultSet.getString("USER_ADDRESS_FIRST_LINE"));
		beneficiaryDetailBean.setUserAddressSecondLine(resultSet.getString("USER_ADDRESS_SECOND_LINE"));
		beneficiaryDetailBean.setNearestLandMark(resultSet.getString("NEAREST_LAND_MARK"));
		beneficiaryDetailBean.setCityTown(resultSet.getString("CITY_TOWN"));
		beneficiaryDetailBean.setState(resultSet.getString("STATE"));
		beneficiaryDetailBean.setCountyDistrict(resultSet.getString("COUNTY_DISTRICT"));
		beneficiaryDetailBean.setCountry(resultSet.getString("COUNTRY"));
		beneficiaryDetailBean.setZipPin(resultSet.getString("ZIP_PIN"));
		
		beneficiaryDetailBean.setMobileNo1(resultSet.getString("MOBILE_NO1"));
		beneficiaryDetailBean.setWhatsappNo(resultSet.getString("WHATSAPP_NO"));	
		beneficiaryDetailBean.setSecondaryEmail(resultSet.getString("SECONDARY_EMAIL"));
		beneficiaryDetailBean.setUserStatus(resultSet.getString("USER_STATUS"));
		
		//beneficiary_detail Table
		beneficiaryDetailBean.setTid(resultSet.getString("TID"));
		beneficiaryDetailBean.setCustomerID(resultSet.getString("CUSTOMER_ID"));
		beneficiaryDetailBean.setBeneficiaryID(resultSet.getString("BENEFICIARY_ID"));
		beneficiaryDetailBean.setRelationWithCustomer(resultSet.getString("RELATION_WITH_CUSTOMER"));
		beneficiaryDetailBean.setDateOfBirthInTimeStamp(resultSet.getTimestamp("DATE_OF_BIRTH"));
		beneficiaryDetailBean.setAge(resultSet.getString("AGE"));
		beneficiaryDetailBean.setHeight(resultSet.getString("HEIGHT"));
		beneficiaryDetailBean.setWeight(resultSet.getString("WEIGHT"));
		beneficiaryDetailBean.setBloodGroup(resultSet.getString("BLOOD_GROUP"));
		beneficiaryDetailBean.setPrefferedHospital(resultSet.getString("PREFFERED_HOSPITAL"));
		beneficiaryDetailBean.setMediclameInsuranceName(resultSet.getString("MEDICLAME_NAME"));
		beneficiaryDetailBean.setInsuranceRelatedNotes(resultSet.getString("INSURANCE_NOTE"));
		beneficiaryDetailBean.setHealthCondition(resultSet.getString("HEALTH_CONDITION"));
		beneficiaryDetailBean.setMedicalChallenges(resultSet.getString("MEDICAL_CHALLENGES"));
		
		
		return beneficiaryDetailBean;
		}
	}

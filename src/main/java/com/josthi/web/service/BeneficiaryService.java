package com.josthi.web.service;

import java.util.List;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.UserDetailsBean;

public interface BeneficiaryService {

	//void getBeneficiaryDetails(UserDetailsBean primaryBeneficiaryDetailsBean,
	//		UserDetailsBean secondaryBeneficiaryDetailsBean, String customerId) throws Exception;

	//UserDetailsBean geBeneficiaryDetails(String customerId, String beneficiaryType) throws Exception;

	List<BeneficiaryDetailBean> getBeneficiaryList(String customerId);

	boolean saveBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId, int nextId) throws Exception;

	void deleteBeneficiaryDetail(String beneficiaryID, String sessionCustomerId) throws Exception;

	BeneficiaryDetailBean getBeneficiaryDetailToEdit(String beneficiaryID, String customerId) throws Exception;

	boolean isValidBeneficiaryID(String beneficiaryID) throws Exception;

	boolean updateBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId) throws Exception;

	List<DropDownBean> getBloodGroup(String bloodGroup);

	BeneficiaryDetailBean getBeneficiaryDetailToView(String userID) throws Exception;

}

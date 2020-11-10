package com.josthi.web.service;

import java.util.List;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.UserDetailsBean;

public interface BeneficiaryService {

	void getBeneficiaryDetails(UserDetailsBean primaryBeneficiaryDetailsBean,
			UserDetailsBean secondaryBeneficiaryDetailsBean, String customerId) throws Exception;

	UserDetailsBean geBeneficiaryDetails(String customerId, String beneficiaryType) throws Exception;

	List<BeneficiaryDetailBean> getBeneficiaryList(String customerId);

	boolean saveBeneficiaryDetails(BeneficiaryDetailBean beneficiaryDetailBean, String custId, int nextId);

}

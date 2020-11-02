package com.josthi.web.service ;
import com.josthi.web.bo.UserDetailsBean ;

public interface UserDetailService {

	UserDetailsBean getUserDetails(String customerId);

	String updateUserDetails(UserDetailsBean userDetailsBean, String custId);

	void setDataToDisplay(UserDetailsBean userDetailsfromDb, UserDetailsBean userDetailsBean);
	
}

package com.josthi.web.dao ;
import java.util.List;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.bo.UserDetailsBean ;

public interface UserDetailsDao {

	UserDetailsBean getUserDetails(String customerId);
	boolean updateUserDetails(UserDetailsBean userDetailsBean, String custId);
}

package com.josthi.web.dao;

import com.josthi.web.bo.UserRegistrationBean;

public interface UserRegistrationDao {

	boolean insertIntoUserAuth(UserRegistrationBean userRegistrationBean);

	boolean insertIntoUserDetail(UserRegistrationBean userRegistrationBean);

	boolean updateNextUid(int i);

}

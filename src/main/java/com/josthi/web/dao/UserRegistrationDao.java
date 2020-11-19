package com.josthi.web.dao;

import com.josthi.web.bo.AgentRegistrationBean;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserRegistrationDao {

	boolean insertIntoUserAuth(UserRegistrationBean userRegistrationBean);

	boolean insertIntoUserDetail(UserRegistrationBean userRegistrationBean);

	boolean updateNextUid(int i);

	boolean insertIntoUserAuthForAgent(AgentRegistrationBean agentRegistrationBean);

	boolean insertIntoUserDetailForAgent(AgentRegistrationBean agentRegistrationBean);

	boolean insertIntoAgentEnquery(AgentRegistrationBean agentRegistrationBean);

	boolean insertUserDefaultPreference(String customerID) throws Exception;

}

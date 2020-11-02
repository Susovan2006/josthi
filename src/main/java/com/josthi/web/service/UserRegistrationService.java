package com.josthi.web.service;

import com.josthi.web.bo.AgentRegistrationBean;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserRegistrationService {

	boolean registerNewUser(UserRegistrationBean userRegistrationBean, int getNextID);
	
	boolean registerNewAgent(AgentRegistrationBean agentRegistrationBean, int getNextID);

}

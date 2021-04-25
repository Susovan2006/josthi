package com.josthi.web.dao;

import com.josthi.web.bo.AgentFeedbackBean;
import com.josthi.web.bo.JosthiFeedbackBean;

public interface FeedbackDao {

	boolean saveFeedbackAgent(AgentFeedbackBean agentFeedbackBean) throws Exception;

	boolean saveFeedbackJosthi(JosthiFeedbackBean josthiFeedbackBean) throws Exception;

}

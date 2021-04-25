package com.josthi.web.service;

import com.josthi.web.bo.AgentFeedbackBean;
import com.josthi.web.bo.JosthiFeedbackBean;

public interface FeedbackService {

	boolean saveFeedbackAgent(AgentFeedbackBean agentFeedbackBean) throws Exception;

	boolean saveFeedbackJosthi(JosthiFeedbackBean josthiFeedbackBean) throws Exception;

}

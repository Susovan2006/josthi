package com.josthi.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.AgentFeedbackBean;
import com.josthi.web.bo.JosthiFeedbackBean;
import com.josthi.web.dao.FeedbackDao;
import com.josthi.web.service.FeedbackService;



@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{
	
	
	@Autowired
	FeedbackDao feedbackDao;

	@Override
	public boolean saveFeedbackAgent(AgentFeedbackBean agentFeedbackBean) throws Exception {
		double overallRating = (double)((agentFeedbackBean.getProactiveness() + 
								agentFeedbackBean.getResponsibility() +
								agentFeedbackBean.getAvailability() +
								agentFeedbackBean.getBehavior() +
								agentFeedbackBean.getCareAndHandling())/5);
		
		agentFeedbackBean.setOverallRating(overallRating);
		
		
		return feedbackDao.saveFeedbackAgent(agentFeedbackBean);
	}

	@Override
	public boolean saveFeedbackJosthi(JosthiFeedbackBean josthiFeedbackBean) throws Exception {
		return feedbackDao.saveFeedbackJosthi(josthiFeedbackBean);
	}

}

package com.josthi.web.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.AgentFeedbackBean;
import com.josthi.web.bo.JosthiFeedbackBean;
import com.josthi.web.dao.FeedbackDao;

public class FeedbackDaoImpl implements FeedbackDao{
	
	private static final Logger logger = LoggerFactory.getLogger(FeedbackDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public static final String INSERT_AGENT_FEEDBACK = "INSERT INTO agentfeedback " + 
			"(FEEDBACK_FOR, FEEDBACK_BY, PROACTIVENESS, RESPONSIBILITY, AVAILABILITY, BEHAVIOR, CARE_AND_HANDLING, "
			+ "OVERALL_RATING, AREA_OF_IMPROVEMENT, OTHER_COMMENTS, FEEDBACK_DATE) " + 
			"VALUES( ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);";
	
	@Override
	public boolean saveFeedbackAgent(AgentFeedbackBean agentFeedbackBean) throws Exception {
		try {
			
			int result = jdbcTemplate.update(INSERT_AGENT_FEEDBACK,new Object[]{agentFeedbackBean.getFeedbackFor(),
					agentFeedbackBean.getFeedbackBy(),
					agentFeedbackBean.getProactiveness(),
					agentFeedbackBean.getResponsibility(),
					agentFeedbackBean.getAvailability(),
					agentFeedbackBean.getBehavior(),
					agentFeedbackBean.getCareAndHandling(),
					agentFeedbackBean.getOverallRating(),
					agentFeedbackBean.getAreaOfImprovement(),
					agentFeedbackBean.getOtherComments()});
			
			return (result > 0 ? true : false);
			
		}catch(Exception ex) {
			logger.error("Feedback DB error :", ex);
			throw ex;
		}
	}
	
	
	public static final String INSERT_JOSTHI_FEEDBACK = "INSERT INTO josthi_feedback" + 
			"(FEEDBACK_BY, SUGGESTIONS_NOTES, FEEDBACK_DATE) " + 
			"VALUES(?, ?, CURRENT_TIMESTAMP);";
	@Override
	public boolean saveFeedbackJosthi(JosthiFeedbackBean josthiFeedbackBean) throws Exception {
		try {
			
			int result = jdbcTemplate.update(INSERT_JOSTHI_FEEDBACK,new Object[]{josthiFeedbackBean.getFeedbackBy(),
					josthiFeedbackBean.getFeedbackNotes()
					});
			
			return (result > 0 ? true : false);
			
		}catch(Exception ex) {
			logger.error("Feedback DB error :", ex);
			throw ex;
		}
	}
	
	
	

}

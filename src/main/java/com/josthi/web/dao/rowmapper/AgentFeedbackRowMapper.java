package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.bo.AgentFeedbackBean;
public class AgentFeedbackRowMapper implements RowMapper<AgentFeedbackBean> {
	@Override
public AgentFeedbackBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		AgentFeedbackBean agentFeedbackBean= new AgentFeedbackBean() ;
		agentFeedbackBean.setFid(resultSet.getInt("FID"));
		agentFeedbackBean.setFeedbackFor(resultSet.getString("FEEDBACK_FOR"));
		agentFeedbackBean.setFeedbackBy(resultSet.getString("FEEDBACK_BY"));
		agentFeedbackBean.setProactiveness(resultSet.getBigDecimal("PROACTIVENESS"));
		agentFeedbackBean.setResponsibility(resultSet.getBigDecimal("RESPONSIBILITY"));
		agentFeedbackBean.setAvailability(resultSet.getBigDecimal("AVAILABILITY"));
		agentFeedbackBean.setBehavior(resultSet.getBigDecimal("BEHAVIOR"));
		agentFeedbackBean.setCareAndHandling(resultSet.getBigDecimal("CARE_AND_HANDLING"));
		agentFeedbackBean.setOverallRating(resultSet.getBigDecimal("OVERALL_RATING"));
		agentFeedbackBean.setAreaOfImprovement(resultSet.getString("AREA_OF_IMPROVEMENT"));
		agentFeedbackBean.setOtherComments(resultSet.getString("OTHER_COMMENTS"));
		agentFeedbackBean.setFeedbackDate(resultSet.getTimestamp("FEEDBACK_DATE"));
		return agentFeedbackBean;
		}
	}

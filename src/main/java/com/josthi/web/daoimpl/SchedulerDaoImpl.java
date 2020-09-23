package com.josthi.web.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.bo.SchedulerTimerBean;
import com.josthi.web.dao.SchedulerDao;

public class SchedulerDaoImpl implements SchedulerDao{

	private static final Logger logger = LoggerFactory.getLogger(SchedulerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	private static final String UPDATE_SCHEDULER_DASHBOARD= "UPDATE scheduled_task_dashboard SET TASK_NAME = ?,CRON_EXPRESSION = ?, LAST_RUN_TIME = ?, NEXT_RUN_TIME = ?, TASK_STATUS = ?  WHERE TASK_ID = ?";
	@Override
	public void updateSchedulerDashBoard(SchedulerTimerBean schedulerTimerBean) {
		logger.info(schedulerTimerBean.toString());
		jdbcTemplate.update(UPDATE_SCHEDULER_DASHBOARD, new Object[]{
										schedulerTimerBean.getTaskName(),
										schedulerTimerBean.getCronExpresion(), 
										schedulerTimerBean.getLastRunTime(),
										schedulerTimerBean.getNextRunTime(),
										schedulerTimerBean.getStatus(),
										schedulerTimerBean.getTaskId()});	
		
	}
	
	
	private static final String INSERT_SCHEDULER_HISTORY = "INSERT INTO scheduled_task_history (TASK_NAME,STATUS,RUN_TIME,ERROR_MESSAGE) VALUES(?, ? , ? , ?)";
	@Override
	public void insertSchedulerHistory(SchedulerTimerBean schedulerTimerBean) {
		jdbcTemplate.update(INSERT_SCHEDULER_HISTORY, new Object[]{
										schedulerTimerBean.getTaskName(), 
										schedulerTimerBean.getStatus(),
										schedulerTimerBean.getLastRunTime(),
										schedulerTimerBean.getErrorMessage()});
		
	}
	
	
	
	
	
}

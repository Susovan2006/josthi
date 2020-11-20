package com.josthi.web.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.dao.ServiceRequestDao;

public class ServiceRequestDaoImpl implements ServiceRequestDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceRequestDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

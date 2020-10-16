package com.josthi.web.daoimpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.po.EmailDbBean;

public class BaseDaoImpl {
	
    public JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}

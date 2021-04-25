package com.josthi.web.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.CacheConfigDao;
import com.josthi.web.dao.HistoryDao;
import com.josthi.web.dao.rowmapper.BeneficiaryDropDownRowmapper;
import com.josthi.web.dao.rowmapper.CacheConfigRowMapper;
import com.josthi.web.dao.rowmapper.DropDownRowmapper;
import com.josthi.web.po.CacheConfigPO;
import com.josthi.web.utils.Utils;

public class HistoryDaoImpl implements HistoryDao{
	
private static final Logger logger = LoggerFactory.getLogger(HistoryDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public static final String INSERT_ACTIVITY_LOG = "INSERT INTO activity_history_table "+
			" (ACTIVITY_FOR, ACTIVITY_BY, ACTIVITY_DATE_TIME, ACTIVITY_NOTE)" +
			" VALUES(?, ?, CURRENT_TIMESTAMP, ?)";
	@Override
	public boolean logActivityHistory(String activityFor, String activityBy, String activityNotes) throws Exception {
		try {
			int result = jdbcTemplate.update(INSERT_ACTIVITY_LOG,
					new Object[]{activityFor,
							activityBy,
							activityNotes});    
				
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	
}

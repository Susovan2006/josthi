package com.josthi.web.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.constants.Constant;
import com.josthi.web.dao.CacheConfigDao;
import com.josthi.web.dao.rowmapper.CacheConfigRowMapper;
import com.josthi.web.po.CacheConfigPO;
import com.josthi.web.utils.Utils;

public class CacheConfigDaoImpl implements CacheConfigDao{
	
private static final Logger logger = LoggerFactory.getLogger(CacheConfigDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	
	public static final String SELECT_CONFIG_DATA_FOR_CACHING = "SELECT VERBIAGE_ID, SCREEN_NAME, SCREEN_SECTION, SCREEN_KEY, VERBIAGE_SHORT_DESC, VERBIAGE_DETAIL_DESC" + 
														" FROM ui_verbiage where STATUS = ?;";
	@Override
	public List<CacheConfigPO> getConfigData() {
		
		
		
		final List<CacheConfigPO> cacheConfigBeanList = jdbcTemplate.query(SELECT_CONFIG_DATA_FOR_CACHING, 
																	new Object[] {Constant.STATUS_ACTIVE}, 
																	new CacheConfigRowMapper());
		
		
		return cacheConfigBeanList;
	}
	
	
	public static final String SELECT_HTML_SCREEN_NAME_FROM_CONFIG_DATA = "SELECT SCREEN_NAME FROM ui_verbiage where STATUS = 'ACTIVE' GROUP BY SCREEN_NAME";
	@Override
	public List<String> getScreenNames() {
		List<String> screenNameList = getJdbcTemplate().query(SELECT_HTML_SCREEN_NAME_FROM_CONFIG_DATA, 
								new RowMapper<String>(){
            						public String mapRow(ResultSet rs, int rowNum) 
                                         throws SQLException {
            								return rs.getString(1);
            							}
									});
		return screenNameList;
	}
	
	
	
	
}

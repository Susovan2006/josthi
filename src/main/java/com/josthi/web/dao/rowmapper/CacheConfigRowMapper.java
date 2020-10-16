package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.po.CacheConfigPO ;



public class CacheConfigRowMapper implements RowMapper<CacheConfigPO> {
	@Override
	public CacheConfigPO mapRow(ResultSet resultSet,int arg1)throws SQLException {
		CacheConfigPO cacheConfigPO= new CacheConfigPO() ;
		cacheConfigPO.setVerbiageId(resultSet.getInt("VERBIAGE_ID"));
		cacheConfigPO.setScreenName(resultSet.getString("SCREEN_NAME"));
		cacheConfigPO.setScreenSection(resultSet.getString("SCREEN_SECTION"));
		cacheConfigPO.setScreenKey(resultSet.getString("SCREEN_KEY"));
		cacheConfigPO.setVerbiageShortDesc(resultSet.getString("VERBIAGE_SHORT_DESC"));
		cacheConfigPO.setVerbiageDetailDesc(resultSet.getString("VERBIAGE_DETAIL_DESC"));
		//cacheConfigPO.setLastUpdateTimeStamp(resultSet.getTimestamp("LAST_UPDATE_TIME_STAMP"));
		//cacheConfigPO.setLastUpdateUser(resultSet.getString("LAST_UPDATE_USER"));
		//cacheConfigPO.setComments(resultSet.getString("COMMENTS"));
		return cacheConfigPO;
	}
}

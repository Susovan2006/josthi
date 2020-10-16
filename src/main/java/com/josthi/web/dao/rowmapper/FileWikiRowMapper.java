package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.po.FileWikiPO;

public class FileWikiRowMapper implements RowMapper<FileWikiPO> {
	@Override
public FileWikiPO mapRow(ResultSet resultSet,int arg1)throws SQLException {
		FileWikiPO fileWikiPO= new FileWikiPO() ;
		fileWikiPO.setFileId(resultSet.getInt("FILE_ID"));
		fileWikiPO.setFileName(resultSet.getString("FILE_NAME"));
		fileWikiPO.setFilePath(resultSet.getString("FILE_PATH"));
		fileWikiPO.setDownloadPath(resultSet.getString("DOWNLOAD_PATH"));
		fileWikiPO.setLinkedId(resultSet.getString("LINKED_ID"));
		fileWikiPO.setFileSize(resultSet.getString("FILE_SIZE"));
		fileWikiPO.setUploadTimestamp(resultSet.getTimestamp("UPLOAD_TIMESTAMP"));
		return fileWikiPO;
		}
	}

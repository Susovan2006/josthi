package com.josthi.web.daoimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.dao.FileWikiDao;
import com.josthi.web.dao.rowmapper.FileWikiRowMapper;
import com.josthi.web.po.FileWikiPO;

public class FileWikiDaoImpl implements FileWikiDao{

private static final Logger logger = LoggerFactory.getLogger(FileWikiDaoImpl.class);

	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	private static final String SELECT_WIKI_FILE_DETAILS = "Select FILE_ID,FILE_NAME,FILE_PATH,DOWNLOAD_PATH,LINKED_ID,FILE_SIZE,UPLOAD_TIMESTAMP from file_wiki order by UPLOAD_TIMESTAMP desc";
	@Override
	public List<FileWikiPO> getWikiFileDetails() {
		List<FileWikiPO> fileWikiList = jdbcTemplate.query(
				SELECT_WIKI_FILE_DETAILS,
                new FileWikiRowMapper());

        return fileWikiList;
	}
	
	private static final String INSERT_WIKI_FILE_DETAILS = "INSERT INTO file_wiki (FILE_NAME,FILE_PATH,DOWNLOAD_PATH,FILE_SIZE) VALUES (? , ? , ? , ?)";
	@Override
	public void insertFileWikiDetails(FileWikiPO fileWikiPO) {
		jdbcTemplate.update(INSERT_WIKI_FILE_DETAILS, new Object[]{fileWikiPO.getFileName(), fileWikiPO.getFilePath(), fileWikiPO.getDownloadPath(), fileWikiPO.getFileSize()});
		
	}
	
	
}

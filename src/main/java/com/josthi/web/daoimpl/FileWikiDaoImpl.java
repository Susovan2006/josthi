package com.josthi.web.daoimpl;

import java.nio.file.Path;
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
	
	
	
	private static final String UPDATE_USER_PROFILE_IMAGE = "UPDATE user_detail SET PROFILE_PIC_PATH = ? WHERE UID = ? ";
	@Override
	public void updateUserProfilePicDetails(Path targetLocation, String customerID, String emailID) {
		jdbcTemplate.update(UPDATE_USER_PROFILE_IMAGE, new Object[]{targetLocation.toString(),customerID});
		
	}
	
	
	private static final String SELECT_PROFILE_IMAGE_PATH_FOR_CUSTOMER_ID = "SELECT PROFILE_PIC_PATH FROM user_detail where UID = ?;";
	@Override
	public String getProfileImagePath(String customerId) {
		String imagePath = (String) jdbcTemplate.queryForObject(
				SELECT_PROFILE_IMAGE_PATH_FOR_CUSTOMER_ID, new Object[] { customerId }, String.class);

	    return imagePath;
	}
	
	
	
	@Override
	public boolean deleteProfileImage(String customerID) {
		int result = jdbcTemplate.update(UPDATE_USER_PROFILE_IMAGE, new Object[]{null,customerID});
	
		return (result > 0 ? true : false);
	}
	
	
}

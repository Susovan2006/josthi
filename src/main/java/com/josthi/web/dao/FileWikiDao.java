package com.josthi.web.dao;

import java.nio.file.Path;
import java.util.List;

import com.josthi.web.po.FileWikiPO;

public interface FileWikiDao {

	List<FileWikiPO> getWikiFileDetails();

	void insertFileWikiDetails(FileWikiPO fileWikiPO);

	void updateUserProfilePicDetails(Path targetLocation, String customerID, String emailID);

	String getProfileImagePath(String customerId);

	boolean deleteProfileImage(String customerID);

}

package com.josthi.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.josthi.web.po.FileWikiPO;

public interface FileWikiService {

	List<FileWikiPO> getWikiFileDetails();

	//String storeFile(MultipartFile file);

	List<FileWikiPO> saveFileInServer(MultipartFile file, FileWikiPO fileWikiPO);

	Resource loadFileAsResource(String fileName);

	String updateProfilePicture(MultipartFile file, String customerID, String emailID);

	File getProfileImageFile(String customerId) throws FileNotFoundException;

	String deleteProfilePicture(String customerID);

}

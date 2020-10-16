package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.po.FileWikiPO;

public interface FileWikiDao {

	List<FileWikiPO> getWikiFileDetails();

	void insertFileWikiDetails(FileWikiPO fileWikiPO);

}

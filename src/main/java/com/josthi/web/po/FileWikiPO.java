package com.josthi.web.po ;
import java.sql.Timestamp ;
public class FileWikiPO {
	private Integer fileId ;
	private String fileName ;
	private String filePath ;
	private String downloadPath ;
	private String linkedId ;
	private String fileSize ;
	private Timestamp uploadTimestamp ;


	public Integer getFileId(){
		return fileId ;
	}
	public void setFileId(Integer fileId){
		this.fileId = fileId ;
	}
	public String getFileName(){
		return fileName ;
	}
	public void setFileName(String fileName){
		this.fileName = fileName ;
	}
	public String getFilePath(){
		return filePath ;
	}
	public void setFilePath(String filePath){
		this.filePath = filePath ;
	}
	public String getDownloadPath(){
		return downloadPath ;
	}
	public void setDownloadPath(String downloadPath){
		this.downloadPath = downloadPath ;
	}
	public String getLinkedId(){
		return linkedId ;
	}
	public void setLinkedId(String linkedId){
		this.linkedId = linkedId ;
	}
	public String getFileSize(){
		return fileSize ;
	}
	public void setFileSize(String fileSize){
		this.fileSize = fileSize ;
	}
	public Timestamp getUploadTimestamp(){
		return uploadTimestamp ;
	}
	public void setUploadTimestamp(Timestamp uploadTimestamp){
		this.uploadTimestamp = uploadTimestamp ;
	}
}

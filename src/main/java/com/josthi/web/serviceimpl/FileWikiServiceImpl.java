package com.josthi.web.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.josthi.web.dao.FileWikiDao;
import com.josthi.web.exception.FileStorageException;
import com.josthi.web.exception.MyFileNotFoundException;
import com.josthi.web.po.FileWikiPO;
import com.josthi.web.service.FileWikiService;
import com.josthi.web.springconfig.FileStorageProperties;
import com.josthi.web.utils.Utils;

@Service("fileWikiService")
public class FileWikiServiceImpl implements FileWikiService{

	private final Path fileStorageLocation;
	
	@Autowired
	FileWikiDao fileWikiDao;
	
	@Autowired
    FileStorageProperties fileStorageProperties;
    
    public FileWikiServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	
	
	
	
	
	@Override
	public List<FileWikiPO> getWikiFileDetails() {
		List<FileWikiPO> editedFileWikiList =  new ArrayList<FileWikiPO>();		
		List<FileWikiPO> rawFileWikiList = fileWikiDao.getWikiFileDetails();
		for( FileWikiPO fileWikiObj : rawFileWikiList) {
			fileWikiObj.setDownloadPath(ServletUriComponentsBuilder.fromCurrentContextPath().path(fileWikiObj.getDownloadPath()).toUriString());
			editedFileWikiList.add(fileWikiObj);
		}
		
		return editedFileWikiList;
	}
	
	
	
	public List<FileWikiPO> saveFileInServer(MultipartFile file, FileWikiPO fileWikiPO) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            fileName = fileName.replaceAll("\\s+","_");
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            if(new File(targetLocation.toString()).exists()) {
	            //Creating the Object to be inserted.
	            
            	fileWikiPO.setFileName(fileName);
	            
            	fileWikiPO.setFilePath(targetLocation.toString());
	            
	            fileWikiPO.setDownloadPath("/downloadFile/"+fileName);
	            
	            long fileSize = FileUtils.sizeOf(new File(targetLocation.toString()));
	            fileWikiPO.setFileSize(Utils.readableFileSize(fileSize));
	            
	            fileWikiDao.insertFileWikiDetails(fileWikiPO);
            }
            
            return getWikiFileDetails();
            //return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
	
	public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

}

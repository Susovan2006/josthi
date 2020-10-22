package com.josthi.web.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.flywaydb.core.internal.resource.classpath.ClassPathResource;

@Service("fileWikiService")
public class FileWikiServiceImpl implements FileWikiService{

	
	private static final Logger logger = LoggerFactory.getLogger(FileWikiServiceImpl.class);
			
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
	
	
	//Called when a file is being uploaded.
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


	/**
	 *  ********************************************************************************************
	 *                    U S E R     P R O F I L E     P I C T U R E      U P D A T E
	 *  ********************************************************************************************
	 */
	@Override
	public String updateProfilePicture(MultipartFile file, String customerID, String emailID) {
		// Normalize file name
         String profilePicFileName = StringUtils.cleanPath(file.getOriginalFilename());
         try {
             // Check if the file's name contains invalid characters
             if(profilePicFileName.contains("..")) {
                 throw new FileStorageException("Sorry! Filename contains invalid path sequence " + profilePicFileName);
             }

             profilePicFileName = profilePicFileName.replaceAll("\\s+","_").toUpperCase();
             // Copy file to the target location (Replacing existing file with the same name)
             
             String profilePath =  this.fileStorageLocation.toString()+File.separator+customerID;
             
             if(!(new File(profilePath).exists())) {
            	 FileUtils.forceMkdir(new File(profilePath));
             }
             
             String fullImagePath = "";
             if(profilePicFileName.contains("JPG") || profilePicFileName.contains("JPEG")) {
            	 fullImagePath = profilePath+File.separator+customerID+".jpg";
             }else if (profilePicFileName.contains("PNG")) {
            	 fullImagePath = profilePath+File.separator+customerID+".png";
             }else if (profilePicFileName.contains("GIF")) {
                	 fullImagePath = profilePath+File.separator+customerID+".gif";
             }else {
            	 throw new FileStorageException("Invalid File Format. Try JPG/PNG/GIF");
             }
             
            	 
            	 
             Path targetLocation = Paths.get(fullImagePath);
             
             Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

             if(new File(targetLocation.toString()).exists()) {	            
 	            fileWikiDao.updateUserProfilePicDetails(targetLocation, customerID, emailID);
             }
             
             return "Successfully Uploaded.";
             //return fileName;
         } catch (IOException ex) {
             throw new FileStorageException("Could not store file " + profilePicFileName + ". Please try again!", ex);
         }
	}





	@Override
	public File getProfileImageFile(String customerId) throws FileNotFoundException {
		 String imagePath = fileWikiDao.getProfileImagePath(customerId);
		 
		 File serverFile = null;
	    	
	    	if(imagePath!=null && imagePath.length()>0) {
	    		serverFile = new File(imagePath);
	    		if(!(serverFile.exists())) {     							//If Image doesn't exists
	    			serverFile = Utils.getDefaultProfileImages();
	    		}
	    	}else if(imagePath==null || org.apache.commons.lang3.StringUtils.isBlank(imagePath)) {   //if there is nothing in the database
	    		serverFile = Utils.getDefaultProfileImages();
	    	}
	    	
	    	return serverFile;
	}

}

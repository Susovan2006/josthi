package com.josthi.web.controller.rest;

//import com.example.filedemo.payload.UploadFileResponse;
//import com.example.filedemo.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.josthi.web.service.FileStorageService;
import com.josthi.web.service.FileWikiService;
import com.josthi.web.serviceimpl.FileStorageServiceImpl;
import com.josthi.web.serviceimpl.UploadFileResponse;
import com.josthi.web.utils.ResizeImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProfilePictureController {

    private static final Logger logger = LoggerFactory.getLogger(ProfilePictureController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
	FileWikiService fileWikiService;

    //@CrossOrigin("*") //TODO : need to remove before prod deployment.
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/userProfilePic", method = RequestMethod.POST)
    public ResponseEntity<String> userProfilePic(@RequestParam("file") MultipartFile file,
    		@RequestParam("customerID") String customerID,
    		@RequestParam("emailID") String emailID) {
    	
    	
    	logger.info("Profile Pic Change request for :"+customerID);
        try {
        	
        	if(file.isEmpty()) {
        		return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
        	}
        	
        	String profileImageLocation = fileWikiService.updateProfilePicture(file, customerID, emailID);
        	
        	String result = ResizeImage.resizeProfileImage(profileImageLocation);
      	 
        	return new ResponseEntity<String>("Successfully uploaded the profile pic, please do a refresh." +
        			file.getOriginalFilename(), HttpStatus.OK);
        	
        }catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        	return new ResponseEntity<String>("Exception Occured, Try again",HttpStatus.BAD_REQUEST);
        }
    
    }
    
    
    @CrossOrigin("*") //TODO : need to remove before prod deployment.
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteProfilePic", method = RequestMethod.POST)
    public ResponseEntity<String> deleteProfilePic(
    		@RequestParam("customerID") String customerID) {
    		
    	logger.info("Profile Pic Delete request for :"+customerID);
        try {    	
        	String status = fileWikiService.deleteProfilePicture(customerID);        	    	 
        	return new ResponseEntity<String>(status , HttpStatus.OK);        	
        }catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        	return new ResponseEntity<String>("Exception Occured while deleting the image, Try again later",HttpStatus.BAD_REQUEST);
        }
    
    }
    
    
    
    
        
    
    
    //---------------------------------------------------------------------
    @RequestMapping("/viewUserPhoto")
    public void viewUserPhoto( 
        @RequestParam String customerID,@RequestParam String picType,
        HttpServletResponse response) throws IOException
    {
    	
        OutputStream outputStream = null;
        InputStream in = null;
        try {
        	String filename = "";//fileUploadService.getCustomerPhoto(customerID,picType);
            in = new FileInputStream(filename); // I assume files are at /tmp
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            response.setContentType("image/jpg");
            response.setHeader(
                "Content-Disposition",
                "attachment;filename=\"" + filename + "\"");
            outputStream = response.getOutputStream();
            while( 0 < ( bytesRead = in.read( buffer ) ) )
            {
                outputStream.write( buffer, 0, bytesRead );
            }
        }catch(FileNotFoundException ex){
        	logger.error(ex.getMessage());
        }catch(Exception ex){
        	logger.error(ex.getMessage(),ex);
        }
        finally
        {
            if ( null != in )
            {
                in.close();
            }
        }

    }
    
    
    
    
    @PostMapping("/userProfilePicSample")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
    		@RequestParam("customerID") String customerID,
    		@RequestParam("emailID") String emailID) {
        String fileName = fileStorageService.storeFile(file);
        logger.info("********************************************************************");
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    

    @GetMapping("/downloadFile_____/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

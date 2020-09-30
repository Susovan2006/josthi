package com.josthi.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.josthi.web.po.FileWikiPO;
import com.josthi.web.service.FileWikiService;
import com.josthi.web.serviceimpl.UploadFileResponse;





@Controller
public class FileWikiController {
	
	
	@Autowired
	FileWikiService fileWikiService;
	
	private static final Logger logger = LoggerFactory.getLogger(FileWikiController.class);
	
	@GetMapping("/admin/wiki")
	public String wiki(FileWikiPO fileWikiPO,  Model model) {

		model.addAttribute("fileWikiList",fileWikiService.getWikiFileDetails());
		return "wiki";
	}
	
	
	@PostMapping("/uploadFileInWiki")
    public String uploadFileInWiki(@RequestParam("file") MultipartFile file,
    											FileWikiPO fileWikiPO, Model model) {
		
		List<FileWikiPO> fileWikiPOList = fileWikiService.saveFileInServer(file, fileWikiPO);

		model.addAttribute("fileWikiList",fileWikiPOList);

        return "wiki";
    }
	
	
	
	
	@GetMapping("/downloadFile/{fileName:.+}")
	@ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        
		
		logger.info("---------File Download Called :"+fileName+"-------------");
		// Load file as Resource
        Resource resource = fileWikiService.loadFileAsResource(fileName);

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

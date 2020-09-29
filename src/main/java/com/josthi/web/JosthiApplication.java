package com.josthi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.josthi.web.springconfig.FileStorageProperties;


@EnableScheduling
@SpringBootApplication

//The Below Configuration is for FileUpload
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class JosthiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JosthiApplication.class, args);
	}

}

package com.josthi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JosthiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JosthiApplication.class, args);
	}

}

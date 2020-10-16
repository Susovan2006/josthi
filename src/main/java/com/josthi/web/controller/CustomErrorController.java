package com.josthi.web.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

 private static final String PATH = "/error";

 //Tips : need to make the below settings in Application.properties
 //server.error.whitelabel.enabled=false
 
 
 @RequestMapping(value = PATH)
 public String error() {
  return "customError";
 }

 @Override
 public String getErrorPath() {
  return PATH;
 }
}

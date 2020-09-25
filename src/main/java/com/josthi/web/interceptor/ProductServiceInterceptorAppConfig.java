package com.josthi.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Component
public class ProductServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {
   @Autowired
   ProductServiceInterceptor productServiceInterceptor;
   
   @Autowired
   MainInterceptor mainInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      //registry.addInterceptor(productServiceInterceptor);
      //registry.addInterceptor(mainInterceptor);
   }
}
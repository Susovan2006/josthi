package com.josthi.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class MainInterceptor extends  HandlerInterceptorAdapter{

	/**
	 * preHandle() method − This is used to perform operations before sending the 
	 * request to the controller. This method should return true to return the 
	 * response to the client.
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
			System.out.println("---- PRE HANDLER CALLED----:"+request.getServletPath());
	        // ignore login page
	        if (request.getServletPath() == "/") { // BEWARE : to be adapted to your actual login page
	            return true;
	        }

	       // Users user=(Users) session.getAttribute("user");
	        String user = (String) request.getSession().getAttribute("LOGGEDIN_USER");
	        if(user == null)
	        {
	            System.err.println("Request Path : ");
	            response.sendRedirect("/");
	            return false;
	        }
	        else
	        {
	            return true;
	        }
	    }
}

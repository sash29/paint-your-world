package org.launchcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.launchcode.pyw.controllers.AbstractController;
import org.launchcode.pyw.models.User;
import org.launchcode.pyw.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;

    
    @Override
    public boolean preHandle(HttpServletRequest request,  HttpServletResponse response, Object handler) throws IOException {
    	
    	String[] a = new String [] {"paintroom", "ContactUs"};
        List<String> authPages = Arrays.asList(a);
        System.out.println("preHandle");
        System.out.println(request.getRequestURI());

        // Require log-in for auth pages
        if ( authPages.contains(request.getRequestURI()) ) {

        	boolean isLoggedIn = false;
        	
        	//get session frm request, then get uid frm that session, then convert into Integer and assign to variable
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);

            if (userId != null) { 
            User user = userDao.findByUid(userId);
            	
            	if (user != null) { // if particular user exists in db
            		isLoggedIn = true;
            	}
            }

            
            if (!isLoggedIn) {// If user not logged in, redirect to login page
            	//model.addAttribute("error","You have to be logged in to proceed further");
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }

}

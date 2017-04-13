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
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;

    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        List<String> authPages = Arrays.asList("/paintroom");

        // Require sign-in for auth pages
        if ( authPages.contains(request.getRequestURI()) ) {

        	boolean isLoggedIn = false;
        	User user;
        	//get session frm request, then get uid frm that session, then convert it into Integer and assign to variable
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);

            if (userId != null) {
            	user = userDao.findByUid(userId);
            	
            	if (user != null) {
            		isLoggedIn = true;
            	}
            }

            // If user not logged in, redirect to login page
            if (!isLoggedIn) {
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }

}

package org.launchcode.pyw.controllers;


import javax.servlet.http.HttpSession;

import org.launchcode.pyw.models.User;
import org.launchcode.pyw.models.dao.PaintedRoomDao;
import org.launchcode.pyw.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
    protected UserDao userDao;
	
	@Autowired
	protected PaintedRoomDao PaintedRoomDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
    
    protected Boolean isUserLoggedIn(HttpSession session){
    	Boolean isloggedin = false;
    	Integer userId = (Integer) session.getAttribute(userSessionKey);
    	 if(userId == null){
    		 isloggedin =  false;
    	 }else{
    		 isloggedin = true;
    	 }
    	 return isloggedin;
    }
	
}


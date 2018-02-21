package org.launchcode.pyw.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.pyw.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	private String nlg_str = "Not logged in";
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup get parameters from request
		model.addAttribute("logged_user",nlg_str);
		String usrName = request.getParameter("username");
		String pswd = request.getParameter("password");
        String cnfirmPswd = request.getParameter("verify");
		
		boolean validUser = User.isValidUsername(usrName);
   		boolean validPswd =  User. isValidPassword(pswd);
  		boolean verifiedPswd = cnfirmPswd.equals(pswd);
  		
		//validate parameters(username, password, verify)
  	    //verify that username and password are valid, they both pass as "acceptable"  and that the pair of passwords are same.
  		//if they validate, create new user, & put them in the session(use function frm AbstractController)
  		if(!validUser)
  		{
  		   model.addAttribute("error","pls give a valid username");
  		   return "signup";
  		}
  		else if(!validPswd)
  		{
  		   model.addAttribute("error","pls give a valid pasword");
  		   model.addAttribute("logged_user",usrName);
  		   return "signup";
  		}
  		else if(!verifiedPswd){
  		   model.addAttribute("verify_error","pls repeat the original password");
  		   model.addAttribute("logged_user",usrName);
  		   return "signup";
  		}
  		else{
  		   User newUser = new User(usrName,pswd);
  		   userDao.save(newUser);// save in database
  		   HttpSession thisSession = request.getSession();
  		   setUserInSession(thisSession,  newUser);
  		   model.addAttribute("login_user",newUser);
  	       model.addAttribute("logged_user",usrName);
  	    }
  													
  		return "redirect:/paintselect";
  	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		System.out.println("login requested");
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		//get parameters from request
		model.addAttribute("logged_user",nlg_str);
		String givenUsrname = request.getParameter("username");
		User member = userDao.findByUsername(givenUsrname);//get user by their username
		String givenPswd = request.getParameter("password");
		String redirectStr = "";
		System.out.println("username and password are "+givenUsrname +" " +givenPswd);
		
		if (!User.isValidUsername(givenUsrname))//check if username is correct
		{ 
		
			model.addAttribute("login_error","This user doesnt exist! Please signup or enter existing user.");

            redirectStr= "/login";
		}
		else 
		{
           
			if (member.isMatchingPassword(givenPswd)) //check if password is correct
			{ 
			   HttpSession currentSession = request.getSession();
		       setUserInSession(currentSession,  member);// if so,log him in(setting the user in session)
		       model.addAttribute("logged_user",givenUsrname);
		       redirectStr = "redirect:/paintselect";
			}
			else
			{
				model.addAttribute("login_error","This password doesnt match! Please signup or enter correct password.");
				redirectStr= "/login";
			}
		}
		
        System.out.println(model.toString());  
		return redirectStr ;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,Model model){
        request.getSession().invalidate();
        model.addAttribute("logged_user",nlg_str);
		return "redirect:paintselect";
	}
	@RequestMapping(value = "/AboutUs&ContactUs", method = RequestMethod.GET)
	public String aboutUs() {
		System.out.println("historyNcontact requested");
		return "AboutUs&ContactUs";
	}
}

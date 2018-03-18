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
	private String nlg_str = "No User logged in";
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		 //model.addAttribute("logged_user",nlg_str);
		String usrName = request.getParameter("username");
		String pswd = request.getParameter("password");
        String cnfirmPswd = request.getParameter("verify");
		
		boolean validUser = User.isValidUsername(usrName);
   		boolean validPswd =  User.isValidPassword(pswd);
  		boolean verifiedPswd = cnfirmPswd.equals(pswd);
  		
  	//verify that username and password are valid, and that the pair of passwords are same.  		
  		if(!validUser)
  		{  
  			model.addAttribute("username_error","username has to be 4-11 characters long");
  		   return "signup";
  		}
  		else if(!validPswd)
  		{
  			model.addAttribute("password_error","password has to be 4-11 characters long");
  		   return "signup";
  		}
  		else if(!verifiedPswd){
  		   model.addAttribute("verify_error","pls repeat the above password");
  		   return "signup";
  		}
  		else{ //if they validate, create new user, & put them in the session(use function frm AbstractController)
  		   User newUser = new User(usrName,pswd);
  		   userDao.save(newUser);// save in database
  		   HttpSession thisSession = request.getSession();
  		   setUserInSession(thisSession,  newUser);
  		  // model.addAttribute("login_user",newUser);
  	      // model.addAttribute("logged_user",usrName);
  	     return "redirect:/paintselect";
  	    }
  		  	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		System.out.println("login requested");
		return "login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
						
		String givenUsrname = request.getParameter("username");//get username parameter from request
		String givenPswd = request.getParameter("password");   //get password from request
		String redirectStr = "";
		System.out.println("username and password are "+givenUsrname +" " +givenPswd);
				  
		User user1 = userDao.findByUsername(givenUsrname);//retrieve user frm db by the username
		if(user1 == null){
			model.addAttribute("login_error","Username does not exist! Signup or enter existing user details");
            return "/login";
		}else{
			boolean matchingPswd = user1.isMatchingPassword(givenPswd);// match its recorded password with pswd given by user

			if(matchingPswd){
				model.addAttribute("username", givenUsrname);
				model.addAttribute("password", givenPswd);
				setUserInSession (request.getSession(),user1);//loggedin session starts
				return "redirect:/paintselect";
				}
			else{
				model.addAttribute("login_error","Password supplied is not correct! Signup or enter existing user details");
				return "/login";
				}
			}
		}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,Model model){
        request.getSession().invalidate();
        model.addAttribute("logged_user",nlg_str);
		return "redirect:paintselect";
	}
	@RequestMapping(value = "/ContactUs", method = RequestMethod.GET)
	public String aboutUs() {
		//System.out.println("historyNcontact requested");
		return "ContactUs";
	}
}

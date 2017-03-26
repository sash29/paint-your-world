package org.launchcode.pyw.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.pyw.models.PaintedRoom;
import org.launchcode.pyw.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaintedRoomController extends AbstractController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String tohome() {
		return "paintselect";
	}
	
	@RequestMapping(value = "paintselect", method = RequestMethod.GET)
	public String topaintselect(HttpServletRequest request, Model model) {
		return "paintselect";
	}
	
	@RequestMapping(value = "paintselect", method = RequestMethod.POST)
	public String paintselected(HttpServletRequest request, Model model) {
		
		//get request parameters
		String selrm = request.getParameter("selroom") ;
		String selcol = request.getParameter("selcolor") ;

		HttpSession thisSession = request.getSession();
		Boolean isloggedin = isUserLoggedIn(thisSession);
		String retStr = "paintselect";

		if(isloggedin){
			System.out.println("paintselect : " + selrm);
			System.out.println("paintselect : " + selcol);
			thisSession.putValue("selcolor", selcol);
			thisSession.putValue("selroom", selrm);
			
			model.addAttribute("selcolor", selcol);
			model.addAttribute("selroom", selrm);
			retStr = "redirect:/paintroom";
		}else{
			model.addAttribute("status_msg","You must be logged in.");
		}
		return retStr;
		
	}
	
	@RequestMapping(value = "paintroom", method = RequestMethod.GET)
	public String topaintroom(HttpServletRequest request, Model model) {
		HttpSession thisSession = request.getSession();
		
		String selrm = thisSession.getValue("selroom").toString() ;
		String selcol =  thisSession.getValue("selcolor").toString() ;
		System.out.println("paintselect : " + selrm);
		System.out.println("paintselect : " + selcol);
		model.addAttribute("selcolor", selcol);
		model.addAttribute("selroom", selrm);
		System.out.println(model.toString());
		return "paintroom";
	}
	
	@RequestMapping(value = "paintroom", method = RequestMethod.POST)
	public String roomdesigned(HttpServletRequest request, Model model) {

		// get the user
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession( thisSession);
	
		String imgururl = request.getParameter("imgururl") ;
		String selcol = request.getParameter("selcolor") ;
		String selrm = request.getParameter("selroom") ;
		
		PaintedRoom prm = new PaintedRoom(selrm,imgururl,author);
		PaintedRoomDao.save(prm);
		return "paintselect";
	}
	
	@RequestMapping(value = "rooms", method = RequestMethod.GET)
	public String viewrooms(HttpServletRequest request, Model model) {
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession( thisSession);
		
		
		ArrayList<Map> rmList = new ArrayList();
		
		List<PaintedRoom> prml = author.getPaintedRooms();
		
		for(Iterator<PaintedRoom> prl=prml.iterator();prl.hasNext();){
			PaintedRoom pr = prl.next();
			String rm = pr.getroomname();
			String imgururl = pr.getimgururl();
			Map<String, String>prmmap= new HashMap<String, String>();
			prmmap.put("rm",rm);
			prmmap.put("imgururl", imgururl);
			System.out.println(prmmap.toString());
			rmList.add(prmmap);
		}
		System.out.println(rmList.toString());
		model.addAttribute("rooms",rmList);
		
		return "rooms";
	}
	
}
	/*
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		//get all of the user's posts
		User correspondingUsr = userDao.findByUsername(username);
		List<Post> allPosts= correspondingUsr.getPosts();
		//pass the posts into the template
		model.addAttribute("posts",allPosts);
		
		return "blog";
	}
	*/
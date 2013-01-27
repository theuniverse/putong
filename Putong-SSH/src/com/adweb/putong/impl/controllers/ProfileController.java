package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.services.IFriendService;
import com.adweb.putong.core.services.IRecordService;

@Controller
public class ProfileController extends BaseController{
	
	@Autowired
	private IRecordService recordService;
	
	@Autowired
	private IFriendService friendService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showMyProfile(Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser self = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null || host == null || !host.equals(request.getRemoteAddr())) {
			try {
				String url = String.format("?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String username = self.getUsername();
		
		List<IRecord> history = recordService.list(username, username, null, null);
		model.addAttribute("history", history);
		model.addAttribute("user",self);
		return "profile";		
	}
	
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String showProfile(@PathVariable("id") String target, Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser self = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null  || host == null || !host.equals(request.getRemoteAddr())) {
			try {
				String url = String.format("../?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String username = self.getUsername();
		IUser user= accountService.userinfo(username, target);
		if(user == null) {
			try {
				String url = String.format("../error/404");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<IRecord> history = recordService.list(username, target, null, null);
		Boolean friend = friendService.isFollowerOfTarget(username, target);
		
		model.addAttribute("history", history);
		model.addAttribute("friend", friend);
		model.addAttribute("user", user);
		return "profile";		
	}
	
	@RequestMapping(value = "/profile/{id}/friends", method = RequestMethod.GET)
	public String showFriend(@PathVariable("id") String target, Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser self = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null  || host == null || !host.equals(request.getRemoteAddr())) {
			try {
				String url = String.format("../../?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String username = self.getUsername();
		IUser user= accountService.userinfo(username, target);
		if(user == null) {
			try {
				String url = String.format("../../error/404");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<IUser> followeds = friendService.follower(username, target, null, null);
		List<IUser> followers = friendService.following(username, target, null, null);
		Boolean friend = friendService.isFollowerOfTarget(username, target);
		
		model.addAttribute("followers", followers);
		model.addAttribute("followeds", followeds);
		model.addAttribute("friend", friend);
		model.addAttribute("user", user);
		return "friend";		
	}
	
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public String showMyFriend(Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser self = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null  || host == null || !host.equals(request.getRemoteAddr())) {
			try {
				String url = String.format("?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String username = self.getUsername();		
		List<IUser> followeds = friendService.follower(username, username, null, null);
		List<IUser> followers = friendService.following(username, username, null, null);
		
		model.addAttribute("followers", followers);
		model.addAttribute("followeds", followeds);
		
		model.addAttribute("user", self);
		return "friend";		
	}
	
}

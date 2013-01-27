package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IUser;

@Controller
public class SettingController extends BaseController {

	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String showiSetting(Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser self = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null  || host == null || !host.equals(request.getRemoteAddr())) {
			// TODO deal with the not login problem
			try {
				String url = String.format("?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//List<IRecordEvent> newthings = eventService.listNewthings(user.getUsername(), null, null);
		model.addAttribute("user", self);
		return "setting";		
	}
	
	@RequestMapping(value = "/setting/apply", method = RequestMethod.POST)
	public String update(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// validate and authenticate json request
		IUser self = (IUser)request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (self == null  || host == null || !host.equals(request.getRemoteAddr())) {
			// TODO deal with the not login problem
			try {
				String url = String.format("../../?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//String username = request.getParameter("username");
		String password = request.getParameter("password");
		//System.out.println("[SETTING]"+username+","+password);
		if(password == null || !password.equals(self.getPassword())) {
			// TODO deal with the unauthenticated problem
			try {
				String url = String.format("?error=%d",801);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String new_password = request.getParameter("new-password");
		String new_nickname = request.getParameter("nickname");
		String new_avatar = null;
		String new_email = request.getParameter("email");
		Integer gender = Integer.parseInt(request.getParameter("gender"));
		Boolean new_gender = null;
		switch(gender){
		case 0 : new_gender = true; break;
		case 1 : new_gender = false; break;
		case 2 : new_gender = null;
		}
		//Boolean new_gender = request.getParameter("gender").equals("0") ? true : false;
		String new_bio = request.getParameter("bio");
		System.out.println("[GENDER]"+request.getParameter("gender")+",[SEEN]"+request.getParameter("seen"));
		Boolean new_seen = request.getParameter("seen").equals("0") ? true : false;

		// call services
		// TODO change the interface
		boolean result = accountService.update(self.getUsername(), new_password, new_nickname, new_avatar, new_email, new_bio, new_gender, new_seen);
		if(result) {
			
			IUser updatedUser = accountService.userinfo(self.getUsername(), self.getUsername());
			request.getSession().setAttribute("self", updatedUser);
			
			try {
				String url = String.format("");
				response.sendRedirect(url);
				return "success";
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		try {
			String url = String.format("?error=%d",800);
			response.sendRedirect(url);
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "error";

	}
	
}
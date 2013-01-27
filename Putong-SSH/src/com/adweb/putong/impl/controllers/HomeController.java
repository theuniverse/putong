package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.events.IRecordEvent;
import com.adweb.putong.core.services.IEventService;

@Controller
public class HomeController extends BaseController{
	
	@Autowired
	private IEventService eventService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHome(Model model, HttpServletRequest request, HttpServletResponse response) {
		IUser user = (IUser) request.getSession().getAttribute("self");
		String host = (String) request.getSession().getAttribute("host");
		if (user == null  || host == null || !host.equals(request.getRemoteAddr())) {
			try {
				String url = String.format("");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<IRecordEvent> newthings = eventService.listNewthings(user.getUsername(), null, null);
		model.addAttribute("things", newthings);
		return "home";		
	}
	
}

package com.adweb.putong.impl.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController extends BaseController{
	
	private static final String ERROR = "error/error";
	
	@RequestMapping(value = "/error/{id}", method = RequestMethod.GET)
	public String activate(@PathVariable String id, HttpServletRequest request, Model model) {
		System.out.println(id);
		
		String username = request.getParameter("username");
		if(username != null)
			model.addAttribute("username", username);
			
		model.addAttribute("errorCode", id);
		return ERROR+"-"+id;
	}
}

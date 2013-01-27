package com.adweb.putong.impl.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.services.ISysParamService;

@Controller
public class SysParamController extends BaseController {
	
	@Autowired
	ISysParamService sysParamService;
	
	@RequestMapping(value = "/config/change", method = RequestMethod.POST)
	public String registerSuccess(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if(request.getSession().getAttribute("admin") == null){
			try {
				String url = String.format("../admin");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String recordDay = request.getParameter("recordDay");
		String rootUsername = request.getParameter("rootUsername");
		String rootPassword = request.getParameter("rootPassword");
		String rootEmail = request.getParameter("rootEmail");
		String emailPassword = request.getParameter("emailPassword");
		
		int code = 200;
		
		if(recordDay == null || recordDay.equals("")){
			code = 1000;
		}
		
		if(rootUsername == null || rootUsername.equals("")){
			code = 2000;
		}
		
		if(rootPassword == null || rootPassword.equals("")){
			code = 3000;
		}
		
		if(rootEmail == null || rootEmail.equals("")){
			code = 4000;
		}
		
		if(emailPassword == null || emailPassword.equals("")){
			code  = 5000;
		}
		
		if(code != 200){
			try {
				String url = String.format("config?error=%d&recordday=%s&rootusername=%s&rootemail=%s", code, recordDay, rootUsername, rootEmail);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		sysParamService.setParam(ISysParamService.DAY_TO_RECORD, Integer.parseInt(recordDay));
		sysParamService.setParam(ISysParamService.ROOT_USERNAME, rootUsername);
		sysParamService.setParam(ISysParamService.ROOT_PASSWORD, rootPassword);
		sysParamService.setParam(ISysParamService.ROOT_EMAIL, rootEmail);
		sysParamService.setParam(ISysParamService.ROOT_EMAIL_PASSWORD, emailPassword);
		
		try {
			String url = String.format("../admin/config");
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "error";
	}

}

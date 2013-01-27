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
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.beans.IBlockSite;
import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.ISysParam;
import com.adweb.putong.core.services.IBlockSiteService;
import com.adweb.putong.core.services.IBlockUserService;
import com.adweb.putong.core.services.ISysParamService;
import com.adweb.putong.impl.services.SysParamService;

@Controller
public class AdminController extends BaseController {
	
	@Autowired
	IBlockSiteService blockSiteService;
	
	@Autowired
	IBlockUserService blockUserService;
	
	@Autowired
	ISysParamService sysParamService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String showPortal(Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getSession().getAttribute("admin"));
		if(request.getSession().getAttribute("admin") == null){
			return "adminPages/index";
		}
		
		try {
			String url = String.format("admin/config");
			response.sendRedirect(url);
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	
	@RequestMapping(value = "/admin/{section}", method = RequestMethod.GET)
	public String showBlockSites(@PathVariable("section") String section, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		//System.out.println(request.getSession().getAttribute("admin"));
		if(request.getSession().getAttribute("admin") == null){
			try {
				String url = String.format("");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(section.equals("sites")){
			List<IBlockSite> blocksites = blockSiteService.list();
			model.addAttribute("blocksites", blocksites);
			return "adminPages/blacklist";
		}
		
		if(section.equals("users")){
			List<IBlockUser> blockusers = blockUserService.list();
			model.addAttribute("blockusers", blockusers);
			return "adminPages/user";
		}
		
		if(section.equals("config")){
			ISysParam sysParam = sysParamService.get();
			model.addAttribute("sysparam", sysParam);			
			return "adminPages/sysParam";
		}
		
		return "error";
	}
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String rootUsername = sysParamService.getParam(SysParamService.ROOT_USERNAME);
		String rootPassword = sysParamService.getParam(SysParamService.ROOT_PASSWORD);
		
		if(username.equals(rootUsername) && password.equals(rootPassword)) {
			//System.out.println("logged");
			request.getSession().setAttribute("admin", username);
			//System.out.println(request.getSession()+":"+request.getSession().getAttribute("admin"));
			
			try {
				String url = String.format("config");
				response.sendRedirect(url);
				return "success";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "error";
	}

}

package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.services.IBlockUserService;

@Controller
public class BlockUserController extends BaseController {
	
	@Autowired
	IBlockUserService blockUserService;
	
	@RequestMapping(value = "/block/user/add", method = RequestMethod.POST)
	public String addBlockUser(@RequestParam("username") String username,
			@RequestParam("reason") String reason, Model model,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(request.getSession().getAttribute("admin") == null){
			try {
				String url = String.format("../../admin");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(username+","+reason);
		System.out.println(blockUserService.put(username, reason));
		

		try {
			String url = String.format("../../admin/users");
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}

	@RequestMapping(value = "/block/user/remove", method = RequestMethod.POST)
	public String removeBlockUser(@RequestParam("id") Long id, Model model,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		if(request.getSession().getAttribute("admin") == null){
			try {
				String url = String.format("../../admin");
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		blockUserService.drop(id);

		try {
			String url = String.format("../../admin/users");
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}

}

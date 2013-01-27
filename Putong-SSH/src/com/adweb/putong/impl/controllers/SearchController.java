package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.beans.IUser;

@Controller
public class SearchController extends BaseController {

	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public String search(@RequestParam("q") String keyword, Model model,
			HttpServletResponse response, HttpServletRequest request) {

		try {
			String url = String.format("../search/%s", keyword);
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}

	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String showSearchResult(@PathVariable("keyword") String keyword,
			Model model, HttpServletResponse response,
			HttpServletRequest request) {

		IUser self = (IUser) request.getSession().getAttribute("self");
		if (self == null) {
			try {
				String url = String.format("../?callback=%s", request.getRequestURL());
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<IUser> result = accountService.searchUsers(keyword, null, null);
		model.addAttribute("keyword", keyword);
		model.addAttribute("results", result);

		return "searchResult";
	}
}

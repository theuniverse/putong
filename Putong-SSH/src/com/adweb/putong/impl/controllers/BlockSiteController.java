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

import com.adweb.putong.core.services.IBlockSiteService;
import com.adweb.putong.impl.controllers.json.JsonError403;

@Controller
public class BlockSiteController extends BaseController {

	private static final String BANNED_SITE = "The site is banned";

	@Autowired
	IBlockSiteService blockSiteService;

	@RequestMapping(value = "/block/site/check", method = RequestMethod.POST)
	public String list(@RequestParam("url") String url, Model model) {
		boolean result = blockSiteService.checkSite(url);
		if (!result) {
			model.addAttribute(ERROR_JSON, new JsonError403(BANNED_SITE));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/block/site/add", method = RequestMethod.POST)
	public String addBlocksite(@RequestParam("domain") String domain,
			@RequestParam("title") String title,
			@RequestParam("reason") String reason, Model model,
			HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
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
		
		String new_domain = request.getParameter("domain");
		String new_title = request.getParameter("title");
		String new_reason = request.getParameter("reason");

		blockSiteService.put(new_domain, new_title, new_reason);

		try {
			String url = String.format("../../admin/sites");
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}

	@RequestMapping(value = "/block/site/remove", method = RequestMethod.POST)
	public String removeBlocksite(@RequestParam("id") Long id, Model model,
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

		blockSiteService.drop(id);

		try {
			String url = String.format("../../admin/sites");
			response.sendRedirect(url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}
}

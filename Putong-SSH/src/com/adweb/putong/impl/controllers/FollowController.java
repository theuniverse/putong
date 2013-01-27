package com.adweb.putong.impl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.services.IBlockUserService;
import com.adweb.putong.core.services.IFriendService;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;

@Controller
public class FollowController extends BaseController {

	@Autowired
	IBlockUserService blockUserService;

	@Autowired
	IFriendService friendService;
	
	private static final String INVALID_FOLLOWER = "Invalid follower.";

	@RequestMapping(value = "/friends/follow", method = RequestMethod.POST)
	public String follow(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("target") String target, Model model) {
		// validate and authenticate json request
		System.out.println("[FOLLOW]"+username+","+target);
		boolean logined = accountService.login(username, password);
		boolean activated = true;//accountService.isActivated(username);
		boolean live = blockUserService.checkUser(username);
		if (!logined || !activated || !live)
			return ERROR_PAGE;
		
		System.out.println("here");

		// check required parameters
		if (target == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = friendService.follow(username, target);
		System.out.println(isSucceed);
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_FOLLOWER));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friends/unfollow", method = RequestMethod.POST)
	public String unfollow(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("target") String target, Model model) {
		// validate and authenticate json request
		boolean logined = accountService.login(username, password);
		boolean activated = true;//accountService.isActivated(username);
		boolean killed = blockUserService.checkUser(username);
		if (!logined || !activated || killed)
			return ERROR_PAGE;

		// check required parameters
		if (target == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = friendService.unfollow(username, target);
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_FOLLOWER));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

}

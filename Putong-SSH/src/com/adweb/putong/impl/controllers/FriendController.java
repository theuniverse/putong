package com.adweb.putong.impl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.services.IFriendService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonObject;
import com.adweb.putong.impl.controllers.json.JsonUserInfo;

@Controller
public class FriendController extends BaseController {
	private static final String INVALID_FOLLOWER = "Invalid follower.";

	@Autowired
	private IFriendService friendService;

	public class JsonFriendFollowRequest extends JsonAuthRequest {
		private String target_user;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}
	}

	public class JsonFriendUnfollowRequest extends JsonAuthRequest {
		private String target_user;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}
	}

	public class JsonFriendFollowingRequest extends JsonAuthRequest {
		private String target_user;
		private Integer sindex;
		private Integer eindex;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}

		public Integer getSindex() {
			return sindex;
		}

		public void setSindex(Integer sindex) {
			this.sindex = sindex;
		}

		public Integer getEindex() {
			return eindex;
		}

		public void setEindex(Integer eindex) {
			this.eindex = eindex;
		}
	}

	public class JsonFriendFollowedRequest extends JsonAuthRequest {
		private String target_user;
		private Integer sindex;
		private Integer eindex;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}

		public Integer getSindex() {
			return sindex;
		}

		public void setSindex(Integer sindex) {
			this.sindex = sindex;
		}

		public Integer getEindex() {
			return eindex;
		}

		public void setEindex(Integer eindex) {
			this.eindex = eindex;
		}
	}

	public class JsonFriendRelationshipRequest extends JsonAuthRequest {
		private String target_user;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}
	}

	public class JsonFriendRelationshipResponse extends JsonObject {
		private boolean following;
		private boolean followed;

		public JsonFriendRelationshipResponse(boolean following,
				boolean followed) {
			this.following = following;
			this.followed = followed;
		}

		public boolean isFollowing() {
			return following;
		}

		public void setFollowing(boolean following) {
			this.following = following;
		}

		public boolean isFollowed() {
			return followed;
		}

		public void setFollowed(boolean followed) {
			this.followed = followed;
		}
	}

	public class JsonFriendSearchRequest extends JsonAuthRequest {
		private String keyword;
		private Integer sindex;
		private Integer eindex;

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public Integer getSindex() {
			return sindex;
		}

		public void setSindex(Integer sindex) {
			this.sindex = sindex;
		}

		public Integer getEindex() {
			return eindex;
		}

		public void setEindex(Integer eindex) {
			this.eindex = eindex;
		}
	}

	@RequestMapping(value = "/friend/follow", method = RequestMethod.POST)
	public String follow(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendFollowRequest ffRequest = authenticate(
				JsonFriendFollowRequest.class, json, model);
		if (ffRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (ffRequest.getTarget_user() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = friendService.follow(ffRequest.getUsername(),
				ffRequest.getTarget_user());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_FOLLOWER));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friend/unfollow", method = RequestMethod.POST)
	public String unfollow(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendUnfollowRequest fuRequest = authenticate(
				JsonFriendUnfollowRequest.class, json, model);
		if (fuRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (fuRequest.getTarget_user() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = friendService.unfollow(fuRequest.getUsername(),
				fuRequest.getTarget_user());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_FOLLOWER));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friend/following", method = RequestMethod.POST)
	public String following(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendFollowingRequest ffiRequest = authenticate(
				JsonFriendFollowingRequest.class, json, model);
		if (ffiRequest == null)
			return ERROR_PAGE;

		// call services
		List<IUser> users = friendService.following(ffiRequest.getUsername(),
				ffiRequest.getTarget_user(), ffiRequest.getSindex(), ffiRequest
						.getEindex());
		if (users == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		List<JsonUserInfo> jsonUsers = new ArrayList<JsonUserInfo>();
		for (IUser user : users)
			jsonUsers.add(new JsonUserInfo(user));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonUsers));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friend/followed", method = RequestMethod.POST)
	public String followed(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendFollowedRequest ffdRequest = authenticate(
				JsonFriendFollowedRequest.class, json, model);
		if (ffdRequest == null)
			return ERROR_PAGE;

		// call services
		List<IUser> users = friendService.follower(ffdRequest.getUsername(),
				ffdRequest.getTarget_user(), ffdRequest.getSindex(), ffdRequest
						.getEindex());
		if (users == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		List<JsonUserInfo> jsonUsers = new ArrayList<JsonUserInfo>();
		for (IUser user : users)
			jsonUsers.add(new JsonUserInfo(user));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonUsers));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friend/relationship", method = RequestMethod.POST)
	public String relationship(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendRelationshipRequest frRequest = authenticate(
				JsonFriendRelationshipRequest.class, json, model);
		if (frRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (frRequest.getTarget_user() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isFollowerOfTarget = friendService.isFollowerOfTarget(frRequest
				.getUsername(), frRequest.getTarget_user());
		boolean isFollowedByTarget = friendService.isFollowedByTarget(frRequest
				.getUsername(), frRequest.getTarget_user());
		model.addAttribute(SUCCEED_JSON, new JsonFriendRelationshipResponse(
				isFollowerOfTarget, isFollowedByTarget));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/friend/search", method = RequestMethod.POST)
	public String search(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonFriendSearchRequest fsRequest = authenticate(
				JsonFriendSearchRequest.class, json, model);
		if (fsRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (fsRequest.getKeyword() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		List<IUser> users = friendService.searchFollowing(fsRequest
				.getUsername(), fsRequest.getKeyword(), fsRequest.getSindex(),
				fsRequest.getEindex());
		if (users == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		List<JsonUserInfo> jsonUsers = new ArrayList<JsonUserInfo>();
		for (IUser user : users)
			jsonUsers.add(new JsonUserInfo(user));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonUsers));
		return SUCCEED_PAGE;
	}
}

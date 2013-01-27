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
import com.adweb.putong.core.services.IWeiboService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonObject;
import com.adweb.putong.impl.controllers.json.JsonUserInfo;

@Controller
public class AccountController extends BaseController {
	private static final String USER_EXISTS = "User already exists.";

	@Autowired
	private IFriendService friendService;

	@Autowired
	private IWeiboService weiboService;

	public class JsonAccountRegisterCheckRequest extends JsonObject {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	public class JsonAccountRegisterCheckResponse extends JsonObject {
		private boolean available;

		public JsonAccountRegisterCheckResponse(boolean available) {
			this.available = available;
		}

		public boolean isAvailable() {
			return available;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}
	}

	public class JsonAccountRegisterRequest extends JsonAuthRequest {
		private String nickname;

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
	}

	public class JsonAccountLoginRequest extends JsonAuthRequest {
		// nothing more
	}

	public class JsonAccountUpdateRequest extends JsonAuthRequest {
		private String new_password;
		private String new_nickname;
		private String new_avatar;

		public String getNew_password() {
			return new_password;
		}

		public void setNew_password(String newPassword) {
			new_password = newPassword;
		}

		public String getNew_nickname() {
			return new_nickname;
		}

		public void setNew_nickname(String newNickname) {
			new_nickname = newNickname;
		}

		public String getNew_avatar() {
			return new_avatar;
		}

		public void setNew_avatar(String newAvatar) {
			new_avatar = newAvatar;
		}
	}

	public class JsonAccountUserinfoRequest extends JsonAuthRequest {
		private String target_user;
		private Boolean full;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
		}

		public Boolean getFull() {
			return full;
		}

		public void setFull(Boolean full) {
			this.full = full;
		}
	}

	public class JsonAccountSearchRequest extends JsonAuthRequest {
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

	@RequestMapping(value = "/account/register/check", method = RequestMethod.POST)
	public String registerCheck(@RequestBody String json, Model model) {
		// validate json request
		JsonAccountRegisterCheckRequest arcRequest = parseJson(
				JsonAccountRegisterCheckRequest.class, json);
		if (arcRequest == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// check required parameters
		if (arcRequest.getUsername() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean available = accountService.registerCheck(arcRequest
				.getUsername());

		model.addAttribute(SUCCEED_JSON, new JsonAccountRegisterCheckResponse(
				available));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/account/register", method = RequestMethod.POST)
	public String register(@RequestBody String json, Model model) {
		// validate json request
		JsonAccountRegisterRequest arRequest = parseJson(
				JsonAccountRegisterRequest.class, json);
		if (arRequest == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// check required parameters
		if (arRequest.getUsername() == null || arRequest.getPassword() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = accountService.register(arRequest.getUsername(),
				arRequest.getPassword(), arRequest.getNickname());

		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(USER_EXISTS));
			return ERROR_PAGE;
		} else
			return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/account/login", method = RequestMethod.POST)
	public String login(@RequestBody String json, Model model) {
		json = ControllerHelper.validateDesktop(json);

		// validate and authenticate json request
		JsonAccountLoginRequest alRequest = authenticate(
				JsonAccountLoginRequest.class, json, model);
		if (alRequest == null)
			return ERROR_PAGE;
		else
			return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/account/update", method = RequestMethod.POST)
	public String update(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonAccountUpdateRequest auRequest = authenticate(
				JsonAccountUpdateRequest.class, json, model);
		if (auRequest == null)
			return ERROR_PAGE;

		// call services
		// TODO change the interface
		accountService.update(auRequest.getUsername(), auRequest
				.getNew_password(), auRequest.getNew_nickname(), auRequest
				.getNew_avatar(), null, null, null, null);

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/account/userinfo", method = RequestMethod.POST)
	public String userinfo(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonAccountUserinfoRequest auiRequest = authenticate(
				JsonAccountUserinfoRequest.class, json, model);
		if (auiRequest == null)
			return ERROR_PAGE;

		// call services
		IUser target = accountService.userinfo(auiRequest.getUsername(),
				auiRequest.getTarget_user());

		if (target == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		JsonUserInfo jsonUserInfo = new JsonUserInfo(target);

		if (auiRequest.getFull() != null && auiRequest.getFull()) {
			// target always exists (checked above)
			// so the 3 services will never return -1
			jsonUserInfo.setWeibos(weiboService.count(auiRequest.getUsername(),
					auiRequest.getTarget_user()));
			jsonUserInfo.setFollowing(friendService.followingCount(auiRequest
					.getUsername(), auiRequest.getTarget_user()));
			jsonUserInfo.setFollowed(friendService.followerCount(auiRequest
					.getUsername(), auiRequest.getTarget_user()));
		}

		model.addAttribute(SUCCEED_JSON, jsonUserInfo);
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/account/search", method = RequestMethod.POST)
	public String search(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonAccountSearchRequest asRequest = authenticate(
				JsonAccountSearchRequest.class, json, model);
		if (asRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (asRequest.getKeyword() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		List<IUser> users = accountService.searchUsers(asRequest.getKeyword(),
				asRequest.getSindex(), asRequest.getEindex());
		// users will not be null

		List<JsonUserInfo> jsonUsers = new ArrayList<JsonUserInfo>();
		for (IUser user : users)
			jsonUsers.add(new JsonUserInfo(user));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonUsers));
		return SUCCEED_PAGE;
	}
}

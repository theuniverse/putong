package com.adweb.putong.impl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.adweb.putong.core.services.IAccountService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError401;
import com.adweb.putong.impl.controllers.json.JsonObject;

public class BaseController implements ResponseInfo {
	@Autowired
	protected IAccountService accountService;

	protected <T extends JsonObject> T parseJson(Class<T> clazz, String json) {
		try {
			return new Gson().fromJson(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	protected <T extends JsonAuthRequest> T authenticate(Class<T> clazz,
			String json, Model model) {
		// validate json request
		T authRequest = parseJson(clazz, json);
		if (authRequest == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return null;
		}

		// authenticate json request
		boolean isSucceed = accountService.login(authRequest.getUsername(),
				authRequest.getPassword());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError401());
			return null;
		}

		return authRequest;
	}
}

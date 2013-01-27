package com.adweb.putong.impl.controllers.json;

import com.google.gson.Gson;

public class JsonObject {
	public String toString() {
		return new Gson().toJson(this);
	}
}
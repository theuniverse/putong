package com.adweb.putong.impl.controllers.json;

public class JsonError extends JsonObject {
	private int code;
	private String message;

	public JsonError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
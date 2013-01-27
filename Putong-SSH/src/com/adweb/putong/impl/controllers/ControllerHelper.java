package com.adweb.putong.impl.controllers;

public class ControllerHelper {
	public static String validateDesktop(String json) {
		return json.substring(json.indexOf("{"));
	}
}

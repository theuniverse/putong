package com.adweb.putong.impl.controllers.json;

public interface ResponseInfo {
	public static final String ERROR_JSON = "error";
	public static final String ERROR_PAGE = "error";
	public static final String SUCCEED_JSON = "json";
	public static final String SUCCEED_PAGE = "succeed";

	public static final int HTTP_BAD_REQUEST = 400;
	public static final String BAD_REQUEST = "Bad Request.";

	public static final int HTTP_UNAUTHORIZED = 401;
	public static final String UNAUTHORIZED = "Unauthorized.";

	public static final int HTTP_FORBIDDEN = 403;
	public static final String NO_SUCH_USER = "User not found.";
	public static final String NO_SUCH_WEIBO = "Weibo not found.";
	// Do not add error messages here unless you are permitted
	// Specific error messages should be defined in their controllers
}

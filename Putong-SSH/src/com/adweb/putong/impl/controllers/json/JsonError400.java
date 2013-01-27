package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.impl.controllers.ResponseInfo;

public class JsonError400 extends JsonError implements ResponseInfo {
	public JsonError400() {
		super(HTTP_BAD_REQUEST, BAD_REQUEST);
	}
}
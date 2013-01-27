package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.impl.controllers.ResponseInfo;

public class JsonError401 extends JsonError implements ResponseInfo {
	public JsonError401() {
		super(HTTP_UNAUTHORIZED, UNAUTHORIZED);
	}
}
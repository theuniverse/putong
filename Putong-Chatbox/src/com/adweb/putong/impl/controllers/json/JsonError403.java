package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.impl.controllers.json.ResponseInfo;

public class JsonError403 extends JsonError implements ResponseInfo {
	public JsonError403(String message) {
		super(HTTP_FORBIDDEN, message);
	}
}
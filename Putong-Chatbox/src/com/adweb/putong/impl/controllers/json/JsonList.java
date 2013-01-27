package com.adweb.putong.impl.controllers.json;

import java.util.List;

public class JsonList extends JsonObject {
	@SuppressWarnings("rawtypes")
	public List list;

	@SuppressWarnings("rawtypes")
	public JsonList(List list) {
		this.list = list;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
}
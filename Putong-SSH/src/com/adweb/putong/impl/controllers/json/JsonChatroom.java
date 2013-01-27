package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.IChatroom;

public class JsonChatroom extends JsonObject {
	
	private String title;
	private String url;
	
	public JsonChatroom(IChatroom chatroom){
		this.title = chatroom.getTitle();
		this.url = chatroom.getUrl();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}

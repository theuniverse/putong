package com.adweb.putong.impl.controllers.json;

import java.util.Date;

public class JsonRecord extends JsonObject {
	
	private Long id;
	private JsonChatroom chatroom;
	private JsonUser user;
	private Long enterTime;
	private Long leaveTime;
	private Boolean online;
	
	public JsonRecord(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JsonChatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(JsonChatroom chatroom) {
		this.chatroom = chatroom;
	}

	public JsonUser getUser() {
		return user;
	}

	public void setUser(JsonUser user) {
		this.user = user;
	}

	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime.getTime();
	}

	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime.getTime();
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}
}

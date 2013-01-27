package com.adweb.putong.impl.controllers.json;

import java.util.Date;

import com.adweb.putong.core.beans.IRecord;

public class JsonRecord extends JsonObject {
	
	private Long id;
	private JsonChatroom chatroom;
	private JsonUser user;
	private Long enterTime;
	private Long leaveTime;
	private Boolean online;
	
	public JsonRecord(IRecord record){
		this.id = record.getKey();
		this.chatroom = new JsonChatroom(record.getChatroom());
		this.user = new JsonUser(record.getUser());
		this.enterTime = record.getEnterTime().getTime();
		this.leaveTime = record.getLeaveTime().getTime();
		this.online = record.getStatus();
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

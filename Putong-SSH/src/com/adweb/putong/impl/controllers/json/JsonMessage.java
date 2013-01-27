package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.IMessage;

public class JsonMessage extends JsonObject {
	private long id;
	private String content;
	private long time;
	private JsonUserInfo sender;

	public JsonMessage(IMessage message) {
		this.id = message.getKey();
		this.content = message.getContent();
		this.time = message.getTime().getTime();
		this.sender = new JsonUserInfo(message.getSender());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public JsonUserInfo getSender() {
		return sender;
	}

	public void setSender(JsonUserInfo sender) {
		this.sender = sender;
	}
}
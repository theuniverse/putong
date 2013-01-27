package com.adweb.putong.impl.controllers.json;

import java.util.ArrayList;
import java.util.List;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;

public class JsonMail extends JsonObject {
	private long id;
	private String topic;
	private List<JsonUserInfo> users = new ArrayList<JsonUserInfo>();
	private JsonMessage last;

	public JsonMail(IMail mail, IMessage last) {
		this.id = mail.getKey();
		this.topic = mail.getTopic();
		for (IUser user : mail.getUsers())
			this.users.add(new JsonUserInfo(user));
		this.last = new JsonMessage(last);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<JsonUserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<JsonUserInfo> users) {
		this.users = users;
	}

	public JsonMessage getLast() {
		return last;
	}

	public void setLast(JsonMessage last) {
		this.last = last;
	}
}
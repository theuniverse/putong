package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.IComment;

public class JsonComment extends JsonObject {
	private long id;
	private long wid;
	private String content;
	private JsonUserInfo author;
	private long time;

	public JsonComment(IComment comment) {
		this.id = comment.getKey();
		this.wid = comment.getWeibo().getKey();
		this.content = comment.getContent();
		this.author = new JsonUserInfo(comment.getAuthor());
		this.time = comment.getTime().getTime();
	}

	public long getId() {
		return id;
	}

	public long getWid() {
		return wid;
	}

	public String getContent() {
		return content;
	}

	public JsonUserInfo getAuthor() {
		return author;
	}

	public long getTime() {
		return time;
	}
}
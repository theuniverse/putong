package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.events.ICommentEvent;
import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.beans.events.IFollowerEvent;

public class JsonNotice extends JsonObject {
	private long id;
	private boolean read;
	private JsonComment comment;
	private JsonUserInfo follower;

	public JsonNotice(IEvent event) {
		this.id = event.getKey();
		this.read = event.isRead();

		if (event instanceof ICommentEvent)
			this.comment = new JsonComment(((ICommentEvent) event).getComment());
		else if (event instanceof IFollowerEvent)
			this.follower = new JsonUserInfo(((IFollowerEvent) event)
					.getFollower());
	}

	public long getId() {
		return id;
	}

	public boolean isRead() {
		return read;
	}

	public JsonComment getComment() {
		return comment;
	}

	public JsonUserInfo getFollower() {
		return follower;
	}
}
package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.beans.events.IWeiboEvent;

public class JsonNews extends JsonObject {
	private long id;
	private boolean read;
	private JsonWeibo weibo;

	public JsonNews(IEvent event) {
		this.id = event.getKey();
		this.read = event.isRead();

		if (event instanceof IWeiboEvent)
			this.weibo = new JsonWeibo(((IWeiboEvent) event).getWeibo());
	}

	public long getId() {
		return id;
	}

	public boolean isRead() {
		return read;
	}

	public JsonWeibo getWeibo() {
		return weibo;
	}

	public void setWeiboComments(int comments) {
		weibo.setComments(comments);
	}
}
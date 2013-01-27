package com.adweb.putong.impl.controllers.json;

import java.util.ArrayList;
import java.util.List;

import com.adweb.putong.core.beans.IImage;
import com.adweb.putong.core.beans.IWeibo;

public class JsonWeibo extends JsonObject {
	private long id;
	private String content;
	private JsonUserInfo author;
	private long time;
	private String video;
	private List<String> imagelist;
	private Long pid;
	private Long oid;
	private int comments;

	public JsonWeibo(IWeibo weibo) {
		this.id = weibo.getKey();
		this.content = weibo.getContent();
		this.author = new JsonUserInfo(weibo.getAuthor());
		this.time = weibo.getTime().getTime();
		this.video = (weibo.getVideo() != null) ? weibo.getVideo().getUrl()
				: null;
		this.imagelist = new ArrayList<String>();
		for (IImage image : weibo.getImages())
			this.imagelist.add(image.getUrl());

		if (weibo.isForwarding()) {
			this.pid = (weibo.getPredecessor() != null) ? weibo
					.getPredecessor().getKey() : null;
			// the origin of forwarding will not be null
			this.oid = weibo.getOrigin().getKey();
		} else {
			this.pid = null;
			this.oid = null;
		}
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public long getId() {
		return id;
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

	public String getVideo() {
		return video;
	}

	public List<String> getImagelist() {
		return imagelist;
	}

	public Long getPid() {
		return pid;
	}

	public Long getOid() {
		return oid;
	}
}
package com.adweb.putong.impl.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;

import com.adweb.putong.core.beans.IVideo;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Video extends BaseBean implements IVideo {
	@Basic
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

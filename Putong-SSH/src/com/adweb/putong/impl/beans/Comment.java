package com.adweb.putong.impl.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Comment extends BaseBean implements IComment {
	@ManyToOne(targetEntity = Weibo.class)
	private IWeibo weibo;

	@Basic
	private String content;

	@ManyToOne(targetEntity = User.class)
	private IUser author;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;

	public IWeibo getWeibo() {
		return weibo;
	}

	public void setWeibo(IWeibo weibo) {
		this.weibo = weibo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public IUser getAuthor() {
		return author;
	}

	public void setAuthor(IUser author) {
		this.author = author;
	}

	public Date getTime() {
		if (time != null)
			return time.getTime();
		else
			return null;
	}

	public void setTime(Date time) {
		this.time = Calendar.getInstance();
		this.time.setTime(time);
	}
}

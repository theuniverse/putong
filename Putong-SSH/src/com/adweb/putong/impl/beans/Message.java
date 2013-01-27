package com.adweb.putong.impl.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Message extends BaseBean implements IMessage {
	@Basic
	private String content;

	@ManyToOne(targetEntity = User.class)
	private IUser sender;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;

	@ManyToOne(targetEntity = Mail.class)
	private IMail mail;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public IUser getSender() {
		return sender;
	}

	public void setSender(IUser sender) {
		this.sender = sender;
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

	public IMail getMail() {
		return mail;
	}

	public void setMail(IMail mail) {
		this.mail = mail;
	}
}

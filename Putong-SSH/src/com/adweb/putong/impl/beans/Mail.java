package com.adweb.putong.impl.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Mail extends BaseBean implements IMail {
	@Basic
	private String topic;

	@ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
	private List<IUser> users = new ArrayList<IUser>();

	@OneToMany(targetEntity = Message.class, cascade = { CascadeType.ALL })
	private List<IMessage> messages = new ArrayList<IMessage>();

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<IUser> getUsers() {
		return users;
	}

	public void setUsers(List<IUser> users) {
		this.users = users;
	}

	public List<IMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<IMessage> messages) {
		this.messages = messages;
	}
}

package com.adweb.putong.core.beans;

import java.util.List;

import com.iiiss.template.ssh.common.core.IBean;

public interface IMail extends IBean {

	public String getTopic();

	public void setTopic(String topic);

	public List<IUser> getUsers();

	public void setUsers(List<IUser> users);

	public List<IMessage> getMessages();

	public void setMessages(List<IMessage> messages);

}
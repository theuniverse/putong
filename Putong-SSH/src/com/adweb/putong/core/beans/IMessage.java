package com.adweb.putong.core.beans;

import java.util.Date;

import com.iiiss.template.ssh.common.core.IBean;

public interface IMessage extends IBean {

	public String getContent();

	public void setContent(String content);

	public IUser getSender();

	public void setSender(IUser sender);

	public Date getTime();

	public void setTime(Date time);

	public IMail getMail();

	public void setMail(IMail mail);

}
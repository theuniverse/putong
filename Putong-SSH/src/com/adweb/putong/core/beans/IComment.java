package com.adweb.putong.core.beans;

import java.util.Date;

import com.iiiss.template.ssh.common.core.IBean;

public interface IComment extends IBean {

	public IWeibo getWeibo();

	public void setWeibo(IWeibo weibo);

	public String getContent();

	public void setContent(String content);

	public IUser getAuthor();

	public void setAuthor(IUser author);

	public Date getTime();

	public void setTime(Date time);

}
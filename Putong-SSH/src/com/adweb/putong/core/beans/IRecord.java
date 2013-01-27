package com.adweb.putong.core.beans;

import java.util.Date;

import com.iiiss.template.ssh.common.core.IBean;

public interface IRecord extends IBean{
	
	public Date getEnterTime();
	public void setEnterTime(Date enterTime);
	
	public Date getLeaveTime();
	public void setLeaveTime(Date leaveTime);
	
	public IUser getUser();
	public void setUser(IUser user);
	
	public IChatroom getChatroom();
	public void setChatroom(IChatroom chatroom);
	
	public Boolean getStatus();
	public void setStatus(Boolean status);

}

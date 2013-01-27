package com.adweb.putong.core.beans;

import java.util.Date;

import com.iiiss.template.ssh.common.core.IBean;

public interface IBlockUser extends IBean {

	public IUser getUser();
	public void setUser(IUser user);
	
	public String getReason();
	public void setReason(String reason);
	
	public Date getTime();
	public void setTime(Date time);
	
}

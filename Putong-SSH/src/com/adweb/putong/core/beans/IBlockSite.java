package com.adweb.putong.core.beans;

import java.util.Date;

import com.iiiss.template.ssh.common.core.IBean;

public interface IBlockSite extends IBean{

	public String getDomain();
	public void setDomain(String domain);
	
	public String getTitle();
	public void setTitle(String title);
	
	public String getReason();
	public void setReason(String reason);
	
	public Date getTime();
	public void setTime(Date time);
	
}

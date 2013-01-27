package com.adweb.putong.core.beans;

import com.iiiss.template.ssh.common.core.IBean;

public interface ISysParam extends IBean {

	public Integer getRecordDay();
	public void setRecordDay(Integer recordDay);
	
	public String getRootUsername();
	public void setRootUsername(String rootUsername);
	
	public String getRootPassword();
	public void setRootPassword(String rootPassword);
	
	public String getRootEmail();
	public void setRootEmail(String rootEmail);
	
	public String getEmailPassword();
	public void setEmailPassword(String emailPassword);
	
}

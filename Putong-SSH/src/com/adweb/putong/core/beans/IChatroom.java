package com.adweb.putong.core.beans;

import java.util.List;

import com.iiiss.template.ssh.common.core.IBean;

public interface IChatroom extends IBean {

	public String getTitle();
	public void setTitle(String title);
	
	public String getUrl();
	public void setUrl(String url);
	
	public List<IRecord> getRecords();
	public void setRecords(List<IRecord> records);
	
}

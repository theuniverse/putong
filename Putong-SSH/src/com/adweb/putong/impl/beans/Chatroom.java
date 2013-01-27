package com.adweb.putong.impl.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.beans.IRecord;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Chatroom extends BaseBean implements IChatroom {
	
	@Basic
	private String title;
	
	@Basic
	private String url;
	
	@OneToMany(targetEntity = Record.class, mappedBy = "chatroom", cascade = { CascadeType.ALL })
	private List<IRecord> records = new ArrayList<IRecord>();

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public List<IRecord> getRecords() {
		return records;
	}

	@Override
	public void setRecords(List<IRecord> records) {
		this.records = records;
	}

}

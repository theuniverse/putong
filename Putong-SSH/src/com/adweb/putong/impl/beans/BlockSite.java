package com.adweb.putong.impl.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IBlockSite;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class BlockSite extends BaseBean implements IBlockSite {
	
	@Basic
	private String domain;
	
	@Basic
	private String title;
	
	@Basic
	private String reason;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public Date getTime() {
		if (time != null)
			return time.getTime();
		else
			return null;
	}

	@Override
	public void setTime(Date time) {
		this.time = Calendar.getInstance();
		this.time.setTime(time);
	}

}

package com.adweb.putong.impl.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;

import com.adweb.putong.core.beans.ISysParam;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class SysParam extends BaseBean implements ISysParam {
	
	@Basic
	private Integer recordDay;
	
	@Basic
	private String rootUsername;
	
	@Basic
	private String rootPassword;
	
	@Basic
	private String rootEmail;
	
	@Basic
	private String emailPassword;

	@Override
	public Integer getRecordDay() {
		return recordDay;
	}

	@Override
	public void setRecordDay(Integer recordDay) {
		this.recordDay = recordDay;
	}

	@Override
	public String getRootUsername() {
		return rootUsername;
	}

	@Override
	public void setRootUsername(String rootUsername) {
		this.rootUsername = rootUsername;
	}

	@Override
	public String getRootPassword() {
		return rootPassword;
	}

	@Override
	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}

	@Override
	public String getRootEmail() {
		return rootEmail;
	}

	@Override
	public void setRootEmail(String rootEmail) {
		this.rootEmail = rootEmail;
	}

	@Override
	public String getEmailPassword() {
		return emailPassword;
	}

	@Override
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

}

package com.adweb.putong.impl.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class BlockUser extends BaseBean implements IBlockUser {
	
	@OneToOne(targetEntity = User.class)
	private IUser user;
	
	@Basic
	private String reason;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;

	@Override
	public IUser getUser() {
		return user;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
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

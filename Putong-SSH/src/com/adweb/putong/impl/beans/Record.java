package com.adweb.putong.impl.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Record extends BaseBean implements IRecord {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar enterTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar leaveTime;
	
	@OneToOne(targetEntity = User.class)
	private IUser user;
	
	@OneToOne(targetEntity = Chatroom.class)
	private IChatroom chatroom;
	
	@Basic
	private Boolean status;

	@Override
	public Date getEnterTime() {
		if (enterTime != null)
			return enterTime.getTime();
		else
			return null;
	}

	@Override
	public void setEnterTime(Date enterTime) {
		this.enterTime = Calendar.getInstance();
		this.enterTime.setTime(enterTime);
	}

	@Override
	public Date getLeaveTime() {
		if (leaveTime != null)
			return leaveTime.getTime();
		else
			return null;
	}

	@Override
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = Calendar.getInstance();
		this.leaveTime.setTime(leaveTime);
	}

	@Override
	public IUser getUser() {
		return user;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
	}

	@Override
	public IChatroom getChatroom() {
		return chatroom;
	}

	@Override
	public void setChatroom(IChatroom chatroom) {
		this.chatroom = chatroom;
	}

	@Override
	public Boolean getStatus() {
		return status;
	}

	@Override
	public void setStatus(Boolean status) {
		this.status = status;
	}

}

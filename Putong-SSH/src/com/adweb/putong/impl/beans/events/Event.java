package com.adweb.putong.impl.beans.events;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.events.IEvent;
import com.iiiss.template.ssh.common.impl.BaseBean;
import com.adweb.putong.impl.beans.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "EventType")
public class Event extends BaseBean implements IEvent {
	@OneToOne(targetEntity = User.class)
	private IUser receiver;

	@Basic
	private boolean read;

	public IUser getReceiver() {
		return receiver;
	}

	public void setReceiver(IUser receiver) {
		this.receiver = receiver;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}

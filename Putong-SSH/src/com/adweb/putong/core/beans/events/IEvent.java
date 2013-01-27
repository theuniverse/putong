package com.adweb.putong.core.beans.events;

import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.core.IBean;

public interface IEvent extends IBean {

	public IUser getReceiver();

	public void setReceiver(IUser receiver);

	public boolean isRead();

	public void setRead(boolean read);

}
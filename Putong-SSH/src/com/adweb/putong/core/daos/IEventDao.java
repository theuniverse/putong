package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.beans.events.IEvent;
import com.iiiss.template.ssh.common.core.IDao;

public interface IEventDao extends IDao {

	public IEvent createEvent(IUser receiver, IWeibo weibo);

	public IEvent createEvent(IUser receiver, IComment comment);

	public IEvent createEvent(IUser receiver, IUser follower);
	
	public IEvent createEvent(IUser receiver, IRecord record);

	public IEvent getEventById(long id);

	public List<IEvent> getEventsByReceiver(IUser receiver);

	public void dropEvents(IWeibo weibo);
	
	public void dropEvents(IComment comment);
	
	public void dropEvents(IRecord record);

}
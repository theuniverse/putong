package com.adweb.putong.impl.daos;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.beans.events.ICommentEvent;
import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.beans.events.IFollowerEvent;
import com.adweb.putong.core.beans.events.IRecordEvent;
import com.adweb.putong.core.beans.events.IWeiboEvent;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.IEventDao;

@Transactional
public class EventDao extends BaseDao implements IEventDao {
	public IEvent createEvent(IUser receiver, IWeibo weibo) {
		IWeiboEvent event = persistenceManager.newObject("weiboEvent");
		event.setReceiver(receiver);
		event.setRead(false);
		event.setWeibo(weibo);
		return event;
	}

	public IEvent createEvent(IUser receiver, IComment comment) {
		ICommentEvent event = persistenceManager.newObject("commentEvent");
		event.setReceiver(receiver);
		event.setRead(false);
		event.setComment(comment);
		return event;
	}

	public IEvent createEvent(IUser receiver, IUser follower) {
		IFollowerEvent event = persistenceManager.newObject("followerEvent");
		event.setReceiver(receiver);
		event.setRead(false);
		event.setFollower(follower);
		return event;
	}
	
	@Override
	public IEvent createEvent(IUser receiver, IRecord record) {
		IRecordEvent event = persistenceManager.newObject("recordEvent");
		event.setReceiver(receiver);
		event.setRead(false);
		event.setRecord(record);
		return event;
	}

	public IEvent getEventById(long id) {
		return (IEvent) persistenceManager.getObject("event", id);
	}

	public List<IEvent> getEventsByReceiver(IUser receiver) {
		return persistenceManager.getObjects("event", "where receiver = ?",
				receiver);
	}

	public void dropEvents(IWeibo weibo) {
		List<IWeiboEvent> events = persistenceManager.getObjects("weiboEvent",
				"where weibo = ?", weibo);
		for (IWeiboEvent event : events)
			persistenceManager.deteleObject(event);
	}

	public void dropEvents(IComment comment) {
		List<ICommentEvent> events = persistenceManager.getObjects(
				"commentEvent", "where comment = ?", comment);
		for (ICommentEvent event : events)
			persistenceManager.deteleObject(event);
	}

	@Override
	public void dropEvents(IRecord record) {
		List<IRecordEvent> events = persistenceManager.getObjects("recordEvent",
				"where record = ?", record);
		for (IRecordEvent event : events)
			persistenceManager.deteleObject(event);
	}
}

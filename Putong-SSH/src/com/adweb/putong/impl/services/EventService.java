package com.adweb.putong.impl.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.events.ICommentEvent;
import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.beans.events.IFollowerEvent;
import com.adweb.putong.core.beans.events.IRecordEvent;
import com.adweb.putong.core.beans.events.IWeiboEvent;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IEventService;

public class EventService implements IEventService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IEventDao eventDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Transactional
	public List<IWeiboEvent> listTimeline(String username, Integer sindex,
			Integer eindex) {
		IUser user = userDao.getUserByUsername(username);

		List<IEvent> events = eventDao.getEventsByReceiver(user);
		List<IWeiboEvent> timeline = new ArrayList<IWeiboEvent>();
		for (IEvent event : events) {
			if (event.isRead())
				continue;

			if (event instanceof IWeiboEvent)
				timeline.add((IWeiboEvent) event);
		}

		return ServiceHelper.getRange(timeline, sindex, eindex, true, true);
	}
	
	@Transactional
	public List<IRecordEvent> listNewthings(String username, Integer sindex, Integer eindex) {
		IUser user = userDao.getUserByUsername(username);
		
		List<IEvent> events = eventDao.getEventsByReceiver(user);
		List<IRecordEvent> newthings = new ArrayList<IRecordEvent>();
		
		for (IEvent event : events) {
			if (event.isRead())
				continue;

			if (event instanceof IRecordEvent)
				newthings.add((IRecordEvent) event);
		}

		return ServiceHelper.getRange(newthings, sindex, eindex, true, true);
	}

	@Transactional
	public List<IEvent> listNotice(String username, Integer sindex,
			Integer eindex) {
		IUser user = userDao.getUserByUsername(username);

		List<IEvent> events = eventDao.getEventsByReceiver(user);
		List<IEvent> notices = new ArrayList<IEvent>();
		for (IEvent event : events) {
			if (event instanceof ICommentEvent
					|| event instanceof IFollowerEvent)
				notices.add(event);
		}

		return ServiceHelper.getRange(notices, sindex, eindex, true, true);
	}

	@Transactional
	public boolean read(String username, long id) {
		IEvent event = eventDao.getEventById(id);
		if (event == null)
			return false;

		if (!event.getReceiver().getUsername().equals(username))
			return false;

		if (event.isRead())
			return false;

		event.setRead(true);
		return true;
	}
}
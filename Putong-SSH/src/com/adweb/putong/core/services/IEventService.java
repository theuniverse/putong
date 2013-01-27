package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.beans.events.IRecordEvent;
import com.adweb.putong.core.beans.events.IWeiboEvent;

public interface IEventService {

	public List<IWeiboEvent> listTimeline(String username, Integer sindex,
			Integer eindex);

	public List<IRecordEvent> listNewthings(String username, Integer sindex,
			Integer eindex);

	public List<IEvent> listNotice(String username, Integer sindex,
			Integer eindex);

	public boolean read(String username, long id);

}

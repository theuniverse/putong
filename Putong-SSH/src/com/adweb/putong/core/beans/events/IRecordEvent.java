package com.adweb.putong.core.beans.events;

import com.adweb.putong.core.beans.IRecord;

public interface IRecordEvent extends IEvent{

	public IRecord getRecord();
	public void setRecord(IRecord record);
	
}

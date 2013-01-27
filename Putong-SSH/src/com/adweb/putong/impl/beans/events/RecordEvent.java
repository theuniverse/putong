package com.adweb.putong.impl.beans.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.events.IRecordEvent;
import com.adweb.putong.impl.beans.Record;

@Entity
@DiscriminatorValue("Record")
public class RecordEvent extends Event implements IRecordEvent {

	@ManyToOne(targetEntity = Record.class)
	private IRecord record;
	
	@Override
	public IRecord getRecord() {
		return record;
	}

	@Override
	public void setRecord(IRecord record) {
		this.record = record;
	}

}

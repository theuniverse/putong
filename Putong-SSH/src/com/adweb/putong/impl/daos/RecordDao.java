package com.adweb.putong.impl.daos;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IRecordDao;
import com.iiiss.template.ssh.common.impl.BaseDao;

@Transactional
public class RecordDao extends BaseDao implements IRecordDao {
	
	@Autowired
	private IEventDao eventDao;
	
	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	@Override
	public IRecord createRecord(IUser user, IChatroom chatroom) {
		Date date = new Date();
		
		IRecord record = persistenceManager.newObject("record");		
		record.setUser(user);
		record.setChatroom(chatroom);		
		record.setEnterTime(date);
		record.setLeaveTime(date);
		record.setStatus(true);
		
		return record;
	}

	@Override
	public IRecord getRecordById(long id) {
		return persistenceManager.getObject("record", id);
	}

	@Override
	public List<IRecord> getRecordsByUser(IUser user) {
		return persistenceManager.getObjects("record", "where user = ?", user);
	}

	@Override
	public List<IRecord> getRecordsByChatroom(IChatroom chatroom) {
		return persistenceManager.getObjects("record", "where chatroom = ?", chatroom);
	}

	@Override
	public void dropRecord(IRecord record) {
		eventDao.dropEvents(record);
		persistenceManager.deteleObject(record);
	}
	
}

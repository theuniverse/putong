package com.adweb.putong.impl.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.daos.IChatroomDao;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IRecordDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IRecordService;

public class RecordService implements IRecordService {
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IChatroomDao chatroomDao;
	
	@Autowired
	IRecordDao recordDao;
	
	@Autowired
	IEventDao eventDao;
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setChatroomDao(IChatroomDao chatroomDao) {
		this.chatroomDao = chatroomDao;
	}
	
	public void setRecordDao(IRecordDao recordDao) {
		this.recordDao = recordDao;
	}
	
	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	@Transactional
	public IRecord put(String username, String title, String url) {
		IUser user = userDao.getUserByUsername(username);
		IChatroom chatroom = chatroomDao.getChatroomByUrl(url);
		
		if(user == null){
			return null;
		}			
		if(chatroom == null){
			chatroom = chatroomDao.createChatroom(title, url);
		}
		
		IRecord record = recordDao.createRecord(user, chatroom);
		
		for (IUser friend : userDao.getFollowers(user))
			eventDao.createEvent(friend, record);
		
		eventDao.createEvent(user, record);		
		return record;
	}

	@Override
	public IRecord get(long id) {
		return recordDao.getRecordById(id);
	}

	@Override
	@Transactional
	public boolean update(long id, Boolean status, String username) {
		IRecord record = recordDao.getRecordById(id);
		if(record == null)
			return false;
		
		if(!record.getUser().getUsername().equals(username))
			return false;
		
		record.setStatus(status);
		
		return true;
	}

	@Override
	@Transactional
	public boolean drop(long id, String username) {
		IRecord record = recordDao.getRecordById(id);
		IUser user = userDao.getUserByUsername(username);
		if (record == null
				|| !record.getUser().getUsername().equals(user.getUsername()))
			return false;
		
		recordDao.dropRecord(record);
		return true;
	}

	@Override
	@Transactional
	public List<IRecord> list(String username, String target, Integer sindex,
			Integer eindex) {
		if (target == null)
			target = username;

		IUser user = userDao.getUserByUsername(target);
		if (user == null)
			return null;

		List<IRecord> records = recordDao.getRecordsByUser(user);
		List<IRecord> results = new ArrayList<IRecord>();
		for (IRecord record : records)
				results.add(record);
		return ServiceHelper.getRange(results, sindex, eindex, true, true);
	}

}

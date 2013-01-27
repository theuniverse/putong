package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.core.IDao;

public interface IRecordDao extends IDao {

	public IRecord createRecord(IUser user, IChatroom chatroom);
	
	public IRecord getRecordById(long id);	
	public List<IRecord> getRecordsByUser(IUser user);
	public List<IRecord> getRecordsByChatroom(IChatroom chatroom);
	
	public void dropRecord(IRecord record);
	
}

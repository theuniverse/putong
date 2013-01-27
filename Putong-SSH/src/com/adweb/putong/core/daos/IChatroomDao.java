package com.adweb.putong.core.daos;

import com.adweb.putong.core.beans.IChatroom;
import com.iiiss.template.ssh.common.core.IDao;

public interface IChatroomDao extends IDao {

	public IChatroom createChatroom(String title, String url);
	
	public IChatroom getChatroomById(long id);
	public IChatroom getChatroomByUrl(String url);
	
	public void dropChatroom(IChatroom chatroom);
	
}

package com.adweb.putong.impl.daos;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IChatroom;
import com.adweb.putong.core.daos.IChatroomDao;
import com.iiiss.template.ssh.common.impl.BaseDao;

@Transactional
public class ChatroomDao extends BaseDao implements IChatroomDao {

	@Override
	public IChatroom createChatroom(String title, String url) {
		IChatroom chatroom = persistenceManager.newObject("chatroom");
		chatroom.setTitle(title);
		chatroom.setUrl(url);
		return chatroom;
	}

	@Override
	public IChatroom getChatroomById(long id) {
		return persistenceManager.getObject("chatroom", id);
	}
	
	@Override
	public IChatroom getChatroomByUrl(String url) {
		return persistenceManager.getObject("chatroom", "where url = ?", url);
	}

	@Override
	public void dropChatroom(IChatroom chatroom) {
		persistenceManager.deteleObject(chatroom);
	}

}

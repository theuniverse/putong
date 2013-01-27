package com.adweb.putong.impl.daos;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.ICommentDao;
import com.adweb.putong.core.daos.IEventDao;

@Transactional
public class CommentDao extends BaseDao implements ICommentDao {
	private IEventDao eventDao;

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	public IComment createComment(IUser author, IWeibo weibo, String content) {
		IComment comment = persistenceManager.newObject("comment");
		comment.setAuthor(author);
		comment.setWeibo(weibo);
		comment.setContent(content);
		comment.setTime(new Date());
		weibo.getComments().add(comment);
		return comment;
	}

	public IComment getCommentById(long commentid) {
		return persistenceManager.getObject("comment", commentid);
	}

	public void dropComment(IComment comment) {
		eventDao.dropEvents(comment);
		persistenceManager.deteleObject(comment);
	}
}

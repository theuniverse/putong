package com.adweb.putong.impl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.daos.ICommentDao;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.daos.IWeiboDao;
import com.adweb.putong.core.services.ICommentService;

public class CommentService implements ICommentService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IWeiboDao weiboDao;

	@Autowired
	private ICommentDao commentDao;

	@Autowired
	private IEventDao eventDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setWeiboDao(IWeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}

	public void setCommentDao(ICommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Transactional
	public IComment put(long weiboid, String username, String content) {
		IUser user = userDao.getUserByUsername(username);
		IWeibo weibo = weiboDao.getWeiboById(weiboid);
		if (weibo == null)
			return null;

		IComment comment = commentDao.createComment(user, weibo, content);

		// create comment event
		if (comment.getWeibo().getAuthor() != user)
			eventDao.createEvent(comment.getWeibo().getAuthor(), comment);
		return comment;
	}

	@Transactional
	public int count(long weiboid) {
		IWeibo weibo = weiboDao.getWeiboById(weiboid);
		if (weibo == null)
			return -1;

		return weibo.getComments().size();
	}

	@Transactional
	public List<IComment> list(long weiboid, Integer sindex, Integer eindex) {
		IWeibo weibo = weiboDao.getWeiboById(weiboid);
		if (weibo == null)
			return null;

		List<IComment> comments = weibo.getComments();
		return ServiceHelper.getRange(comments, sindex, eindex, true, false);
	}

	@Transactional
	public boolean drop(long commentid, String username) {
		IComment comment = commentDao.getCommentById(commentid);
		if (comment == null)
			return false;

		IUser user = userDao.getUserByUsername(username);
		if (comment.getAuthor() != user
				&& comment.getWeibo().getAuthor() != user)
			return false;

		comment.getWeibo().getComments().remove(comment);
		commentDao.dropComment(comment);
		return true;
	}
}

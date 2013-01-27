package com.adweb.putong.core.daos;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.core.IDao;

public interface ICommentDao extends IDao {
	public IComment createComment(IUser author, IWeibo weibo, String content);

	public IComment getCommentById(long commentid);

	public void dropComment(IComment comment);
}
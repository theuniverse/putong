package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IComment;

public interface ICommentService {

	public IComment put(long weiboid, String username, String content);

	public int count(long weiboid);

	public List<IComment> list(long weiboid, Integer sindex, Integer eindex);

	public boolean drop(long commentid, String username);

}

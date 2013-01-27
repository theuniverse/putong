package com.adweb.putong.core.beans.events;

import com.adweb.putong.core.beans.IComment;

public interface ICommentEvent extends IEvent {

	public IComment getComment();

	public void setComment(IComment comment);

}
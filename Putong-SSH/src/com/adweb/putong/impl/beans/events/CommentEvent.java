package com.adweb.putong.impl.beans.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.events.ICommentEvent;
import com.adweb.putong.impl.beans.Comment;

@Entity
@DiscriminatorValue("Comment")
public class CommentEvent extends Event implements ICommentEvent {
	@ManyToOne(targetEntity = Comment.class)
	private IComment comment;

	public IComment getComment() {
		return comment;
	}

	public void setComment(IComment comment) {
		this.comment = comment;
	}
}

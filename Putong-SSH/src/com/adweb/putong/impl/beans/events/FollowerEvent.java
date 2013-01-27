package com.adweb.putong.impl.beans.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.events.IFollowerEvent;
import com.adweb.putong.impl.beans.User;

@Entity
@DiscriminatorValue("Follower")
public class FollowerEvent extends Event implements IFollowerEvent {
	@OneToOne(targetEntity = User.class)
	private IUser follower;

	public IUser getFollower() {
		return follower;
	}

	public void setFollower(IUser follower) {
		this.follower = follower;
	}
}

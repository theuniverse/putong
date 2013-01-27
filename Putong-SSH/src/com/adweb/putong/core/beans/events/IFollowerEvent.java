package com.adweb.putong.core.beans.events;

import com.adweb.putong.core.beans.IUser;

public interface IFollowerEvent extends IEvent {

	public IUser getFollower();

	public void setFollower(IUser follower);

}
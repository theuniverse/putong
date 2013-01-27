package com.adweb.putong.core.beans.events;

import com.adweb.putong.core.beans.IWeibo;

public interface IWeiboEvent extends IEvent {

	public IWeibo getWeibo();

	public void setWeibo(IWeibo weibo);

}
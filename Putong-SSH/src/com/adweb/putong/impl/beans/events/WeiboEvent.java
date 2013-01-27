package com.adweb.putong.impl.beans.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.beans.events.IWeiboEvent;
import com.adweb.putong.impl.beans.Weibo;

@Entity
@DiscriminatorValue("Weibo")
public class WeiboEvent extends Event implements IWeiboEvent {
	@ManyToOne(targetEntity = Weibo.class)
	private IWeibo weibo;

	public IWeibo getWeibo() {
		return weibo;
	}

	public void setWeibo(IWeibo weibo) {
		this.weibo = weibo;
	}
}

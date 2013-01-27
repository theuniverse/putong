package com.adweb.putong.impl.controllers.json;

import com.adweb.putong.core.beans.IUser;

public class JsonUserInfo extends JsonObject {
	private String username;
	private String nickname;
	private String avatar;
	private Integer following;
	private Integer followed;
	private Integer weibos;

	public JsonUserInfo(IUser user) {
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.avatar = (user.getAvatar() != null) ? user.getAvatar().getUrl()
				: null;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		return nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	public Integer getFollowed() {
		return followed;
	}

	public void setFollowed(Integer followed) {
		this.followed = followed;
	}

	public Integer getWeibos() {
		return weibos;
	}

	public void setWeibos(Integer weibos) {
		this.weibos = weibos;
	}
}
package com.adweb.putong.impl.controllers.json;

public class JsonUser extends JsonObject {
	
	private String username;
	private String nickname;
	private String avatar;
	private String email;
	private String bio;
	private String gender;
	private Integer following;
	private Integer followed;
	private Integer pages;
	
	public JsonUser() {
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		if(gender == null){
			this.gender = "s";
		}else{
			this.gender = gender ? "m" : "f";
		}
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

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}	
}

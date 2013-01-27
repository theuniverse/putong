package com.adweb.putong.impl.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.adweb.putong.core.beans.IImage;
import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.impl.beans.SnsUser;

@Entity(name = "Account")
public class User extends SnsUser implements IUser {
	@Basic
	private String nickname;
	
	@Basic
	private String email;
	
	@Basic
	private Boolean gender;
	
	@Basic
	private String bio;
	
	@Basic
	private Boolean seen;
	
	@Basic
	private Boolean activated;

	@OneToOne(targetEntity = Image.class, cascade = { CascadeType.ALL })
	private IImage avatar;

	@ManyToMany(targetEntity = Mail.class, mappedBy = "users")
	private List<IMail> mails = new ArrayList<IMail>();

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public IImage getAvatar() {
		return avatar;
	}

	public void setAvatar(IImage avatar) {
		this.avatar = avatar;
	}

	public List<IMail> getMails() {
		return mails;
	}

	public void setMails(List<IMail> mails) {
		this.mails = mails;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Boolean getGender() {
		return gender;
	}

	@Override
	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	@Override
	public String getBio() {
		return bio;
	}

	@Override
	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public Boolean getSeen() {
		return seen;
	}

	@Override
	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	@Override
	public Boolean getActivated() {
		return activated;
	}

	@Override
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
}

package com.adweb.putong.core.beans;

import java.util.List;

import com.iiiss.template.ssh.core.beans.ISnsUser;

public interface IUser extends ISnsUser {

	public String getNickname();
	public void setNickname(String nickname);

	public IImage getAvatar();
	public void setAvatar(IImage avatar);

	public List<IMail> getMails();
	public void setMails(List<IMail> mails);
	
	public String getEmail();
	public void setEmail(String email);
	
	public Boolean getGender();
	public void setGender(Boolean gender);
	
	public String getBio();
	public void setBio(String Bio);	
	
	public Boolean getSeen();
	public void setSeen(Boolean seen);
	
	public Boolean getActivated();
	public void setActivated(Boolean activated);

}
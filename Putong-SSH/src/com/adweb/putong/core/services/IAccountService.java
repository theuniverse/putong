package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IUser;

public interface IAccountService {

	public boolean registerCheck(String username);

	public boolean register(String username, String password, String email);

	public boolean login(String username, String password);

	public IUser userinfo(String username, String target);

	public boolean update(String username, String new_password,
			String new_nickname, String new_avatar, String new_email,
			String new_bio, Boolean new_gender, Boolean new_seen);

	public List<IUser> searchUsers(String keyword, Integer sindex,
			Integer eindex);

	boolean activate(String username, String code);
	boolean isActivated(String username);
}

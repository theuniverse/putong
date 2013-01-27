package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IBlockUser;

public interface IBlockUserService {

	public IBlockUser put(String username, String reason);
	public IBlockUser get(long id);
	public boolean drop(long id);
	
	public List<IBlockUser> list();
	
	public boolean checkUser(String username);
}

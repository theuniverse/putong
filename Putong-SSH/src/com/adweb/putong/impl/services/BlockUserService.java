package com.adweb.putong.impl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.daos.IBlockUserDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IBlockUserService;

public class BlockUserService implements IBlockUserService {
	
	@Autowired
	IBlockUserDao blockUserDao;
	
	@Autowired
	IUserDao userDao;
	
	public void setBlockUserDao(IBlockUserDao blockUserDao){
		this.blockUserDao = blockUserDao;
	}
	
	public void setUserDao(IUserDao userDao){
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public IBlockUser put(String username, String reason) {
		System.out.println(username+","+reason);
		if(username == null)
			return null;
		
		IUser user = userDao.getUserByUsername(username);
		if(user == null)
			return null;
		
		if(reason == null)
			reason = "";
		
		IBlockUser blockUser = blockUserDao.createBlockUser(user, reason);
		return blockUser;
	}

	@Override
	public IBlockUser get(long id) {
		return blockUserDao.getBlockUserById(id);
	}

	@Override
	@Transactional
	public boolean drop(long id) {
		IBlockUser blockUser = blockUserDao.getBlockUserById(id);
		if(blockUser == null)
			return false;
		
		blockUserDao.dropBlockUser(blockUser);
		return true;
	}

	@Override
	@Transactional
	public List<IBlockUser> list() {
		return blockUserDao.getAllBlockUsers();
	}

	@Override
	public boolean checkUser(String username) {
		if(username == null)
			return false;
		
		IUser user = userDao.getUserByUsername(username);
		if(user == null)
			return false;
		
		List<IBlockUser> blockUsers = blockUserDao.getAllBlockUsers();
		for(IBlockUser blockUser: blockUsers){
			if(blockUser.getUser().getUsername().equals(user.getUsername()))
				return false;
		}
		
		return true;
	}

}

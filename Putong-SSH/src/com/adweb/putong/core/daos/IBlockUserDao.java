package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.core.IDao;

public interface IBlockUserDao extends IDao {

	public IBlockUser createBlockUser(IUser user, String reason);
	
	public IBlockUser getBlockUserById(long id);
	public List<IBlockUser> getAllBlockUsers();
	
	public void dropBlockUser(IBlockUser blockUser);
	
}

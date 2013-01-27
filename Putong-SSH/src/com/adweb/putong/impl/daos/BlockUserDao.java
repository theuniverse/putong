package com.adweb.putong.impl.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.daos.IBlockUserDao;
import com.iiiss.template.ssh.common.impl.BaseDao;

@Transactional
public class BlockUserDao extends BaseDao implements IBlockUserDao {

	@Override
	public IBlockUser createBlockUser(IUser user, String reason) {
		Date date = new Date();
		
		IBlockUser blockUser = persistenceManager.newObject("blockuser");
		blockUser.setUser(user);
		blockUser.setReason(reason);
		blockUser.setTime(date);
		return blockUser;
	}

	@Override
	public IBlockUser getBlockUserById(long id) {
		return persistenceManager.getObject("blockuser", id);
	}

	@Override
	public List<IBlockUser> getAllBlockUsers() {
		List<IBlockUser> users = persistenceManager.getObjects("blockuser", null, null);
		return (users == null)?new ArrayList<IBlockUser>():users;
	}

	@Override
	public void dropBlockUser(IBlockUser blockUser) {
		persistenceManager.deteleObject(blockUser);
	}

}

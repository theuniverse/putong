package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.core.IDao;
import com.iiiss.template.ssh.core.beans.ISnsUser;

public interface IUserDao extends IDao {

	public IUser createUser(String username, String password, String nickname, String email);

	public <T extends com.iiiss.template.ssh.core.beans.IUser> T getUserByUsername(
			String username);

	public <T extends ISnsUser> List<T> getFollowers(T targetUser);
	
	public List<IUser> searchUserByKeyword(String keyword);

}
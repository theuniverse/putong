package com.adweb.putong.impl.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.impl.daos.SnsUserDao;
import com.adweb.putong.core.daos.IUserDao;

@Transactional
public class UserDao extends SnsUserDao implements IUserDao {
	
	@Override
	public IUser createUser(String username, String password, String nickname, String email) {
		IUser user = super.createUser(username, password);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setSeen(true);
		user.setActivated(false);
		return user;
	}
	
	public List<IUser> searchUserByKeyword(String keyword){
		List<IUser> userByNickname = persistenceManager.getObjects(BEAN_NAME,
				"where nickname like ?", "%" + keyword + "%");
		
		List<IUser> userByBio = persistenceManager.getObjects(BEAN_NAME, "where bio like ?", "%" + keyword + "%");
		List<IUser> result = new ArrayList<IUser>();
		int minLength = Math.min(userByNickname.size(), userByBio.size());
		int sizeNickname = userByNickname.size(), sizeBio = userByBio.size();
		
		int i=0;
		for(; i<minLength; i++){
			if(!result.contains(userByNickname.get(i))){
				result.add(userByNickname.get(i));
			}
			
			if(!result.contains(userByBio.get(i))){
				result.add(userByBio.get(i));
			}			
		}
		
		if(minLength < sizeNickname){
			for(;i<sizeNickname;i++){
				if(!result.contains(userByNickname.get(i))){
					result.add(userByNickname.get(i));
				}
			}
		}
		
		if(minLength < sizeBio){
			for(;i<sizeBio;i++){
				if(!result.contains(userByBio.get(i))){
					result.add(userByBio.get(i));
				}
			}
		}
		
		return result; 
	}
}

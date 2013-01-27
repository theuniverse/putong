package com.adweb.putong.impl.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.impl.services.UserService;
import com.adweb.putong.core.daos.IImageDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IAccountService;
import com.adweb.putong.util.UtilityTools;

public class AccountService extends UserService implements IAccountService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IImageDao imageDao;

	public void setUserDao(IUserDao userDao) {
		super.setUserDao((com.iiiss.template.ssh.core.daos.IUserDao) userDao);
		this.userDao = userDao;
	}

	public void setImageDao(IImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public boolean registerCheck(String username) {
		if (userDao.getUserByUsername(username) != null)
			return false;
		else
			return true;
	}

	@Override
	public boolean register(String username, String password, String email) {

		if (userDao.getUserByUsername(username) != null)
			return false;
		
		//String hashedPassword = UtilityTools.createHashPassword(password);
		//System.out.println(hashedPassword);
		userDao.createUser(username, password, username, email);
		return true;
	}
	
	@Override
	public boolean login(String username, String password) {
		//System.out.println("[AUTH]:" + System.nanoTime());
		boolean result = true;
		long startTime = System.nanoTime();
		IUser user = userDao.getUserByUsername(username);
		if (user == null){
			System.out.println("[FIND FAILED]:" + (long)(System.nanoTime()-startTime)/1000);
			result = false;
		}
		
		//System.out.println("[FIND SUCCESS]:" + (long)(System.nanoTime()-startTime)/1000);

		if(result){
			if (UtilityTools.checkPassword(password, user.getPassword())){
				System.out.println("[PASS RIGHT]:" + (long)(System.nanoTime()-startTime)/1000);
				return true;
			}else{
				System.out.println("[PASS FAILED]:" + (long)(System.nanoTime()-startTime)/1000);
				return false;
			}
		}else{
			int times = 100000 + (int)(10000 * Math.random()), c=0;
			for(int i = 0; i<times; i++){
				c += i;
			}
			if (UtilityTools.checkPassword("xiqi1990", "291c10cc588cc325fa8036c8,37b1e7d84443151ed0842dcea2265cc6")){
				System.out.println("[FAKE RIGHT]:" + (long)(System.nanoTime()-startTime)/1000);
				return false;
			}else{
				System.out.println("[FAKE FAILED]:" + (long)(System.nanoTime()-startTime)/1000);
				return false;
			}
		}
		
	}

	@Transactional
	public boolean update(String username, String new_password,
			String new_nickname, String new_avatar, String new_email,
			String new_bio, Boolean new_gender, Boolean new_seen) {
		IUser user = userDao.getUserByUsername(username);
		if (user == null)
			return false;

		if (new_password != null)
			user.setPassword(new_password);

		if (new_nickname != null)
			user.setNickname(new_nickname);

		if (new_avatar != null)
			imageDao.createImage(user, new_avatar);
		
		if (new_email != null)
			user.setEmail(new_email);
		
		user.setGender(new_gender);
		
		if (new_seen != null)
			user.setSeen(new_seen);
		
		if (new_bio != null)
			user.setBio(new_bio);

		return true;
	}

	public IUser userinfo(String username, String target) {
		if (target == null)
			target = username;

		return userDao.getUserByUsername(target);
	}

	public List<IUser> searchUsers(String keyword, Integer sindex,
			Integer eindex) {
		List<IUser> users = super.search(keyword);
		List<IUser> keywordUsers = userDao.searchUserByKeyword(keyword);
		
		System.out.println(users.size()+","+keywordUsers.size());
		
		List<IUser> result = new ArrayList<IUser>();
		Map<String,IUser> uniqueUsers = new ConcurrentHashMap<String, IUser>();
		
		for(IUser user: users){
			uniqueUsers.put(user.getUsername(), user);
			result.add(user);
		}
		
		for(IUser user: keywordUsers){
			if(uniqueUsers.get(user.getUsername()) == null)
				result.add(user);
		}
		
		return ServiceHelper.getRange(result, sindex, eindex, true, false);
	}
	
	@Override
	@Transactional
	public boolean activate(String username, String code) {
		IUser user = userDao.getUserByUsername(username);
		if (user == null) {
			return false;
		}
		
		String password = user.getPassword();
		String validateCode = UtilityTools.generateMD5(username+password);
		if(!validateCode.equals(code)) {
			return false;
		}
		
		System.out.println(user.getActivated());
		
		user.setActivated(true);
		
		System.out.println(user.getActivated());
		
		return true;
	}
	
	@Override
	public boolean isActivated(String username) {
		IUser user = userDao.getUserByUsername(username);
		if(user == null) {
			return false;
		}
		
		if(!user.getActivated())
			return false;
		
		return true;
	}
}

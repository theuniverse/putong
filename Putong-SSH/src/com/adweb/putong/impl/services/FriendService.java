package com.adweb.putong.impl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.impl.services.SnsUserService;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IFriendService;

public class FriendService extends SnsUserService implements IFriendService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IEventDao eventDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Transactional
	public boolean follow(String username, String target) {
		try {
			if (super.addFriend(username, target)) {
				IUser currentUser = userDao.getUserByUsername(username);
				IUser targetUser = userDao.getUserByUsername(target);
				eventDao.createEvent(targetUser, currentUser);
				return true;
			} else
				return false;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean unfollow(String username, String target) {
		try {
			return super.removeFriend(username, target);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public List<IUser> following(String username, String target,
			Integer sindex, Integer eindex) {
		try {
			List<IUser> friends = super.getFriends(username, target);
			return ServiceHelper.getRange(friends, sindex, eindex, true, false);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public List<IUser> follower(String username, String target, Integer sindex,
			Integer eindex) {
		try {
			List<IUser> followers = super.getFollowers(username, target);
			return ServiceHelper.getRange(followers, sindex, eindex, true,
					false);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public int followingCount(String username, String target) {
		try {
			return super.countFriends(username, target);
		} catch (NullPointerException e) {
			return -1;
		}
	}

	public int followerCount(String username, String target) {
		try {
			return super.countFollowers(username, target);
		} catch (NullPointerException e) {
			return -1;
		}
	}

	public boolean isFollowerOfTarget(String username, String target) {
		try {
			return super.isFriend(username, target);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean isFollowedByTarget(String username, String target) {
		try {
			return super.isFriend(target, username);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public List<IUser> searchFollowing(String username, String keyword,
			Integer sindex, Integer eindex) {
		try {
			List<IUser> friends = super.searchFriends(username, username,
					keyword);
			return ServiceHelper.getRange(friends, sindex, eindex, true, false);
		} catch (NullPointerException e) {
			return null;
		}
	}
}

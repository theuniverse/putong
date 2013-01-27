package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IUser;

public interface IFriendService {

	public boolean follow(String username, String target);

	public boolean unfollow(String username, String target);

	public List<IUser> following(String username, String target,
			Integer sindex, Integer eindex);

	public List<IUser> follower(String username, String target, Integer sindex,
			Integer eindex);

	public int followingCount(String username, String target);

	public int followerCount(String username, String target);

	public boolean isFollowerOfTarget(String username, String target);

	public boolean isFollowedByTarget(String username, String target);

	public List<IUser> searchFollowing(String username, String keyword,
			Integer sindex, Integer eindex);

}

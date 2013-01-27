package com.adweb.putong.impl.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IImageDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.daos.IVideoDao;
import com.adweb.putong.core.daos.IWeiboDao;
import com.adweb.putong.core.services.IWeiboService;

public class WeiboService implements IWeiboService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IWeiboDao weiboDao;

	@Autowired
	private IImageDao imageDao;

	@Autowired
	private IVideoDao videoDao;

	@Autowired
	private IEventDao eventDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setWeiboDao(IWeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}

	public void setImageDao(IImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public void setVideoDao(IVideoDao videoDao) {
		this.videoDao = videoDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Transactional
	public IWeibo put(String username, String content, String video,
			List<String> imagelist) {
		IUser author = userDao.getUserByUsername(username);
		IWeibo weibo = weiboDao.createWeibo(author, content);

		if (video != null)
			videoDao.createVideo(weibo, video);

		if (imagelist != null)
			for (String imageurl : imagelist)
				imageDao.createImage(weibo, imageurl);

		// create weibo event
		for (IUser user : userDao.getFollowers(author))
			eventDao.createEvent(user, weibo);
		eventDao.createEvent(author, weibo);
		return weibo;
	}

	public IWeibo get(long weiboid) {
		return weiboDao.getWeiboById(weiboid);
	}

	@Transactional
	public int count(String username, String target) {
		if (target == null)
			target = username;

		IUser user = userDao.getUserByUsername(target);
		if (user == null)
			return -1;

		List<IWeibo> weibos = weiboDao.getWeibosByAuthor(user);
		int count = 0;
		for (IWeibo weibo : weibos)
			if (!weibo.isDeleted())
				count++;
		return count;
	}

	@Transactional
	public List<IWeibo> list(String username, String target, Integer sindex,
			Integer eindex) {
		if (target == null)
			target = username;

		IUser user = userDao.getUserByUsername(target);
		if (user == null)
			return null;

		List<IWeibo> weibos = weiboDao.getWeibosByAuthor(user);
		List<IWeibo> results = new ArrayList<IWeibo>();
		for (IWeibo weibo : weibos)
			if (!weibo.isDeleted())
				results.add(weibo);
		return ServiceHelper.getRange(results, sindex, eindex, true, true);
	}

	@Transactional
	public IWeibo forward(long weiboid, String username, String content) {
		IUser author = userDao.getUserByUsername(username);
		IWeibo weibo = weiboDao.getWeiboById(weiboid);
		if (weibo == null)
			return null;

		IWeibo forwarding = weiboDao.forwardWeibo(author, weibo, content);

		// create forwarding event
		for (IUser user : userDao.getFollowers(author))
			eventDao.createEvent(user, forwarding);
		eventDao.createEvent(author, forwarding);
		return forwarding;
	}

	@Transactional
	public boolean drop(long weiboid, String username) {
		IUser user = userDao.getUserByUsername(username);
		IWeibo weibo = weiboDao.getWeiboById(weiboid);
		if (weibo == null
				|| !weibo.getAuthor().getUsername().equals(user.getUsername()))
			return false;

		if (weibo.isForwarding()) {
			if (weibo.getPredecessor() != null) {
				weibo.getPredecessor().setReferences(
						weibo.getPredecessor().getReferences() - 1);
			}
			weibo.getOrigin().setReferences(
					weibo.getOrigin().getReferences() - 1);
		}

		weibo.setDeleted(true);
		recursiveDrop(weibo);
		return true;
	}

	@Transactional
	private void recursiveDrop(IWeibo weibo) {
		if (!weibo.isDeleted() || weibo.getReferences() != 0)
			return;

		if (weibo.isForwarding()) {
			IWeibo origin = weibo.getOrigin();
			IWeibo predecessor = weibo.getPredecessor();

			weiboDao.dropWeibo(weibo);
			if (predecessor != null)
				recursiveDrop(predecessor);
			recursiveDrop(origin);
		} else {
			weiboDao.dropWeibo(weibo);
		}
	}
}

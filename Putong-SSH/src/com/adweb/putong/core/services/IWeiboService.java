package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IWeibo;

public interface IWeiboService {

	public IWeibo put(String username, String content, String video,
			List<String> imagelist);

	public IWeibo get(long weiboid);

	public int count(String username, String target);

	public List<IWeibo> list(String username, String target, Integer sindex,
			Integer eindex);

	public IWeibo forward(long weiboid, String username, String content);

	public boolean drop(long weiboid, String username);

}

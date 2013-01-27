package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.core.IDao;

public interface IWeiboDao extends IDao {

	public IWeibo createWeibo(IUser author, String content);

	public IWeibo forwardWeibo(IUser author, IWeibo weibo, String content);

	public IWeibo getWeiboById(long id);
	
	public List<IWeibo> getWeibosByAuthor(IUser author);

	public void dropWeibo(IWeibo weibo);

}
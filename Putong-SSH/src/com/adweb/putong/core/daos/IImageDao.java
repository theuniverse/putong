package com.adweb.putong.core.daos;

import com.adweb.putong.core.beans.IImage;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.core.IDao;

public interface IImageDao extends IDao {

	public IImage createImage(IUser user, String url);

	public IImage createImage(IWeibo weibo, String url);

}
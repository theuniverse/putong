package com.adweb.putong.core.daos;

import com.adweb.putong.core.beans.IVideo;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.core.IDao;

public interface IVideoDao extends IDao {

	public IVideo createVideo(IWeibo weibo, String url);

}
package com.adweb.putong.impl.daos;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IVideo;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.IVideoDao;

@Transactional
public class VideoDao extends BaseDao implements IVideoDao {
	public IVideo createVideo(IWeibo weibo, String url) {
		IVideo video = persistenceManager.newObject("video");
		video.setUrl(url);
		weibo.setVideo(video);
		return video;
	}
}

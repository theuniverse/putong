package com.adweb.putong.impl.daos;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IImage;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.IImageDao;

@Transactional
public class ImageDao extends BaseDao implements IImageDao {
	public IImage createImage(IUser user, String url) {
		IImage image = persistenceManager.newObject("image");
		image.setUrl(url);
		user.setAvatar(image);
		return image;
	}

	public IImage createImage(IWeibo weibo, String url) {
		IImage image = persistenceManager.newObject("image");
		image.setUrl(url);
		weibo.getImages().add(image);
		return image;
	}
}

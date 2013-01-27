package com.adweb.putong.impl.daos;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.IEventDao;
import com.adweb.putong.core.daos.IWeiboDao;

@Transactional
public class WeiboDao extends BaseDao implements IWeiboDao {
	private IEventDao eventDao;

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	public IWeibo createWeibo(IUser author, String content) {
		IWeibo weibo = persistenceManager.newObject("weibo");
		weibo.setContent(content);
		weibo.setAuthor(author);
		weibo.setTime(new Date());
		return weibo;
	}

	public IWeibo forwardWeibo(IUser author, IWeibo predecessor, String content) {
		IWeibo forwarding = createWeibo(author, content);

		if (predecessor.isForwarding()) {
			forwarding.setPredecessor(predecessor);
			forwarding.setOrigin(predecessor.getOrigin());
			predecessor.setReferences(predecessor.getReferences() + 1);
			predecessor.getOrigin().setReferences(
					predecessor.getOrigin().getReferences() + 1);
		} else {
			forwarding.setOrigin(predecessor);
			predecessor.setReferences(predecessor.getReferences() + 1);
		}

		return forwarding;
	}

	public List<IWeibo> getWeibosByAuthor(IUser author) {
		return persistenceManager.getObjects("weibo", "where author = ?",
				author);
	}

	public IWeibo getWeiboById(long id) {
		return persistenceManager.getObject("weibo", id);
	}

	public void dropWeibo(IWeibo weibo) {
		eventDao.dropEvents(weibo);
		persistenceManager.deteleObject(weibo);
	}
}

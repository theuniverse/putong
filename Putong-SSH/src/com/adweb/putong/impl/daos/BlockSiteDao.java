package com.adweb.putong.impl.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IBlockSite;
import com.adweb.putong.core.daos.IBlockSiteDao;
import com.iiiss.template.ssh.common.impl.BaseDao;

@Transactional
public class BlockSiteDao extends BaseDao implements IBlockSiteDao {

	@Override
	public IBlockSite createBlockSite(String title, String domain, String reason) {
		Date date = new Date();
		
		IBlockSite blockSite = persistenceManager.newObject("blocksite");
		blockSite.setTitle(title);
		blockSite.setDomain(domain);
		blockSite.setReason(reason);
		blockSite.setTime(date);
		return blockSite;
	}

	@Override
	public IBlockSite getBlockSiteById(long id) {
		return persistenceManager.getObject("blocksite", id);
	}

	@Override
	public List<IBlockSite> getAllBlockSites() {
		List<IBlockSite> sites = persistenceManager.getObjects("blocksite", "", null);
		return (sites == null) ? new ArrayList<IBlockSite>() : sites;
	}

	@Override
	public void dropBlockSite(IBlockSite blockSite) {
		persistenceManager.deteleObject(blockSite);
	}

}

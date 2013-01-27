package com.adweb.putong.impl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IBlockSite;
import com.adweb.putong.core.daos.IBlockSiteDao;
import com.adweb.putong.core.services.IBlockSiteService;

public class BlockSiteService implements IBlockSiteService {
	
	@Autowired
	IBlockSiteDao blockSiteDao;
	
	public void setBlockSiteDao(IBlockSiteDao blockSiteDao){
		this.blockSiteDao = blockSiteDao;
	}

	@Override
	@Transactional
	public IBlockSite put(String domain, String title, String reason) {
		if(domain == null)
			return null;
		
		if(title == null)
			title = domain;
		
		if(reason == null)
			reason = "";
		
		IBlockSite blockSite = blockSiteDao.createBlockSite(title, domain, reason);
		return blockSite;
	}

	@Override
	public IBlockSite get(long id) {
		return blockSiteDao.getBlockSiteById(id);
	}

	@Override
	@Transactional
	public boolean drop(long id) {
		IBlockSite blockSite = blockSiteDao.getBlockSiteById(id);
		if(blockSite == null)
			return false;
		
		blockSiteDao.dropBlockSite(blockSite);
		return true;
	}

	@Override
	@Transactional
	public List<IBlockSite> list() {
		return blockSiteDao.getAllBlockSites();
	}

	@Override
	public boolean checkSite(String url) {
		List<IBlockSite> blockSites = blockSiteDao.getAllBlockSites();
		for(IBlockSite blockSite: blockSites){
			boolean blocked = check(url, blockSite);
			if(blocked)
				return false;
		}
		return true;
	}

	private boolean check(String url, IBlockSite blockSite) {
		String domain = blockSite.getDomain();
		if(url.contains(domain))
			return true;
		
		return false;
	}

}

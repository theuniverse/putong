package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IBlockSite;
import com.iiiss.template.ssh.common.core.IDao;

public interface IBlockSiteDao extends IDao {

	public IBlockSite createBlockSite(String title, String domain, String reason);
	
	public IBlockSite getBlockSiteById(long id);
	public List<IBlockSite> getAllBlockSites();
	
	public void dropBlockSite(IBlockSite blockSite);
	
}

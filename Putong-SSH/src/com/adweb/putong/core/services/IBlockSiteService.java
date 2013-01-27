package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IBlockSite;

public interface IBlockSiteService {
	
	public IBlockSite put(String domain, String title, String reason);
	public IBlockSite get(long id);
	public boolean drop(long id);
	
	public List<IBlockSite> list();
	
	public boolean checkSite(String url);

}

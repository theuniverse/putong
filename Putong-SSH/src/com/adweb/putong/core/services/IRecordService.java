package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IRecord;

public interface IRecordService {

	public IRecord put(String username, String title, String url);
	public IRecord get(long id);
	public boolean update(long id, Boolean status, String username);
	public boolean drop(long id, String username);

	public List<IRecord> list(String username, String target, Integer sindex,
			Integer eindex);

}

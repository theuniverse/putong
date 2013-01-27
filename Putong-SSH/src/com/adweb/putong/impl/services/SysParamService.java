package com.adweb.putong.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.ISysParam;
import com.adweb.putong.core.daos.ISysParamDao;
import com.adweb.putong.core.services.ISysParamService;

@Service
public class SysParamService implements	ISysParamService {
	
	@Autowired
	ISysParamDao sysParamDao;
	
	public void setSysParamDao(ISysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public <T> T getParam(int type) {
		switch(type){
			case DAY_TO_RECORD:{
				return (T) sysParamDao.getSysParam().getRecordDay();
			}
			case ROOT_USERNAME:{
				return (T) sysParamDao.getSysParam().getRootUsername();
			}
			case ROOT_PASSWORD:{
				return (T) sysParamDao.getSysParam().getRootPassword();
			}
			case ROOT_EMAIL:{
				return (T) sysParamDao.getSysParam().getRootEmail();
			}
			case ROOT_EMAIL_PASSWORD:{
				return (T) sysParamDao.getSysParam().getEmailPassword();
			}
		}
		
		return null;
	}

	@Override
	@Transactional
	public <T> void setParam(int type, T param) {
		switch(type){
			case DAY_TO_RECORD:{
				sysParamDao.getSysParam().setRecordDay((Integer) param);
			}
			break;
			case ROOT_USERNAME:{
				sysParamDao.getSysParam().setRootUsername((String) param);
			}
			break;
			case ROOT_PASSWORD:{
				sysParamDao.getSysParam().setRootPassword((String) param);
			}
			break;
			case ROOT_EMAIL:{
				sysParamDao.getSysParam().setRootEmail((String) param);
			}
			break;
			case ROOT_EMAIL_PASSWORD:{
				sysParamDao.getSysParam().setEmailPassword((String) param);
			}
		}
	}
	
	@Override
	@Transactional
	public ISysParam get(){
		return sysParamDao.getSysParam();
	}
}

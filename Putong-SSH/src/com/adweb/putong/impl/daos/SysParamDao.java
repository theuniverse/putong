package com.adweb.putong.impl.daos;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.ISysParam;
import com.adweb.putong.core.daos.ISysParamDao;
import com.iiiss.template.ssh.common.impl.BaseDao;

@Transactional
public class SysParamDao extends BaseDao implements ISysParamDao {

	@Override
	public ISysParam getSysParam() {
		ISysParam sysParam = persistenceManager.getObject("sysparam", null, null);
		
		if(sysParam == null) {
			sysParam = persistenceManager.newObject("sysparam");
			sysParam.setRecordDay(5);
			sysParam.setEmailPassword("theuniverseblanc");
			sysParam.setRootEmail("199354400@qq.com");
			sysParam.setRootUsername("admin");
			sysParam.setRootPassword("admin");
		}
		
		return sysParam;
	}
	
}

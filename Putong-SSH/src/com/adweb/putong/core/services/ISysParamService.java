package com.adweb.putong.core.services;

import com.adweb.putong.core.beans.ISysParam;

public interface ISysParamService {
	
	public static final int DAY_TO_RECORD = 1000;
	public static final int ROOT_USERNAME = 1001;
	public static final int ROOT_PASSWORD = 1002;
	public static final int ROOT_EMAIL = 1003;
	public static final int ROOT_EMAIL_PASSWORD = 1004;
	
	public <T> T getParam(int type);
	public <T> void setParam(int type, T param);
	public ISysParam get();

}

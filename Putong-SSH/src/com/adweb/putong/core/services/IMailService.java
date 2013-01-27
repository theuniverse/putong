package com.adweb.putong.core.services;

import java.util.List;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;

public interface IMailService {

	public IMail putMail(String username, List<String> receivers, String topic,
			String content);

	public IMessage getLastMessage(long mailid);

	public List<IMail> listMail(String username, Integer sindex, Integer eindex);

	public IMessage putMessage(long mailid, String username, String content);

	public List<IMessage> listMessage(long mailid, Integer sindex,
			Integer eindex);

}

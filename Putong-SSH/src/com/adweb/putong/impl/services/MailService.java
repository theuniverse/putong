package com.adweb.putong.impl.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.daos.IMailDao;
import com.adweb.putong.core.daos.IUserDao;
import com.adweb.putong.core.services.IMailService;

public class MailService implements IMailService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IMailDao mailDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setMailDao(IMailDao mailDao) {
		this.mailDao = mailDao;
	}

	@Transactional
	public IMail putMail(String username, List<String> receivers, String topic,
			String content) {
		IUser currentUser = userDao.getUserByUsername(username);

		List<IUser> users = new ArrayList<IUser>();
		users.add(currentUser);
		for (String receiver : receivers) {
			IUser targetUser = userDao.getUserByUsername(receiver);
			if (targetUser == null)
				return null;
			users.add(targetUser);
		}

		IMail mail = mailDao.createMail(topic, users);
		mailDao.createMessage(mail, currentUser, content);
		return mail;
	}

	@Transactional
	public IMessage getLastMessage(long mailid) {
		IMail mail = mailDao.getMailById(mailid);
		List<IMessage> messages = mail.getMessages();
		Collections.sort(messages);
		return messages.get(messages.size() - 1);
	}

	@Transactional
	public List<IMail> listMail(String username, Integer sindex, Integer eindex) {
		IUser user = userDao.getUserByUsername(username);
		List<IMail> mails = user.getMails();
		return ServiceHelper.getRange(mails, sindex, eindex, true, true);
	}

	@Transactional
	public IMessage putMessage(long mailid, String username, String content) {
		IUser user = userDao.getUserByUsername(username);
		IMail mail = mailDao.getMailById(mailid);
		if (mail == null)
			return null;

		return mailDao.createMessage(mail, user, content);
	}

	@Transactional
	public List<IMessage> listMessage(long mailid, Integer sindex,
			Integer eindex) {
		IMail mail = mailDao.getMailById(mailid);
		if (mail == null)
			return null;

		List<IMessage> messages = mail.getMessages();
		return ServiceHelper.getRange(messages, sindex, eindex, true, true);
	}
}

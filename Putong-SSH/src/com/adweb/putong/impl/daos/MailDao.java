package com.adweb.putong.impl.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.impl.BaseDao;
import com.adweb.putong.core.daos.IMailDao;

@Transactional
public class MailDao extends BaseDao implements IMailDao {
	public IMail createMail(String topic, List<IUser> users) {
		IMail mail = persistenceManager.newObject("mail");
		mail.setTopic(topic);
		for (IUser user : users) {
			mail.getUsers().add(user);
			user.getMails().add(mail);
		}
		return mail;
	}

	public IMessage createMessage(IMail mail, IUser sender, String content) {
		IMessage message = persistenceManager.newObject("message");
		mail.getMessages().add(message);
		message.setMail(mail);
		message.setContent(content);
		message.setTime(Calendar.getInstance().getTime());
		message.setSender(sender);
		return message;
	}

	public IMail getMailById(long id) {
		return persistenceManager.getObject("mail", id);
	}
}

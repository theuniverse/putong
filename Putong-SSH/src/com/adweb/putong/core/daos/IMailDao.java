package com.adweb.putong.core.daos;

import java.util.List;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.beans.IUser;
import com.iiiss.template.ssh.common.core.IDao;

public interface IMailDao extends IDao {

	public IMail createMail(String topic, List<IUser> users);

	public IMessage createMessage(IMail mail, IUser sender, String content);

	public IMail getMailById(long id);

}
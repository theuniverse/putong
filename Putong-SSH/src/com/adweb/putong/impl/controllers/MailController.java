package com.adweb.putong.impl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IMail;
import com.adweb.putong.core.beans.IMessage;
import com.adweb.putong.core.services.IMailService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonMail;
import com.adweb.putong.impl.controllers.json.JsonMessage;

@Controller
public class MailController extends BaseController {
	private static final String INVALID_MAILID = "Invalid mail id";

	@Autowired
	private IMailService mailService;

	public class JsonMailPutRequest extends JsonAuthRequest {
		private List<String> receivers;
		private String topic;
		private String content;

		public List<String> getReceivers() {
			return receivers;
		}

		public void setReceivers(List<String> receivers) {
			this.receivers = receivers;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public class JsonMailListRequest extends JsonAuthRequest {
		private Integer sindex;
		private Integer eindex;

		public Integer getSindex() {
			return sindex;
		}

		public void setSindex(Integer sindex) {
			this.sindex = sindex;
		}

		public Integer getEindex() {
			return eindex;
		}

		public void setEindex(Integer eindex) {
			this.eindex = eindex;
		}
	}

	public class JsonMessagePutRequest extends JsonAuthRequest {
		private Long mailid;
		private String content;

		public Long getMailid() {
			return mailid;
		}

		public void setMailid(Long mailid) {
			this.mailid = mailid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public class JsonMessageListRequest extends JsonAuthRequest {
		private Long mailid;
		private Integer sindex;
		private Integer eindex;

		public Long getMailid() {
			return mailid;
		}

		public void setMailid(Long mailid) {
			this.mailid = mailid;
		}

		public Integer getSindex() {
			return sindex;
		}

		public void setSindex(Integer sindex) {
			this.sindex = sindex;
		}

		public Integer getEindex() {
			return eindex;
		}

		public void setEindex(Integer eindex) {
			this.eindex = eindex;
		}
	}

	@RequestMapping(value = "/mail/put", method = RequestMethod.POST)
	public String putMail(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonMailPutRequest mpRequest = authenticate(JsonMailPutRequest.class,
				json, model);
		if (mpRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (mpRequest.getReceivers() == null
				|| mpRequest.getReceivers().size() == 0
				|| mpRequest.getTopic() == null
				|| mpRequest.getContent() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IMail mail = mailService.putMail(mpRequest.getUsername(), mpRequest
				.getReceivers(), mpRequest.getTopic(), mpRequest.getContent());
		if (mail == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		IMessage last = mailService.getLastMessage(mail.getKey());
		JsonMail jsonMail = new JsonMail(mail, last);
		model.addAttribute(SUCCEED_JSON, jsonMail);
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/mail/list", method = RequestMethod.POST)
	public String listMail(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonMailListRequest mlRequest = authenticate(JsonMailListRequest.class,
				json, model);
		if (mlRequest == null)
			return ERROR_PAGE;

		// call services
		List<IMail> mails = mailService.listMail(mlRequest.getUsername(),
				mlRequest.getSindex(), mlRequest.getEindex());
		if (mails == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		List<JsonMail> jsonMails = new ArrayList<JsonMail>();
		for (IMail mail : mails) {
			IMessage last = mailService.getLastMessage(mail.getKey());
			JsonMail jsonMail = new JsonMail(mail, last);
			jsonMails.add(jsonMail);
		}

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonMails));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/message/put", method = RequestMethod.POST)
	public String putMessage(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonMessagePutRequest mpRequest = authenticate(
				JsonMessagePutRequest.class, json, model);
		if (mpRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (mpRequest.getContent() == null || mpRequest.getMailid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IMessage message = mailService.putMessage(mpRequest.getMailid(),
				mpRequest.getUsername(), mpRequest.getContent());
		if (message == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_MAILID));
			return ERROR_PAGE;
		}

		model.addAttribute(SUCCEED_JSON, new JsonMessage(message));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/message/list", method = RequestMethod.POST)
	public String listMessage(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonMessageListRequest request = authenticate(
				JsonMessageListRequest.class, json, model);
		if (request == null)
			return ERROR_PAGE;

		// check required parameters
		if (request.getMailid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		List<IMessage> messages = mailService.listMessage(request.getMailid(),
				request.getSindex(), request.getEindex());
		if (messages == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(INVALID_MAILID));
			return ERROR_PAGE;
		}

		List<JsonMessage> jsonMessages = new ArrayList<JsonMessage>();
		for (IMessage message : messages)
			jsonMessages.add(new JsonMessage(message));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonMessages));
		return SUCCEED_PAGE;
	}
}

package com.adweb.putong.impl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.events.IEvent;
import com.adweb.putong.core.services.IEventService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonNotice;

@Controller
public class NoticeController extends BaseController {
	private static final String READ_FAIL = "Set read failed.";

	@Autowired
	private IEventService eventService;

	public class JsonNoticeListRequest extends JsonAuthRequest {
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

	public class JsonNoticeReadRequest extends JsonAuthRequest {
		private Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	@RequestMapping(value = "/notice/list", method = RequestMethod.POST)
	public String list(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonNoticeListRequest nlRequest = authenticate(
				JsonNoticeListRequest.class, json, model);
		if (nlRequest == null)
			return ERROR_PAGE;

		// call services
		List<IEvent> notices = eventService.listNotice(nlRequest.getUsername(),
				nlRequest.getSindex(), nlRequest.getEindex());

		List<JsonNotice> jsonNotices = new ArrayList<JsonNotice>();
		for (IEvent event : notices)
			jsonNotices.add(new JsonNotice(event));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonNotices));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/notice/read", method = RequestMethod.POST)
	public String read(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonNoticeReadRequest nrRequest = authenticate(
				JsonNoticeReadRequest.class, json, model);
		if (nrRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (nrRequest.getId() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = eventService.read(nrRequest.getUsername(),
				nrRequest.getId());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(READ_FAIL));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}
}

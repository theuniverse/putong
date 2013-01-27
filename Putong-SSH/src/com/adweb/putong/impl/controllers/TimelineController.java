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
import com.adweb.putong.core.beans.events.IWeiboEvent;
import com.adweb.putong.core.services.ICommentService;
import com.adweb.putong.core.services.IEventService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonNews;

@Controller
public class TimelineController extends BaseController {
	private static final String READ_FAIL = "Set read failed.";

	@Autowired
	private IEventService eventService;

	@Autowired
	private ICommentService commentService;

	public class JsonTimelineListRequest extends JsonAuthRequest {
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

	public class JsonTimelineReadRequest extends JsonAuthRequest {
		private Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	@RequestMapping(value = "/timeline/list", method = RequestMethod.POST)
	public String list(@RequestBody String json, Model model) {
		json = ControllerHelper.validateDesktop(json);

		// validate and authenticate json request
		JsonTimelineListRequest tlRequest = authenticate(
				JsonTimelineListRequest.class, json, model);
		if (tlRequest == null)
			return ERROR_PAGE;

		// call services
		List<IWeiboEvent> timeline = eventService.listTimeline(tlRequest
				.getUsername(), tlRequest.getSindex(), tlRequest.getEindex());

		List<JsonNews> jsonTimeline = new ArrayList<JsonNews>();
		for (IEvent event : timeline) {
			JsonNews jsonNews = new JsonNews(event);
			jsonNews.setWeiboComments(commentService
					.count(((IWeiboEvent) event).getWeibo().getKey()));
			jsonTimeline.add(jsonNews);
		}

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonTimeline));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/timeline/read", method = RequestMethod.POST)
	public String read(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonTimelineReadRequest trRequest = authenticate(
				JsonTimelineReadRequest.class, json, model);
		if (trRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (trRequest.getId() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = eventService.read(trRequest.getUsername(),
				trRequest.getId());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(READ_FAIL));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}
}

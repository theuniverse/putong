package com.adweb.putong.impl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.services.ICommentService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonComment;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;

@Controller
public class CommentController extends BaseController {
	private static final String DROP_FAIL = "Drop comment failed.";

	@Autowired
	private ICommentService commentService;

	public class JsonCommentPutRequest extends JsonAuthRequest {
		private Long weiboid;
		private String content;

		public Long getWeiboid() {
			return weiboid;
		}

		public void setWeiboid(Long weiboid) {
			this.weiboid = weiboid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public class JsonCommentListRequest extends JsonAuthRequest {
		private Long weiboid;
		private Integer sindex;
		private Integer eindex;

		public Long getWeiboid() {
			return weiboid;
		}

		public void setWeiboid(Long weiboid) {
			this.weiboid = weiboid;
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

	public class JsonCommentDropRequest extends JsonAuthRequest {
		private Long commentid;

		public Long getCommentid() {
			return commentid;
		}

		public void setCommentid(Long commentid) {
			this.commentid = commentid;
		}
	}

	@RequestMapping(value = "/comment/put", method = RequestMethod.POST)
	public String put(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonCommentPutRequest cpRequest = authenticate(
				JsonCommentPutRequest.class, json, model);
		if (cpRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (cpRequest.getWeiboid() == null || cpRequest.getContent() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IComment comment = commentService.put(cpRequest.getWeiboid(), cpRequest
				.getUsername(), cpRequest.getContent());

		if (comment == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_WEIBO));
			return ERROR_PAGE;
		}

		model.addAttribute(SUCCEED_JSON, new JsonComment(comment));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/comment/list", method = RequestMethod.POST)
	public String list(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonCommentListRequest clRequest = authenticate(
				JsonCommentListRequest.class, json, model);
		if (clRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (clRequest.getWeiboid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		List<IComment> comments = commentService.list(clRequest.getWeiboid(),
				clRequest.getSindex(), clRequest.getEindex());

		if (comments == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_WEIBO));
			return ERROR_PAGE;
		}

		List<JsonComment> jsonComments = new ArrayList<JsonComment>();
		for (IComment comment : comments)
			jsonComments.add(new JsonComment(comment));

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonComments));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/comment/drop", method = RequestMethod.POST)
	public String drop(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonCommentDropRequest request = authenticate(
				JsonCommentDropRequest.class, json, model);
		if (request == null)
			return ERROR_PAGE;

		// check required parameters
		if (request.getCommentid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = commentService.drop(request.getCommentid(), request
				.getUsername());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(DROP_FAIL));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}
}

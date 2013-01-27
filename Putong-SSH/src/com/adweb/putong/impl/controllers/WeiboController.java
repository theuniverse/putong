package com.adweb.putong.impl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adweb.putong.core.beans.IWeibo;
import com.adweb.putong.core.services.ICommentService;
import com.adweb.putong.core.services.IWeiboService;
import com.adweb.putong.impl.controllers.json.JsonAuthRequest;
import com.adweb.putong.impl.controllers.json.JsonError400;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonList;
import com.adweb.putong.impl.controllers.json.JsonWeibo;

@Controller
public class WeiboController extends BaseController {
	private static final String DROP_FAIL = "Drop weibo failed.";

	@Autowired
	private IWeiboService weiboService;

	@Autowired
	private ICommentService commentService;

	public class JsonWeiboPutRequest extends JsonAuthRequest {
		private String content;
		private String video;
		private List<String> imagelist;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getVideo() {
			return video;
		}

		public void setVideo(String video) {
			this.video = video;
		}

		public List<String> getImagelist() {
			return imagelist;
		}

		public void setImagelist(List<String> imagelist) {
			this.imagelist = imagelist;
		}
	}

	public class JsonWeiboGetRequest extends JsonAuthRequest {
		private Long weiboid;

		public Long getWeiboid() {
			return weiboid;
		}

		public void setWeiboid(Long weiboid) {
			this.weiboid = weiboid;
		}
	}

	public class JsonWeiboListRequest extends JsonAuthRequest {
		private String target_user;
		private Integer sindex;
		private Integer eindex;

		public String getTarget_user() {
			return target_user;
		}

		public void setTarget_user(String targetUser) {
			target_user = targetUser;
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

	public class JsonWeiboForwardRequest extends JsonAuthRequest {
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

	public class JsonWeiboDropRequest extends JsonAuthRequest {
		private Long weiboid;

		public Long getWeiboid() {
			return weiboid;
		}

		public void setWeiboid(Long weiboid) {
			this.weiboid = weiboid;
		}
	}

	@RequestMapping(value = "/weibo/put", method = RequestMethod.POST)
	public String put(@RequestBody String json, Model model) {
		json = ControllerHelper.validateDesktop(json);

		// validate and authenticate json request
		JsonWeiboPutRequest wpRequest = authenticate(JsonWeiboPutRequest.class,
				json, model);
		if (wpRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (wpRequest.getContent() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IWeibo weibo = weiboService.put(wpRequest.getUsername(), wpRequest
				.getContent(), wpRequest.getVideo(), wpRequest.getImagelist());

		JsonWeibo jsonWeibo = new JsonWeibo(weibo);
		jsonWeibo.setComments(commentService.count(weibo.getKey()));
		model.addAttribute(SUCCEED_JSON, jsonWeibo);
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/weibo/get", method = RequestMethod.POST)
	public String get(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonWeiboGetRequest wgRequest = authenticate(JsonWeiboGetRequest.class,
				json, model);
		if (wgRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (wgRequest.getWeiboid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IWeibo weibo = weiboService.get(wgRequest.getWeiboid());
		if (weibo == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_WEIBO));
			return ERROR_PAGE;
		}

		JsonWeibo jsonWeibo = new JsonWeibo(weibo);
		jsonWeibo.setComments(commentService.count(weibo.getKey()));
		model.addAttribute(SUCCEED_JSON, jsonWeibo);
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/weibo/list", method = RequestMethod.POST)
	public String list(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonWeiboListRequest wlRequest = authenticate(
				JsonWeiboListRequest.class, json, model);
		if (wlRequest == null)
			return ERROR_PAGE;

		// call services
		List<IWeibo> weibos = weiboService.list(wlRequest.getUsername(),
				wlRequest.getTarget_user(), wlRequest.getSindex(), wlRequest
						.getEindex());
		if (weibos == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		List<JsonWeibo> jsonWeibos = new ArrayList<JsonWeibo>();
		for (IWeibo weibo : weibos) {
			JsonWeibo jsonWeibo = new JsonWeibo(weibo);
			jsonWeibo.setComments(commentService.count(weibo.getKey()));
			jsonWeibos.add(jsonWeibo);
		}

		model.addAttribute(SUCCEED_JSON, new JsonList(jsonWeibos));
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/weibo/forward", method = RequestMethod.POST)
	public String forward(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonWeiboForwardRequest wfRequest = authenticate(
				JsonWeiboForwardRequest.class, json, model);
		if (wfRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (wfRequest.getWeiboid() == null || wfRequest.getContent() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		IWeibo forwarding = weiboService.forward(wfRequest.getWeiboid(),
				wfRequest.getUsername(), wfRequest.getContent());
		if (forwarding == null) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_WEIBO));
			return ERROR_PAGE;
		}

		JsonWeibo jsonWeibo = new JsonWeibo(forwarding);
		jsonWeibo.setComments(commentService.count(forwarding.getKey()));
		model.addAttribute(SUCCEED_JSON, jsonWeibo);
		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/weibo/drop", method = RequestMethod.POST)
	public String drop(@RequestBody String json, Model model) {
		// validate and authenticate json request
		JsonWeiboDropRequest wdRequest = authenticate(
				JsonWeiboDropRequest.class, json, model);
		if (wdRequest == null)
			return ERROR_PAGE;

		// check required parameters
		if (wdRequest.getWeiboid() == null) {
			model.addAttribute(ERROR_JSON, new JsonError400());
			return ERROR_PAGE;
		}

		// call services
		boolean isSucceed = weiboService.drop(wdRequest.getWeiboid(), wdRequest
				.getUsername());
		if (!isSucceed) {
			model.addAttribute(ERROR_JSON, new JsonError403(DROP_FAIL));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}
}

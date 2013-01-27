package com.adweb.putong.impl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.beans.IRecord;
import com.adweb.putong.core.services.IAccountService;
import com.adweb.putong.core.services.IRecordService;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.impl.controllers.json.JsonRecord;

@Controller
public class RecordController extends BaseController {
	
	private static final String NO_URL = "Url not found";
	private static final String NO_ID = "Record id not found";
	private static final String NO_SUCH_RECORD = "Record not found";

	@Autowired
	private IRecordService recordService;
	
	@Autowired
	private IAccountService accountService;
	
	@RequestMapping(value = "/record/list", method = RequestMethod.GET)
	public String list(@RequestParam("username") String username, @RequestParam("target") String target, Model model) {
		List<IRecord> records = recordService.list(username, target, 0, 10);
		model.addAttribute("records", records);
		
		return "record";		
	}
	
	@RequestMapping(value = "/record/enter", method = RequestMethod.POST)
	public String enter(@RequestParam("username") String username, @RequestParam("url") String url,  @RequestParam("title") String title, Model model) {
		System.out.println(username+","+url+","+title);
		if(username == null || accountService.registerCheck(username)){
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}else if(url == null){
			model.addAttribute(ERROR_JSON, new JsonError403(NO_URL));
			return ERROR_PAGE;
		}
		
		if(title == null){
			title = url;
		}
		
		IRecord record = recordService.put(username, title, url);
		JsonRecord jsonRecord = new JsonRecord(record);
		
		model.addAttribute(SUCCEED_JSON, jsonRecord);
		return SUCCEED_PAGE;
	}
	
	@RequestMapping(value = "/record/leave", method = RequestMethod.POST)
	public String enter(@RequestParam("username") String username, @RequestParam("record") String record, Model model) {
		
		if(username == null || accountService.registerCheck(username)){
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}else if(record == null){
			model.addAttribute(ERROR_JSON, new JsonError403(NO_ID));
			return ERROR_PAGE;
		}
		
		Long recordId = null;
		try{
			recordId = Long.parseLong(record);
		} catch(NumberFormatException e) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_ID));
			return ERROR_PAGE;
		}
		
		System.out.println(username+","+recordId);
		
		boolean result = recordService.update(recordId, false, username);
		if(!result){
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_RECORD));
			return ERROR_PAGE;
		}
			
		//JsonRecord jsonRecord = new JsonRecord(record);
		
		return SUCCEED_PAGE;
	}
}

package com.adweb.putong.chat.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.*;

public class JSONParser {

	//private static final Gson g = new Gson();
	
	private JSONParser(){}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON(String json){
		
		if(json == null || json == "") return null;	
		
		Map<String, Object> jsonObject = new LinkedHashMap<String, Object>();
		jsonObject = new Gson().fromJson(json, jsonObject.getClass());
		
		return jsonObject;
		
	}
	
	public static String generateJSONString(Map<String, ? extends Object> map){
		
		String json = null;		
		
		if(map == null) return json;			
		json = new Gson().toJson(map);	
		
		return json;		
	}
	
}

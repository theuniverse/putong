<%@ page language="java" import="com.adweb.putong.impl.controllers.json.*"
	pageEncoding="utf-8" contentType="application/json; charset=utf-8"%>
<%
	response.setStatus(200);
	JsonObject json = (JsonObject) request.getAttribute("json");
	String newJson;
	if (json != null) {
		if (json.toString().trim().length() == 0){
			newJson = "{\"code\":200}\n";
		}else{
			newJson = "{\"code\":200,\"json\":" + json.toString().trim()+ "}\n";
		}
		
		System.out.println(json);
		response.getWriter().print(newJson);	
	}else{
		newJson = "{\"code\":200}\n";
		response.getWriter().print(newJson);	
	}
	
%>
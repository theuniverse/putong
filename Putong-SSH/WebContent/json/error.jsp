<%@ page language="java" import="com.adweb.putong.impl.controllers.json.*"
	pageEncoding="utf-8" contentType="application/json; charset=utf-8"%>
<%
	response.setStatus(403);
	JsonObject json = (JsonObject) request.getAttribute("error");
	if (json != null)
		response.getWriter().print(json);
	System.out.println(json);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="${requestScope.records}" var="item">
						<ul id="" class="item-content-small">
							<c:choose>
							    <c:when test="${item.status eq 'true'}">
							    	<a href="${item.chatroom.url}" target="_blank" class="item-forward item-online">噗通一跳</a>
								</c:when>
								<c:otherwise>
									<a href="${item.chatroom.url}" target="_blank" class="item-forward">观看遗址</a>
								</c:otherwise>
							</c:choose>							
							<li class="item-title"><span class="title">${item.chatroom.title}</span><span>${item.enterTime}</span></li>
							
							<li class="item-url">${item.chatroom.url}</li>
						</ul>
					</c:forEach>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页/噗通</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="resources/css/main.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="resources/css/page.css" rel="stylesheet" type="text/css" media="screen" />	
	<script language="javaScript" type="text/javascript" src="resources/js/jquery-1.7.2.min.js" charset="utf-8"></script> 
    <script language="javaScript" type="text/javascript" src="resources/js/sidebar.js" charset="utf-8"></script>
  </head>
  
  <body>
  	<div id="body">
		<jsp:include page="clips/sidebar.jsp" />
		<div id="main-frame">
			<c:choose>
			    <c:when test="${requestScope.things.size() eq 0}">
			    	<div class="timeline-item">
						<ul id="" class="item-content">
							<li style="color:#aaaaaa">没有新鲜事</li>
						</ul>				
					</div>
			    </c:when>
			    <c:otherwise>
			    	<c:forEach items="${requestScope.things}" var="thing">
						<div class="timeline-item">
							<div class="avatar"></div>
							<ul id="" class="item-content">
								<li><span  class="item-author">${thing.record.user.username}</span> <span style="color:#999999;"><fmt:formatDate
							pattern="yyyy-MM-dd hh:mm:ss" value="${thing.record.enterTime}" /></span></li>
								<c:choose>
								
								    <c:when test="${thing.record.status eq true}">
								    	<a href="${thing.record.chatroom.url}" target="_blank" class="item-forward item-online">噗通一下</a>
									</c:when>
									<c:otherwise>
										<a href="${thing.record.chatroom.url}" target="_blank" class="item-forward">观看遗址</a>
									</c:otherwise>
								</c:choose>						
								<li class="item-title"><span class="title">${thing.record.chatroom.title}</span></li>
								<li class="item-url">${thing.record.chatroom.url}</li>
							</ul>				
						</div>
					</c:forEach>
			    </c:otherwise>
			</c:choose>
			
		</div>
	</div>
  </body>
</html>

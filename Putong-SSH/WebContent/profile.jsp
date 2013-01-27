<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    <title>${requestScope.user.username}/噗通</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="resources/css/main.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="resources/css/page.css" rel="stylesheet" type="text/css" media="screen" />
	<script language="JavaScript" type="text/javascript" src="resources/js/jquery-1.7.2.min.js" charset="utf-8"></script> 
	<script language="JavaScript" type="text/javascript" src="resources/js/sidebar.js" charset="utf-8"></script> 
  	<script language="JavaScript" type="text/javascript" src="resources/js/bio.js" charset="utf-8"></script>
  </head>
  
  <body>
  	<div id="body">		
		<jsp:include page="clips/sidebar.jsp" />
		<div id="main-frame">
			<jsp:include page="clips/bio.jsp" />
			<div class="timeline-item">
				<div class="item-content">
					<c:choose>
					    <c:when test="${requestScope.history.size() eq 0}">
					    	<div class="timeline-item">
								<ul id="" class="item-content">
									<li style="color:#aaaaaa">没有新鲜事</li>
								</ul>				
							</div>
					    </c:when>
					    <c:otherwise>
							<c:forEach items="${requestScope.history}" var="item">
								<ul id="" class="item-content-small">
									<c:choose>
									    <c:when test="${item.status eq 'true'}">
									    	<a href="${item.chatroom.url}" target="_blank" class="item-forward item-online">噗通一跳</a>
										</c:when>
										<c:otherwise>
											<a href="${item.chatroom.url}" target="_blank" class="item-forward">观看遗址</a>
										</c:otherwise>
									</c:choose>							
									<li class="item-title"><span class="title">${item.chatroom.title}</span></li>
									
									<li class="item-url">${item.chatroom.url}</li>
									<li class="item-time"><fmt:formatDate
							pattern="yyyy-MM-dd hh:mm:ss" value="${item.enterTime}" /></li>
								</ul>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
				
			</div>
		</div>
	</div>
  </body>
</html>

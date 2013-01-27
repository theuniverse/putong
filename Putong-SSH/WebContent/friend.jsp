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
    
    <c:choose>
	    <c:when test="${requestScope.user.username eq sessionScope.self.username}">
	    	<title>好友/噗通</title>
		</c:when>
		<c:otherwise>
			<title>好友/${requestScope.user.username}/噗通</title>
		</c:otherwise>
	</c:choose>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="resources/css/main.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="resources/css/page.css" rel="stylesheet" type="text/css" media="screen" />
	<script language="JavaScript" type="text/javascript" src="resources/js/jquery-1.6.2.min.js" charset="utf-8"></script> 
	<script language="JavaScript" type="text/javascript" src="resources/js/sidebar.js" charset="utf-8"></script> 
  </head>
  
  <body>
  	<div id="body">		
		<jsp:include page="clips/sidebar.jsp" />
		<div id="main-frame">
			<jsp:include page="clips/bio.jsp" />
			
			<div class="item-content">
			<c:choose>
			    <c:when test="${requestScope.user.username eq sessionScope.self.username}">
			    	<div>我关注</div>
				</c:when>
				<c:otherwise>
					<div>TA关注</div>
				</c:otherwise>
			</c:choose>
			<c:choose>
			    <c:when test="${requestScope.followers.size() eq 0}">
			    	<div class="timeline-item">
						<ul id="" class="item-content-small">
							<li style="color:#aaaaaa">谁也没有关注</li>
						</ul>				
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${requestScope.followers}" var="friend">			
						<div class="timeline-item">
							<div class="avatar"></div>
							<ul id="" class="item-content-small">
								<li><span  class="item-author"><a href="http://localhost:8080/new-putong/profile/${friend.username}" target="_blank" class="item-forward">也去看看</a>${friend.nickname}</span></li>
								<li class="item-title">${thing.chatroom.title}</li>
								<li class="item-url">${thing.chatroom.url}</li>
							</ul>				
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
			
			</div>			
			<div class="item-content">
			<c:choose>
			    <c:when test="${requestScope.user.username eq sessionScope.self.username}">
			    	<div>关注我的人</div>
				</c:when>
				<c:otherwise>
					<div>关注TA的人</div>
				</c:otherwise>
			</c:choose>
			<c:choose>
			    <c:when test="${requestScope.followeds.size() eq 0}">
			    	<div class="timeline-item">
						<ul id="" class="item-content-small">
							<li style="color:#aaaaaa">没有关注者</li>
						</ul>				
					</div>
			    </c:when>
				<c:otherwise>
					<c:forEach items="${requestScope.followeds}" var="friend">			
						<div class="timeline-item">
							<div class="avatar"></div>
							<ul id="" class="item-content-small">
								<li><span  class="item-author"><a href="http://localhost:8080/new-putong/profile/${friend.username}" target="_blank" class="item-forward">也去看看</a>${friend.nickname}</span></li>
								<li class="item-title">${thing.chatroom.title}</li>
								<li class="item-url">${thing.chatroom.url}</li>
							</ul>				
						</div>
					</c:forEach>
			</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
  </body>
</html>

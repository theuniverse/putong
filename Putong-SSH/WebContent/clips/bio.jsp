<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="timeline-item">
	<div class="avatar"></div>
	<ul id="" class="item-content" style="padding-top:10px;">		
		<c:choose>
			<c:when	test="${sessionScope.self.username eq requestScope.user.username}">
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${requestScope.friend eq 'true'}">
						<a
							href="javascript:friend('unfollow','${sessionScope.self.username}','${sessionScope.self.password}','${requestScope.user.username}')"
							class="item-forward friend-button">取消好友</a>
					</c:when>
					<c:otherwise>
						
						<a
							href="javascript:friend('follow','${sessionScope.self.username}','${sessionScope.self.password}','${requestScope.user.username}')"
							class="item-forward friend-button">加为好友</a>
					</c:otherwise>
				</c:choose>
				
			</c:otherwise>
		</c:choose>
		<li class="bio-author">${requestScope.user.nickname}</li>
		<li class="bio-content">
			<c:choose>
				<c:when	test="${sessionScope.self.gender}">
					<span class="bio-icon bio-gender"></span>
					<span class="bio-text">男</span>
				</c:when>
				<c:when	test="${sessionScope.self.gender eq null}">
				</c:when>
				<c:otherwise>
					<span class="bio-icon bio-gender"></span>
					<span class="bio-text">女</span>
				</c:otherwise>
			</c:choose>
			
			<span class="bio-icon bio-contact"></span><span class="bio-bold">电子邮件</span>
			<span class="bio-text">${requestScope.user.email}</span>
		</li>
		<li class="bio-content">
		<c:choose>
			<c:when	test="${requestScope.user.bio eq null}"><span class="bio-text">TA很懒，什么都没留</span></c:when>
			<c:when	test="${requestScope.user.bio eq ''}"><span class="bio-text">TA很懒，什么都没留</span></c:when>
			<c:otherwise><span class="bio-text">${requestScope.user.bio}</span></c:otherwise>
		</c:choose>
		</li>
	</ul>
</div>
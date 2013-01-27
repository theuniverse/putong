<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>设置/噗通</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/main.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="resources/css/page.css" rel="stylesheet" type="text/css"
	media="screen" />
<script language="JavaScript" type="text/javascript"
	src="resources/js/jquery-1.7.2.min.js" charset="utf-8"></script>
<script language="JavaScript" type="text/javascript"
	src="resources/js/sidebar.js" charset="utf-8"></script>
</head>

<body>
	<div id="body">
		<jsp:include page="clips/sidebar.jsp" />
		<div id="main-frame">
			<jsp:include page="clips/bio.jsp" />
			<div class="timeline-item">
				<div class="item-content">
					<form action="setting/apply" method="post" autocomplete="off">
						<table border="0">
							<tr>
								<td class="label">用户名</td>
								<td><input name="username" id="username" type="text"
									width="40" disabled="disabled"
									value="${sessionScope.self.username}"></td>
							</tr>
							<tr>
								<td class="label">密码</td>
								<td><input name="password" type="password" width="40"
									value="${sessionScope.self.password}"></td>
							</tr>
							<tr>
								<td class="label">新密码</td>
								<td><input name="new-password" type="password" width="40"
									value="${sessionScope.self.password}"></td>
							</tr>
							<tr>
								<td class="label">昵称</td>
								<td><input name="nickname" id="name" type="text" width="40"
									value="${sessionScope.self.nickname}"></td>
							</tr>
							<tr>
								<td class="label">性别</td>
								<td>
								<select name="gender" id="sex">
								<c:choose>
									<c:when	test="${sessionScope.self.gender}">
										<option value="0">男</option>
										<option value="1">女</option>
										<option value="2">不说</option>
									</c:when>
									<c:when	test="${sessionScope.self.gender eq null}">
										<option value="0">男</option>
										<option value="1">女</option>
										<option value="2"  selected="selected">不说</option>
									</c:when>
									<c:otherwise>
										<option value="0">男</option>
										<option value="1"  selected="selected">女</option>
										<option value="2">不说</option>
									</c:otherwise>
								</c:choose> 										
								</select></td>
							</tr>
							<tr>
								<td class="label">电子邮件</td>
								<td><input name="email" type="text" width="40"
									value="${sessionScope.self.email}"></td>
							</tr>
							<tr>
								<td class="label">他人能否查看</td>
								<td><select name="seen" id="seen">
								<c:choose>
									<c:when	test="${sessionScope.self.seen}">
										<option value="0">不能</option>
										<option value="1" selected="selected">能</option>
									</c:when>
									<c:otherwise>
										<option value="0">不能</option>
										<option value="1">能</option>
									</c:otherwise>
								</c:choose>
								</select></td>
							</tr>
							<tr>
								<td class="label">自我介绍</td>
								<td><textarea name="bio" width="40">${sessionScope.self.bio}</textarea></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" value="确认"></td>
							</tr>
						</table>
					</form>
				</div>

			</div>
		</div>
	</div>
</body>
</html>

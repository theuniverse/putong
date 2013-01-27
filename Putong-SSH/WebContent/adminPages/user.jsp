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

<title>禁言用户/噗通后台管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">
<link rel="stylesheet" type="text/css" href="resources/css/page.css">

</head>
<body>
	<div id="body">
		<jsp:include page="sidebar.jsp" />
		<div id="main-frame">
			<div class="content">
				<table>
					<tr>
						<td colspan=2><div class="admin-title">用户封号处理</div></td>
					</tr>
					<tr>
						<td>
							<h3>添加封禁用户账号:</h3>
							<form action="block/user/add" method="post" autocomplete="off">
								<table>
									<tr>
										<td class="label">用户名称:</td>
										<td><input name="username" type="text" width="40"></td>
									</tr>
									<tr>
										<td class="label">封禁原因:</td>
										<td><input name="reason" type="text" width="40"></td>
									</tr>

									<tr>
										<td colspan="2"><input type="submit"></td>
									</tr>
								</table>
							</form>
						</td>
						<td>
							<h3>撤销封禁用户账号：</h3>
							<form action="block/user/remove" method="post" autocomplete="off">
								<table>
									<tr>
										<td class="label">撤销用户ID</td>
										<td><input name="id" type="text" width="40"></td>
									</tr>
									<tr>
										<td colspan="2"><input type="submit"></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>


				<table class="showlist">
					<tr>
						<th>被封禁用户id</th>
						<th>被封禁用户名</th>
						<th>被封禁原因</th>
						<th>被封禁时间</th>
					</tr>

					<c:forEach items="${requestScope.blockusers}" var="blockuser">
						<tr>
							<td>${blockuser.key}</td>
							<td>${blockuser.user.username}</td>
							<td>${blockuser.reason}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${blockuser.time}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>

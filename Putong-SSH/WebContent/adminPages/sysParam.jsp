<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>系统参数/噗通管理后台</title>

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
			<form action="config/change" method="post" autocomplete="off">
				<table>
					<tr><td colspan=2><div class="admin-title">系统参数设置</div></td></tr>
					<tr>
						<td class="label">历史记录保存天数:</td>
						<td><input name="recordDay" type="text" width="40" value="${requestScope.sysparam.recordDay}"></td>
						<td>目前：${requestScope.sysparam.recordDay}</td>
					</tr>
					<tr>
						<td class="label">管理员用户名:</td>
						<td><input name="rootUsername" type="text" width="40" value="${requestScope.sysparam.rootUsername}"></td>
						<td>目前：${requestScope.sysparam.rootUsername}</td>
					</tr>
					<tr>
						<td class="label">管理员密码:</td>
						<td><input name="rootPassword" type="password" width="40" value="${requestScope.sysparam.rootPassword}"></td>
						<td>目前：${requestScope.sysparam.rootPassword}</td>
					</tr>
					<tr>
						<td class="label">系统邮件地址:</td>
						<td><input name="rootEmail" type="text" width="40" value="${requestScope.sysparam.rootEmail}"></td>
						<td>目前：${requestScope.sysparam.rootEmail}</td>
					</tr>
					<tr>
						<td class="label">系统邮件密码:</td>
						<td><input name="emailPassword" type="text" width="40" value="${requestScope.sysparam.emailPassword}"></td>
						<td>目前：${requestScope.sysparam.emailPassword}</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	</div>
</body>
</html>

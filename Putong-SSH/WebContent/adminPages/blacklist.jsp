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

<title>域名黑名单/噗通管理后台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">
<link rel="stylesheet" type="text/css" href="resources/css/page.css">

</head>
<body>
	<div id="body">	
		<jsp:include page="sidebar.jsp" />
		<div id="main-frame">		
		<div class="content">
			<table>
				<tr><td colspan=2><div class="admin-title">网站黑名单管理</div></td></tr>
				<tr>
					<td>
						<h3>添加新的黑名单网站:</h3>
						<form action="block/site/add" method="post" autocomplete="off">
							<table>
								<tr>
									<td class="label">网站名称:</td>
									<td><input name="title" type="text" width="40"></td>
								</tr>
								<tr>
									<td class="label">网址:</td>
									<td><input name="domain" type="text" width="40"></td>
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
						<h3>撤销列入黑名单的网站</h3>
						<form action="block/site/remove" method="post" autocomplete="off">
							<table>
								<tr>
									<td class="label">网站ID:</td>
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
					<th>编号</th>
					<th>网站名称</th>
					<th>网址</th>
					<th>封禁原因</th>
				</tr>
				<c:forEach items="${requestScope.blocksites}" var="item" >
					<tr>
						<td>${item.key}</td>
						<td>${item.title}</td>
						<td>${item.domain}</td>
						<td>${item.reason}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</div>
</body>
</html>

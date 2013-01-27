<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>控制平台登录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">

</head>

<body>

	<div id="content-frame">
		<div class="header">
			<img src="resources/image/logo_large.png" alt="" />
		</div>
		<div class="title">噗通管理登录</div>
		<form action="admin/login" method="post" autocomplete="off">
			用户名<input id="username" name="username" type="text" value=""><br />
			密码<input id="password" name="password" type="password" value=""><br />
			<input type="submit" value="登录">
		</form>
	</div>

</body>
</html>

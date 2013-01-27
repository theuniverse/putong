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

<title>验证完成</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">


</head>

<body>
	<div id="content-frame">
		<div class="header">
			<img src="../resources/image/logo_large.png" />
		</div>
		<div class="title">你未能激活自己的账号。</div>
		<div>
		<form action="../register/reactive" method="post">
			<input name="username" type="text" value="${requestScope.username}" />
			<input type="submit" value="重新发送" />
		</form>
		</div>
	</div>
</body>
</html>
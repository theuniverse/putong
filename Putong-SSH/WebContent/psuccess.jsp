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

<title>账号封禁成功/噗通</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">
<script type="text/javascript" src="resources/js/jquery-1.7.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.timers-1.2.js" charset="utf-8"></script>
</head>

<body>
	<div id="content-frame">
		<div class="header">
			<img src="resources/image/logo_large.png" />
		</div>
		<div class="title">申请账号封禁成功</div>
		<div>请等待管理员处理，<span id="time">5</span>秒后回到首页……</div>
		<script type="application/javascript" language="javascript" charset="utf-8">
			var i = 5;
			$(document).everyTime(1000, function(){
				$("#time").html(i);
				i--;
				if(i == 0)
					window.location = "http://localhost:8080/new-putong/";
			});
		</script>
	</div>
</body>
</html>
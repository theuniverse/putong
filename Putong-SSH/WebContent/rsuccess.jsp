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

<title>${requestScope.type}/噗通</title>

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
			<img src="resources/image/logo_large.png" />
		</div>
		<div class="title">${requestScope.type}，请查收激活邮件。<span id="time">5</span>秒后关闭页面……</div>
		<script type="application/javascript" language="javascript" charset="utf-8">
			var i = 5;
			$(document).everyTime(1000, function(){
				if(i == 0)
					window。close();
				$("#time").html(i);
				i--;				
			});
		</script>
	</div>

</body>
</html>
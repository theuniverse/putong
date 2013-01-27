<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号未激活/噗通</title>

<link rel="stylesheet" type="text/css" href="../resources/css/admin.css">
</head>
<body>

	<div id="content-frame">
		<div class="header">
			<img src="../resources/image/logo_large.png" />
		</div>
		<div class="title">您尚未激活自己的账号。</div>
		<div>
		<form action="../register/reactive" method="post">
			<input name="username" type="text" value="${requestScope.username}" />
			<input type="submit" value="重新发送" />
		</form>
		</div>
		<script type="application/javascript" language="javascript" charset="utf-8">
			
		</script>
	</div>

</body>
</html>
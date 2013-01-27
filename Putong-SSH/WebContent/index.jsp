<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>首页/噗通</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="噗通网首页">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/css/main.css" rel="stylesheet" type="text/css" media="screen" />
    <script language="JavaScript" type="text/javascript" src="resources/js/jquery-1.7.2.min.js" charset="utf-8"></script> 
    <script language="JavaScript" type="text/javascript" src="resources/js/jquery.cookie.js" charset="utf-8"></script>     
    <script language="JavaScript" type="text/javascript" src="resources/js/login.js" charset="utf-8"></script>
    <script language="JavaScript" type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
  </head>
  
  <body>
  	<c:choose>
		<c:when	test="${not (sessionScope.self eq null)}">
			<jsp:forward page="home"></jsp:forward>
		</c:when>
  		<c:otherwise></c:otherwise>
  	</c:choose>
  	<div id="body">
    	<div id="header">
        	<div id="header-login-frame">
				<div style="height:35px;">
				<form action="login" method="post" autocomplete="off">
					<div id="login-username-pane">
						<label class="label-on">用户名</label>
						<input name="username" id="login-username" type="text" value="" />
					</div>	
					<div id="login-password-pane">
						<label class="label-on">密码</label>
						<input name="password" id="login-password" type="password" value="" />
					</div>
					<input name="callback" id="callback" type="hidden" value="" />
                    <input id="login-button" type="submit" value="登录" />
               	</form>
               	</div>
            	<div id="login-tip-frame">
					<input type="checkbox" /><span>记住用户名</span>
					<div class="login-tip" id="login-failure"></div>
				</div>
            </div>
        	<div id="header-logo">putong</div>            
        </div>
		<div id="main-container">
        	 <div id="main-signup-frame">
             	<p id="signup-intro"><b>还未加入噗通?</b> 注册吧！</p>
            	<form id="register-form" action="register" method="post" onSubmit="return validate_reg()" autocomplete="off">
                	<div id="register-username-pane">
						<label class="label-on">选择用户名</label>
						<input name="username" id="register-username" type="text" value="" />
					</div>
					<div id="register-email-pane">
						<label class="label-on">输入邮箱</label>
						<input name="email" id="register-email" type="text" value="" />
					</div>	
					<div id="register-password-pane">
						<label class="label-on">输入密码</label>
						<div id="password-secure-level">〇</div>
						<div id="password-secure-tip">密码安全提示：<br />1、不要使用太常见的密码。<br />2、密码同用户名不要一致。<br />3、密码长度多于8位，且兼有数字、大小写字符最安全。</div>
						<input name="password" id="register-password" type="password" value="" />
					</div>
					<div id="register-password2-pane">
						<label class="label-on">再次输入密码</label>
						<input name="password2" id="register-password2" type="password" value="" />
					</div>
					<div id="recaptcha"></div>
					<input id="register-button" type="submit" value="注册" />
					<div class="register-tip" id="register-failure"></div>
                </form>
            </div>
        	<div id="main-slogan-frame">
            	<p id="main-slogan">无论什么网页，“<span>噗通</span>”一下！</p>
                <p id="sub-slogan">Instant chat anytime, anywhere, with those who are interested in the same thing as you.</p>
            </div>           
        </div>
        <!-- <div id="footer">“噗通”团队</div> -->
    </div>
  </body>
</html>
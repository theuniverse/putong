<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="sidebar">
	<div id="header">
		<img src="resources/image/logo_large.png" />
	</div>
	<div id="account" class="sidebar-frame">
		<div class="avatar" id="account-avatar"></div>
		<ul id="account-info" class="sidebar-list">
			<li>${sessionScope.self.nickname}，你好</li>
			<li>在234个网站噗通</li>
			<li>关注34个好友</li>
		</ul>
	</div>

	<div id="sidebar-search">
		<form action="user/search" method="post">
			<div class="icon isearch"></div>
			<div id="search-pane">
				<label class="label-on">搜索感兴趣的人</label>
				<input name="q" id="q" type="text" value="" />
			</div>
		</form>
	</div>
	<div id="channel-frame" class="sidebar-frame">

		<ul class="sidebar-list">
			<li><div class="icon ihome"></div><a href="home" >主页</a></li>
			<li><div class="icon ihistory"></div><a href="profile" >我的历史</a></li>
			<li><div class="icon ifriend"></div><a href="friends" >好友</a></li>
			<li><div class="icon isetting"></div><a href="setting" >设置</a></li>
		</ul>
		
	</div>
	
	<div class="sidebar-frame logout-frame">
	<form action="logout" method="post">
		<input type="submit" value="退出登录" />
	</form>
	</div>
</div>
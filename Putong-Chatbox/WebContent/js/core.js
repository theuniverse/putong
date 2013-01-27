
var chat = {
	lastUsername: "Donghwan Kim",
	username: null,
	password: null,
	url: null,
	title: null,
	online:	false,
	asked: false,
	log_user:{
		uname: null,
		upassword: null
	},
	userlist: null,
	is_chat: true
};

var PARAM = {
	SERVER_NAME : "http://localhost:8080/new-putong",
	profile : "http://localhost:8080/new-putong/profile/",
};

$(document).ready(function() {
	
	$("#login-wrapper").fadeIn(500,function(){
		$("#info-content").animate({top:0}, 500);
		$("#wait-content").fadeIn(500,function(){
			$.stream("chat", {
				type: "http",
				dataType: "json",
				context: $("#content")[0],
				open: function(event, stream) {
					//alert("knock");
					knockdoor();
					handshake();
				},
				message: function(event) {
					//$("<p />").addClass("log").text(event.data.message+","+event.data.username).appendTo(this);
					if(event.data.url == chat.url){
						 if (event.data.type == "auth"){
							//alert(event.data.message);
							if (chat.log_user.uname == event.data.username) {
								if(event.data.message == "success"){
									//alert(event.data.message);
									submit_success();
								}else if(event.data.message == "fail"){
									alert(event.data.message);
									submit_fail();
								}
							}
						}
						else if (event.data.type == "log") {
							//alert(event.data.username + chat.username+event.data.message);
							if (chat.username != event.data.username) {
								var message = null;
								if (event.data.message == "enter") {
									alert(event.data.username);
									message = event.data.username + "进入了聊天室";
								} else if (event.data.message == "leave") {
									message = event.data.username + "离开了聊天室";
								}
								if(message != null){
									$("<p />").addClass("log").text(message).appendTo(this);
									$("#content-wrapper").scrollTop($("#content-wrapper")[0].scrollHeight);
								}
							}
						}
						else if (event.data.type == "reply"){
							//$("<p />").addClass("log").text(event.data.url+event.data.message).appendTo(this);
							$("#wait-content").clearQueue().fadeOut(200);
							chat.asked = true;
							$("#info-content").clearQueue().animate({top:"-200px"}, 500).animate({top:0}, 500, function(){
								if(event.data.message == "no") {
									$("#deny-content").fadeIn(200);
								}else{
									$("#login-content").fadeIn(200);
								}
							});							
						}
						else if (event.data.type == "update"){
							//$("<p />").addClass("log").text(event.data.url+event.data.message).appendTo(this);
							//alert(event.data.message);
							chat.userlist = eval(event.data.message);
							//alert(chat.userlist.length);
							refresh_list();
							//alert(userlist.length);
						}
						else if (event.data.type == "message"){
							//alert("message");
							if (chat.lastUsername !== event.data.username) {								
								var displayName = null;
								if (chat.username !== event.data.username) {
									displayName = (chat.username !== event.data.username) ? event.data.name : "我";
									
									$("<p />").addClass("user other").html("<a href='"+PARAM.profile+displayName+"' target='_blank' >"+displayName).appendTo(this);
								}else{
									displayName = "我";
									$("<p />").addClass("user self").html(displayName).appendTo(this);
								}
								chat.lastUsername = event.data.username; 							
							}
							
							$("<p />").addClass("message").text(event.data.message).appendTo(this);
							//$("<p />").addClass("message").text(event.data.time).appendTo(this);
							$("#content-wrapper").scrollTop($("#content-wrapper")[0].scrollHeight);
							$("#chatbox-header").css({background:"#06c"});
						}
					}
					
				},
				error: function() {
					//$("#editor .message").attr("disabled", "disabled");
				},
				close: function() {
					//$("#editor .message").attr("disabled", "disabled");
				}			
				
			});
			
			$("#editor .message")
			.keyup(function(event) {
				if (event.which === 13 && $.trim(this.value)) {
					send(this.value);
					this.value = "";
				}
			})
			.click(function(){
				$("#chatbox-header").css({background:"#666666"});
			});

			$(window).resize(function() {
				//var content = $("#content").height($(window).height() - $("#editor").outerHeight(true) - 15)[0];
				//content.scrollTop = content.scrollHeight;
				$("#content-wrapper").scrollTop($("#content-wrapper")[0].scrollHeight);
			}).resize();
		});
	});
	
	$("#friend-button").click(function(){
		if(chat.is_chat) {
			$("#info-content").fadeOut(200);
			$("#login-wrapper").fadeIn(500,function(){
				$("#user-list-frame").fadeIn(200);
			});		
		} else {
			$("#login-wrapper").fadeOut(500,function(){
				$("#user-list-frame").fadeOut(200);
				$("#info-content").fadeIn(200);
			});	
		}
		chat.is_chat = !chat.is_chat;
	});
	
	$(window).unload(function(){
		//alert('bye');
	});

	
});

/**
 * 
 */
function handshake(){
	$("#editor .message").removeAttr("disabled");
	if(chat.username != null && chat.password != null){		
		if(!chat.online){		
			$.stream().send({
				type:"log",
				username: chat.username,
				password: chat.password,
				message: "enter",
				url: get_source(),
				title: get_source_name(),
				time: new Date().toString()
			});
			
			chat.online = !chat.online;
			var message = chat.username + ",欢迎进入聊天室";
			$("<p />").addClass("log").text(message).appendTo($("#content")[0]);
		}
		$.stream().send({
			type:"log",
			username: chat.username,
			password: chat.password,
			message: "alive",
			url: get_source(),
			title: get_source_name(),
			time: new Date().toString()
		});
	}	
}

function knockdoor(){
	if(chat.asked) return;
	$.stream().send({
		type:"knock",
		message: "open",
		url: get_source(),
		title: get_source_name(),
		time: new Date().toString()
	});
}

function send(str){
	if(chat.username != null && chat.password != null){
		//alert("send");
		$.stream().send({
			type: "message", 
			username: chat.username, 
			password: chat.password, 
			message: str,
			url: get_source(),
			title: get_source_name(),
			time: new Date().toString()
		});
	}	
}

function submit(){
	
	// authenticate
	var uname = $("#login-user").val();
	var upass = $("#login-pass").val();
	
	//alert(uname+upass);
	if(uname != "" && upass != "") {
		chat.log_user.uname = uname;
		chat.log_user.upass = upass;
		$.stream().send({
			type: "auth", 
			username: uname, 
			password: upass, 
			message: "auth",
			url: get_source(),
			title: get_source_name(),
			time: new Date().toString()
		});
	}
}

function submit_success(){
	// enter the main chatting frame
	$("#info-content").animate({top:"-200px"},500,function(){
		$("#login-wrapper").fadeOut(500, function(){
			$("#editor .message").removeAttr("disabled").focus();
			$("#friend-button").css("zIndex","5");
			chat.username = chat.log_user.uname;
			chat.password = chat.log_user.uname;
			chat.url = get_source();
			chat.title = get_source_name();
			handshake();
		});
	});
}

function submit_fail(){
	// enter the main chatting frame
	$("#login-tip").text("用户不存在");
}

function get_source(){
	var location = window.location.toString();	
	var str = location.substring(location.indexOf("?") + 3, location.indexOf("&"));
	//alert(str);
	if (chat.url != null) return chat.url;
	else{
		chat.url = str;
		return encodeURI(str);
	}
}

function get_source_name(){
	var location = window.location.toString();
	var str = location.substring(location.indexOf("&") + 3);
	//alert(str);
	if (chat.title != null) return chat.title;
	else{
		chat.title = str;
		return str;
	}
}

function refresh_list(){
	
	$("#list-content ul").html();
	
	var html = "";
	
	var list = chat.userlist;
	//alert(list.length);
	var i = 0;
	for(; i< list.length; i++){
		html += "<li class='user'>";
		html += "<a href='profile/"+list[i].username+"'>"+list[i].name+"</a>";
		html += "</li>";
	}
	
	$("#list-content ul").html(html);
}
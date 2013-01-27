// JavaScript Document
var ERROR = {
	403 : "用户名与密码不匹配",
	
	601 : "验证码错误",
	602 : "用户名或邮箱已被注册",
	603 : "必填项未填写",
	604 : "两次密码不一致",
	605 : "邮箱格式不正确",
	
	701 : "用户名或密码为空"	
};

$(document).ready(function(){
	
	//alert(get_error());
	deal_error();
	
	if(get_callback() != null){
		$("input#callback").val(get_callback);
	}
	
	$("#login-form input[type='text']")
	.mouseenter(function(){
		$(this).css("opacity",".9");
	})
	.mouseleave(function(){
		if($(this).css("borderColor") != "rgb(255, 255, 255)"){
			$(this).css("opacity",".7");
		}
	});
	
	if($("input#login-username").val() == ''){
		$("#login-username-pane .label-on").fadeIn(250);
	}
	
	if($("input#login-password").val() == ''){
		$("#login-password-pane .label-on").fadeIn(250);
	}
	
	$("input#login-username")
	.focus(function(){
		$("#login-username-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #fff");
		$(this).css("opacity",".9");
		if($(this).val() == '用户名'){
			$(this).val('');
		}
	})
	.blur(function(){
		$(this).css("border","1px solid transparent");
		$(this).css("opacity",".7");
		if($(this).val() == ''){
			$("#login-username-pane .label-on").fadeIn(250);
		}
	});
	
	$("#login-username-pane .label-on").click(function(){
		$("#login-username").focus();
	});
	
	$("input#login-password")
	.focus(function(){
		$("#login-password-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #ffffff");
		$(this).css("opacity",".9");
		if($(this).val() == '密码'){
			$(this).val('');
		}
	})
	.blur(function(){
		$(this).css("border","1px solid transparent");
		$(this).css("opacity",".7");
		if($(this).val() == ''){
			$("#login-password-pane .label-on").fadeIn(250);
		}
	});
	
	$("#login-password-pane .label-on").click(function(){
		$("#login-password").focus();
	});
	
	if($("input#register-username").val() == ''){
		$("#register-username-pane .label-on").fadeIn(250);
	}
	
	if($("input#register-email").val() == ''){
		$("#register-email-pane .label-on").fadeIn(250);
	}
	
	if($("input#register-password").val() == ''){
		$("#register-password-pane .label-on").fadeIn(250);
	}
	
	if($("input#register-password2").val() == ''){
		$("#register-password2-pane .label-on").fadeIn(250);
	}
	
	
	
	$("input#register-username")
	.focus(function(){
		$("#register-username-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #ffffff");
		$(this).css("background","#eeeeee");
		if($(this).val() == '选择用户名'){
			$(this).val('');
		}
	})
	.blur(function(){
		$(this).css("border","1px solid transparent");
		$(this).css("background","#ffffff");
		if($(this).val() == ''){
			$("#register-username-pane .label-on").fadeIn(250);
		}
	});
	
	$("#register-username-pane .label-on").click(function(){
		$("#register-username").focus();
	});
	
	$("input#register-email")
	.focus(function(){
		$("#register-email-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #ffffff");
		$(this).css("background","#eeeeee");
		if($(this).val() == '选择用户名'){
			$(this).val('');
		}
	})
	.blur(function(){
		$(this).css("border","1px solid transparent");
		$(this).css("background","#ffffff");
		if($(this).val() == ''){
			$("#register-email-pane .label-on").fadeIn(250);
		}
	});
	
	$("#register-email-pane .label-on").click(function(){
		$("#register-email").focus();
	});
	
	$("input#register-password")
	.focus(function(){
		$("#register-password-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #fff");
		$(this).css("background","#eeeeee");
		if($(this).val() == '输入密码'){
			$(this).val('');
		}
	})
	.blur(function(){
		//alert("hehe");
		$(this).css("border","1px solid transparent");
		$(this).css("background","#ffffff");
		if($(this).val() == ''){
			$("#register-password-pane .label-on").fadeIn(250);
		}
	})
	.keyup(function(){
		var level = password_level(), level_text = "", level_color = "#cccccc";
		if(level == 1) {
			level_text = "低";
			level_color = "#F75C2F";
		}
		if(level == 2) {
			level_text = "中";
			level_color = "#FFC408";
		}
		if(level == 3) {
			level_text = "高";
			level_color = "#7BA23F";
		}
		$("#password-secure-level").html(level_text).css("color",level_color);
	});
	
	$("#password-secure-level")
	.mouseenter(function(){
		$("#password-secure-tip").clearQueue().fadeTo(1.0, 250);
	})
	.mouseleave(function(){
		$("#password-secure-tip").clearQueue().fadeOut(250);
	});
	
	$("#register-password-pane .label-on").click(function(){
		$("#register-password").focus();
	});
	
	$("input#register-password2")
	.focus(function(){
		$("#register-password2-pane .label-on").fadeOut(250);
		$(this).css("border","1px solid #fff");
		$(this).css("background","#eeeeee");
		if($(this).val() == '再次输入密码'){
			$(this).val('');
		}
	})
	.blur(function(){
		$(this).css("border","1px solid transparent");
		$(this).css("background","#ffffff");
		if($(this).val() == ''){
			$("#register-password2-pane .label-on").fadeIn(250);
		}
	});
	
	$("#register-password2-pane .label-on").click(function(){
		$("#register-password2").focus();
	});
	
	function showRecaptcha(element) {
        Recaptcha.create("6LefftASAAAAAGbyMf9MyJfgqG7k0azwH64VfzRz", element, {
          theme: "white",
          callback: Recaptcha.focus_response_field});
      }
	
	showRecaptcha("recaptcha");
});

function deal_error(){
	if(get_error() == null) {
		return null;
	} 
	
	var error = get_error().split("?");
	var errorcode = parseInt(error[0].split("-")[1]);
	var params = error[1].split("&");
	var i = 0;
	for(;i<params.length;i++){
		var param = params[i].split("="), param_name, param_value;
		if(errorcode < 700 && errorcode >= 600) {
			param_name = "register-"+param[0];			
		}else{
			param_name = "login-"+param[0];
		}
		param_value = param[1];
		$("#"+param_name).val(param_value);		
	}
	
	if(errorcode == 403){
		$("#login-failure").html(ERROR[errorcode]).fadeIn(250);
	}
	
	if(errorcode == 601){
		$("#register-failure").html(ERROR[errorcode]).fadeIn(250);
	}
	
	if(errorcode == 602) {
		$("#register-failure").html(ERROR[errorcode]).fadeIn(250);
	}
	
	if(errorcode == 603) {
		$("#register-failure").html(ERROR[errorcode]).fadeIn(250);
	}
	
	if(errorcode == 701){
		$("#login-failure").html(ERROR[errorcode]).fadeIn(250);
	}
}

function get_error(){
	var location = window.location.toString();
	var index = location.indexOf("!");
	if(index == -1){
		return null;
	}else{
		var str = location.substring(index+1);
		return str;
	}	
}

function get_callback(){
	var location = window.location.toString();
	var index = location.indexOf("?callback");
	if(index != -1){
		return location.substring(index+10);
	}
	
	return null;
}

function validate_reg(){
	var username = $("#register-username").val();
	var email = $("#register-email").val();
	var pass1 = $("#register-password").val();
	var pass2 = $("#register-password2").val();
	if(username == "" || email == "" || pass1 == "" || pass2 == ""){
		$("#register-failure").clearQueue().fadeOut(250).html(ERROR[603]).fadeIn(250);
		return false;
	}
	
	if(!is_email(email)) {
		$("#register-failure").clearQueue().fadeOut(250).html(ERROR[605]).fadeIn(250);
		return false;
	}
	
	if(pass1 != pass2){
		$("#register-failure").clearQueue().fadeOut(250).html(ERROR[604]).fadeIn(250);
		return false;
	}
}

function password_level(){
	var password = $("#register-password").val();
	if(password.length <= 6) {
		return 1;
	}
	
	//alert(is_num(password));
	if(password.length <= 10){
		if(is_num(password)) return 1;
		else return 2;
	}
	
	if(is_num(password)) return 2;
	else return 3;
	
}

function is_num(s) {
    if(s!=null){
        var r,re;
        re = /\d+/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}

function is_email(str){
       var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
       return reg.test(str);
}
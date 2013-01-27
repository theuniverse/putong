function friend(action, username, password, id) {
	$.ajax({
		url : encodeURI("friends/"+action),
		type : "POST",
		data:{
			username: username,
			password: password,
			target: id
		},
		dataType : "json",
		success:function(json){
			alert(json.code);
			if(action == "follow") {
				$("a.friend-button")
				.html("取消关注")
				.attr("href","javascript:friend('unfollow','"+username+"','"+password+"','"+id+"')");
			}else{
				$("a.friend-button")
				.html("关注")
				.attr("href","javascript:friend('follow','"+username+"','"+password+"','"+id+"')");
			}
		}
	});
}
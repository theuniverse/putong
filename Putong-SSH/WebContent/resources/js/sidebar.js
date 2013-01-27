$(document).ready(function(){
	
	$("input#q")
	.focus(function() {
		$("#search-pane .label-on").fadeOut(250);
		$(this).css("border", "1px solid #fff");
		$(this).css("opacity", ".9");
		if ($(this).val() == '用户名') {
			$(this).val('');
		}
	})
	.blur(function() {
		$(this).css("border", "1px solid transparent");
		$(this).css("opacity", ".7");
		if ($(this).val() == '') {
			$("#search-pane .label-on").fadeIn(250);
		}
	});

	$("#search-pane .label-on")
	.fadeIn(250)
	.click(function() {
		$("#q").focus();
	});
});

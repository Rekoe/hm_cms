<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<#include "/template/admin/head.ftl" />
<script type="text/javascript">
var messageBox;
var percent;

function checkComplete(){
	$.post("v_check_complete.do",{},function(message){
		if (message.type != "success") {
			createProgress();
		}
	},"json");
}

function setBar(percent,infor, message) {
	$("#bar").attr("width",6*percent);
	$("#percent").html(percent + "%");
	infor.html(message);
}

function createProgress() {
	$.post("v_progress_data.do",{},function(data){
		$("#progressContainer").html(data);
		messageBox = $("#messageBox");
		if (percent ==0){
			setBar(percent,messageBox, "<@s.m 'rule.complete'/>");
			checkComplete();
		}
		else {
		setBar(percent,messageBox, "<@s.m 'rule.acquiting'/>");
		setTimeout(createProgress, 1000);
		}
	});
}

$(function() {
	createProgress();
});
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 采集进度</div>
	<div class="clear"></div>
</div>
<div class="body-box">
	<div id="progressContainer"></div>
</div>
</body>
</html>
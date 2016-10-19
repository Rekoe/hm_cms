<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl"/>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
function start(id) {
	$.dialog({
		type: "warn",
		content: '<@s.m "admin.dialog.updateConfirm"/>',
		ok: '<@s.m "admin.dialog.ok"/>',
		cancel: '<@s.m "admin.dialog.cancel"/>',
		onOk: function() {
			$.ajax({
				url: "start.rk",
				type: "POST",
				data: {"id":id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") 
					{
						window.location.href = "progress"
					}
				}
			});
		}
	});
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 文章 - <@s.m "global.list"/></div>
	<form class="ropt">
		<input class="add" type="submit" value="<@s.m "global.add"/>" onclick="this.form.action='add';"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" method="post">
		<@p.table value=obj;acquisition,i,has_next><#rt/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column code="ArticleCategory.name" align="center">${acquisition.name}</@p.column><#t/>
			<@p.column code="admin.common.handle" align="center">
				<a href="javascript:void(0)" onclick="start('${acquisition.id}')" class="pn-opt">[<@s.m "admin.common.acquisition.start" />]</a>
			</@p.column><#t/>
			<@p.column code="admin.common.handle" align="center"><a href="edit.rk?id=${acquisition.id}">[<@s.m "admin.common.edit" />]</@p.column><#t/>	
			<#t/>
		</@p.table>
	</@p.form>
</div>
</body>
</html>
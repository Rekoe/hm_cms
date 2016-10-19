<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<#include "/template/admin/head.ftl"/>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}

function lockUser(id,isLocked) {
	$.dialog({
		type: "warn",
		content: '<@s.m "admin.dialog.updateConfirm"/>',
		ok: '<@s.m "admin.dialog.ok"/>',
		cancel: '<@s.m "admin.dialog.cancel"/>',
		onOk: function() {
			$.ajax({
				url: "lock",
				type: "POST",
				data: {"id":id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") 
					{
						window.location.href = "list"
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
	<div class="rpos"><@s.m "global.position"/>: 管理员 - <@s.m "global.list"/></div>
	<@shiro.hasPermission name="system.user:add">
	<form class="ropt">
		<input class="add" type="submit" value="<@s.m "global.add"/>" onclick="this.form.action='add';"/>
	</form>
	</@shiro.hasPermission>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" method="post">
<@p.hidden name="pageNumber" value="${pageNo!}"/>
<@p.table value=obj;user,i,has_next><#rt/>
	<@p.column title="ID" align="center">${i+1}</@p.column><#t/>
	<@p.column code="login.username" align="center">${user.name}</@p.column><#t/>
	<@p.column code="global.lock.status" align="center"><div id="lock_${user.id}"><#if user.locked><span style="color:red"><@s.m "global.true"/></span><#else><@s.m "global.false"/></#if></div></@p.column><#t/>
	<@p.column code="global.operate" align="center">
		<@shiro.hasPermission name="system.user:update">
		<a href="edit?id=${user.id}" class="pn-opt"><@s.m "global.edit"/></a> <#rt/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="system.user:lock">
		<a href="javascript:void(0)" onclick="lockUser('${user.id}')" class="pn-opt"><#if user.locked><span style="color:red">解锁</span><#else>锁定</#if></a><#t/>
		</@shiro.hasPermission>
	</@p.column><#t/>
</@p.table>
</@p.form>
</div>
</body>
</html>
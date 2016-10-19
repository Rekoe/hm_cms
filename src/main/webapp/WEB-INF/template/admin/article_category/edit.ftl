<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl"/>
<script type="text/javascript">
$().ready(function() {
	$("#jvForm").validate();
	$('#submit').click(function() {  
			$.dialog({
				type: "warn",
				content: '<@s.m "admin.dialog.updateConfirm"/>',
				ok: '<@s.m "admin.dialog.ok"/>',
				cancel: '<@s.m "admin.dialog.cancel"/>',
				onOk: function() {
					$.ajax({
						url: "update.rk",
						type: "POST",
						data: $('#jvForm').serialize(),
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								window.location.href = "list"
							}
						}
					});
				}
			}); 
			return false;
		}); 
});
</script>
</head>
<body>
	<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: <@s.m "admin.articleCategory.edit"/> - <@s.m "global.edit"/></div>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="inputForm" labelWidth="10" method="post">
	<@p.hidden name="category.id" value="${obj.id}" />
	<@p.text label="ArticleCategory.name" value="${obj.name}" id="category.name" name="category.name" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.tree label="ArticleCategory.parent" colspan="2" name="category.parentId" required="true" value="${obj.id}" class="required"/><@p.tr/>			
	<@p.text label="admin.common.order" id="category.order" name="category.order" value="${obj.order}" required="true" class="requireField" help="只允许输入零或正整数"/><@p.tr/>
	<@p.td>
		<@p.submit code="admin.common.submit" id="submit"/>
	</@p.td>
</@p.form>
</div>
</body>
</html>
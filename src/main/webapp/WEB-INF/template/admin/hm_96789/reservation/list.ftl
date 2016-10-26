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
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 菜系 - <@s.m "global.list"/></div>
	<form class="ropt">
		<input class="add" type="submit" value="<@s.m "global.add"/>" onclick="this.form.action='add';"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" method="post">
	<@p.table value=obj;res,i,has_next><#rt/>
		<@p.column title="ID" align="center">${i+1}</@p.column><#t/>
		<@p.column title="订单ID" align="center">${res.orderId}</@p.column><#t/>
		<@p.column title="人数" align="center">${res.persion}</@p.column><#t/>
		<@p.column title="联系电话" align="center">${res.phon}</@p.column><#t/>
		<@p.column title="预定时间" align="center">${res.eatTim?string('yyyy-MM-dd HH:mm:ss')}</@p.column><#t/>
		<@p.column title="是否单间" align="center"><#if res.privateRoom>是<#else>否</#if></@p.column><#t/>
		<@p.column title="状态" align="center"><#if res.status==1>已预定<#elseif res.status==2>已预定成功<#else>未处理</#if></@p.column><#t/>
		<@p.column code="admin.common.handle" align="center"><a href="edit.rk?id=${res.id}">[<@s.m "admin.common.edit" />]</@p.column><#t/>	
		<#t/>
	</@p.table>
</@p.form>
</div>
</body>
</html>
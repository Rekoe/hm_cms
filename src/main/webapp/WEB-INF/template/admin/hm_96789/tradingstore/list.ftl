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
		<@p.hidden name="id" value="${id}"/>
		<input class="add" type="submit" value="<@s.m "global.add"/>" onclick="this.form.action='add';"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" method="post">
	<@p.hidden name="pageNumber" value="${pageNo!'1'}"/>
	<@p.hidden name="id" value="${id}"/>
	<@p.table value=obj;tradingstore,i,has_next><#rt/>
		<@p.column title="ID" align="center">${i+1}</@p.column><#t/>
		<@p.column title="菜系" align="center">${tradingstore.name}</@p.column><#t/>
		<@p.column code="admin.common.handle" align="center"><a href="edit.rk?id=${tradingstore.id}">[<@s.m "admin.common.edit" />]</@p.column><#t/>	
		<@shiro.hasPermission name="admin.hm:tradingstore:delete">
		<@p.column title="删除" align="center"><a href="javascript:void(0)" onclick="Cms.deleted('${tradingstore.id}')" class="pn-opt">[<@s.m "global.delete" />]</a></@p.column><#t/>	
		</@shiro.hasPermission>
		<#t/>
	</@p.table>
</@p.form>
</div>
</body>
</html>
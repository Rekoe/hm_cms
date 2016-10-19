<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 文章 - <@s.m "global.list"/></div>
	<@shiro.hasPermission name="admin.article:add">
	<form class="ropt">
		<input class="add" type="submit" value="<@s.m "global.add"/>" onclick="this.form.action='add';"/>
	</form>
	</@shiro.hasPermission>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" method="post">
	<@p.hidden name="pageNumber" value="${pageNo!'1'}"/>
	<@p.table value=obj;article,i,has_next><#rt/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column code="Article.title" align="center"><@htmlCut s=article.title len=50 append='...' /></@p.column><#t/>
			<@p.column code="Article.articleCategory" align="center"><a href="${base}/article/list/${article.articleCategory.id}.rk" target="_blank">${article.articleCategory.name}</a></@p.column><#t/>			
			<@p.column code="Article.isPublication" align="center">${article.publication?string('true', 'false')}&nbsp;</@p.column><#t/>
			<@p.column code="admin.common.createDate" align="center">${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</@p.column><#t/>				
			<@shiro.hasPermission name="admin.article:edit">
			<@p.column code="admin.common.handle" align="center"><a href="edit.rk?id=${article.id}">[<@s.m "admin.common.edit" />]</@p.column><#t/>			
			</@shiro.hasPermission>
			<@shiro.hasPermission name="admin.article:delete">
			<@p.column code="admin.common.handle" align="center"><a href="javascript:void(0)" onclick="Cms.deleted('${article.id}')" class="pn-opt">[<@s.m "global.delete" />]</a></@p.column><#t/>		
			</@shiro.hasPermission>
			<#t/>
	</@p.table>
</@p.form>
</div>
</body>
</html>
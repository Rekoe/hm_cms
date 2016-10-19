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
	<div class="rpos"><@s.m "global.position"/>: 绑定账号 - <@s.m "global.list"/></div>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="tableForm" action="list.rk" method="post">
	<@p.hidden name="pageNumber" value="${pageNo!'1'}"/>
	<div style="padding-top:5px; padding-bottom:5px; text-indent:10px; border-bottom:1px solid #fff; border-top:1px solid #fff;">
		<@s.m "global.type"/>:uid: <input type="text" name="uid" value="${uid!''}" style="width:180px"/>
		<input class="query" type="submit" value="搜索"/>
	</div>
	<@p.table listAction="list.rk" value=obj;bind,i,has_next><#rt/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column title="muid" align="center">${bind.mUid}</@p.column><#t/>
			<@p.column title="mpid" align="center">${bind.mPid}</@p.column><#t/>	
			<@p.column title="msid" align="center">${bind.mSid}</@p.column><#t/>		
			<@p.column title="webuid" align="center">${bind.webUid}</@p.column><#t/>
			<@p.column title="websid" align="center">${bind.webSid}</@p.column><#t/>
			<@p.column title="Addr" align="center">${bind.addr}</@p.column><#t/>
			<@p.column title="admin.common.createDate" align="center">${bind.time?string("yyyy-MM-dd HH:mm:ss")}</@p.column><#t/>				
			<@shiro.hasPermission name="admin.bind.uid:delete">
			<@p.column code="admin.common.handle" align="center"><a href="javascript:void(0)" onclick="Cms.deleted('${bind.id}')" class="pn-opt">[<@s.m "global.delete" />]</a></@p.column><#t/>		
			</@shiro.hasPermission>
			<#t/>
	</@p.table>
</@p.form>
</div>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加菜系 - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 惠民菜系 - <@s.m "global.add"/></div>
	<form class="ropt">
		<input type="submit" value="<@s.m "global.backToList"/>" onclick="this.form.action='list.rk';" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="jvForm" action="save" labelWidth="10" method="post" onsubmit="return false;">
	<@p.text label="菜系" id="cuisine.name" name="cuisine.name" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.td colspan="2"><@p.submit code="global.submit" onclick="Cms.add();"/></@p.td>
</@p.form>
</div>
</body>
</html>
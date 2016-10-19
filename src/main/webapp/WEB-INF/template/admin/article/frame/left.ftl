<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>文章管理</title>
<#include "/template/admin/head.ftl"/>
<script type="text/javascript">
$(function(){
        Cms.lmenu('lmenu');
    });
</script>
<style>
h3{ padding:0; margin:0; font-weight:normal; font-size:12px;}
</style>
</head>
<body class="lbody">
<div class="left">
<#include "/template/admin/date.ftl"/>
     <ul class="w-lful"><li><@s.m "global.admin.index"/></li></ul>
	<@perm_chow perm="admin.article">
	<h3>文章管理</h3>
	<div style="margin:0; padding:0;">
		<div class="leftmenuBG"><a href="${base}/admin/article/list" target="rightFrame">文章列表</a></div>
		<div class="leftmenuBG"><a href="${base}/admin/article_category/list" target="rightFrame">文章分类列表</a></div>
	</div>
	</@perm_chow>
</div>
</body>
</html>
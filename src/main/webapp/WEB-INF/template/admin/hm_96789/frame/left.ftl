<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>采集器</title>
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
	<@perm_chow perm="admin.hm:resinf">
	<h3>店鋪管理</h3>
	<div style="margin:0; padding:0;">
		<div class="leftmenuBG"><a href="${base}/admin/hm/resinf/list.rk" target="rightFrame">店鋪列表</a></div>
	</div>
	</@perm_chow>
	<@perm_chow perm="admin.hm:cuisine">
	<h3>惠民菜系管理</h3>
	<div style="margin:0; padding:0;">
		<div class="leftmenuBG"><a href="${base}/admin/hm/cuisine/list.rk" target="rightFrame">菜系列表</a></div>
	</div>
	</@perm_chow>
	<@perm_chow perm="admin.hm:tradingstore:build">
	<h3>系统设置</h3>
	<div style="margin:0; padding:0;">
		<div class="leftmenuBG"><a href="${base}/admin/hm/tradingStore/build.rk" target="rightFrame">构建索引</a></div>
		<div class="leftmenuBG"><a href="${base}/admin/hm/tradingStore/m_s.rk" target="rightFrame">搜索</a></div>
		<div class="leftmenuBG"><a href="${base}/admin/hm/reservation/list.rk" target="rightFrame">订单列表</a></div>
	</div>
	</@perm_chow>
</div>
</body>
</html>
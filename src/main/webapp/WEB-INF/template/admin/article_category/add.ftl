<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.add" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<script src="${base}/res/common/js/jquery.form.min.js" type="text/javascript"></script> 
<script type="text/javascript">
$(function() {
		$("#jvForm").validate();
		var options = {
			success:	afterSuccess,  // post-submit callback  
			resetForm: true       // reset the form after successful submit 
		}; 
		 $('#jvForm').submit(function() {
			$(this).ajaxSubmit(options); 
			return false; 
		}); 
	});
	function afterSuccess()
	{
		$.message({type: "success", content: "成功"});
		window.location.href = "list.rk"
	}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 文章分类 - <@s.m "global.add"/></div>
	<form class="ropt">
		<input type="submit" value="<@s.m "global.backToList"/>" onclick="this.form.action='list.rk';" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="jvForm" action="save" labelWidth="10" method="post">
		<@p.text label="ArticleCategory.name" id="name" name="name" required="true" class="required" maxlength="40"/><@p.tr/>
		<@p.tree label="Article.articleCategory" name="ac.parentId" required="true" class="required" category=true/><@p.tr/>
		<@p.text label="admin.common.order" id="order" name="order" required="true" class="required" help="只允许输入零或正整数"/><@p.tr/>	
		<@p.td colspan="2"><@p.submit code="global.submit"/> &nbsp; <@p.reset code="global.reset"/></@p.td>
	</@p.form>
</div>
</body>
</html>
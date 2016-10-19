<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.add" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<script src="${base}/res/common/js/jquery.form.min.js" type="text/javascript"></script> 
<style type="text/css">
.sel-disabled{background-color:#ccc}
</style>
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
	<div class="rpos"><@s.m "global.position"/>: 文章 - <@s.m "global.add"/></div>
	<form class="ropt">
		<input type="submit" value="<@s.m "global.backToList"/>" onclick="this.form.action='list.rk';" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
	<@p.form id="jvForm" labelWidth="10"action="save.rk" method="post" tableClass="input">
	<@p.text label="Article.title" colspan="2" id="art.title" name="art.title" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.tree label="Article.articleCategory" colspan="2" name="art.articleCategoryId" required="true" class="required" /><@p.tr/>
	<@p.shiroAuthor label="Article.author" colspan="2" id="art.author" name="art.author" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.radio width="50" colspan="2" label="Article.isTop" id="art.top" name="art.top" value="false" list={"false":"否","true":"是"}/><@p.tr/>
	<@p.radio width="50" colspan="2" label="Article.isPublication" id="art.publication" name="art.publication" value="true" list={"false":"否","true":"是"}/><@p.tr/>	
	<@p.editor value="" colspan="2" label="Article.content" name="art.content" required="true" /><@p.tr/>
	<@p.td>
		<@p.submit code="admin.common.submit" id="submit"/>
	</@p.td>
</@p.form>
</div>
</body>
</html>
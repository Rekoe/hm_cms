<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加菜系 - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<style type="text/css">
.authorities label {
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;
}
</style>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#jvForm");
	var $selectAll = $("#jvForm .selectAll");
	$selectAll.click(function() {
		var $this = $(this);
		var $thisCheckbox = $this.closest("tr").find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.prop("checked", false);
		} else {
			$thisCheckbox.prop("checked", true);
		}
		return false;
	});
});
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 惠民商家 - <@s.m "global.add"/></div>
	<form class="ropt">
		<input type="submit" value="<@s.m "global.backToList"/>" onclick="this.form.action='list.rk';" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<@p.form id="jvForm" action="o_save" labelWidth="10" method="post" onsubmit="return false;">
	<@p.text label="菜系" id="resinf.name" name="resinf.name" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.text label="拼音" id="resinf.pinyin" name="resinf.pinyin" required="true" class="required" maxlength="40"/><@p.tr/>
 	<tr class="authorities">
		<th width="15%" class="pn-flabel pn-flabel-h"><a href="javascript:;" class="selectAll" title="<@s.m "global.selectAll" />">菜系</a></th>
		<td width="30%" class="pn-fcontent">
			<span class="fieldSet">
			<#list obj as cuisine>
				<label><input value="${cuisine.id}" type="checkbox" name="resinf.cuisines[${cuisine_index}].id">${cuisine.name} </label>
			</#list>
			</span>
		</td>
	</tr>
	<@p.td colspan="2"><@p.submit code="global.submit" onclick="Cms.add();"/></@p.td>
</@p.form>
</div>
</body>
</html>
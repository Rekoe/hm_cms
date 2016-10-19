<div class="list">
	<ul>
		<#list obj.list as new>
	    <li><label>[${new.articleCategory.name}]</label><a data-pjax href="${base}/news/content/${new.id}">${new.title}</a><span>${new.createDate?string("yyyy-MM-dd")}</span></li>
	    </#list>
	</ul>
</div>
<div class="pager">
	<@pagination pageNumber = obj.pageNo totalPages = obj.totalPage pattern = "">
		<#include "/template/front/common/pagination.ftl">
	<script type="text/javascript">
	function pageSkip(segmentPageNumber) {
		$("#tags").load("${base}/news/view/",{id:'${tag}',pageNumber:segmentPageNumber})
		return false;
	}
</script>
	</@pagination>
</div>

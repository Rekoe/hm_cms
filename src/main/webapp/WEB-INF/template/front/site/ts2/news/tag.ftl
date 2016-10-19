<#list obj as new>
<li><label>[${new.articleCategory.name}]</label><a data-pjax href="${base}/news/content/${new.id}"><@htmlCut s=new.title len=16 append='...' /></a><span>${new.createDate?string("yyyy-MM-dd")}</span></li>
</#list>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>《塔防三国志》手游_经典新铸，众盼来袭！</title>
		<#include "/template/front/site/ts2/common/head.ftl" />
    <link type="text/css" rel="stylesheet" href="${base}/res/cms/site/ts2/styles/class.css" />
    <script type="text/javascript">
		function load(id){
			$("#tags").load("${base}/news/view/",{id:id,pageNumber:'1'});
			return false;
		}
		$().ready(function() {
			$.ajax({type:"POST",url:"${base}/news/tags",dataType: "json",success: function(data){
	        	var nav = '';
	            $.each(data,function(i,item){
	            	var id = item.id;
	            	var loadStr = "load(";
	            	    loadStr += "'";
	            	    loadStr += item.id;
	            	    loadStr += "'";
	            	    loadStr += ")";
	               if(i==0){
	                 nav += '<li class="act"><a href="javascript:void(0);" onclick="return '+loadStr+'">'+item.name+'</a></li>';
	                 load(id);
	               }else{
	                 nav += '<li><a href="javascript:void(0);" onclick="return '+loadStr+'">'+item.name+'</a></li>';
	               }
	            }) 
	           $(".content .nav").html(nav); 
	           $(".content .nav li").click(function () {
	            	$(".content .nav li").removeClass("act").eq($(this).index()).addClass("act");
	            	$(".content .list").css("display", "none").eq($(this).index()).css("display", "block");
	        	});
	         }  
	    	});
		});
</script>
</head>
<body>
    <div class="panel class">
        <div class="fixs">
            <a href="javascript:;" onClick="alert('iOS下载暂未开启哦！请主公耐心等待。');"><img src="${base}/res/cms/site/ts2/images/ios.png" /></a>
            <a href="http://fblx.dnion.com/APK/tf2_shanggame.apk"><img src="${base}/res/cms/site/ts2/images/android.png" /></a>
            <img src="${base}/res/cms/site/ts2/images/qr.png" />
        </div>
        <div class="content">
            <div class="addr">您当前的位置：首页>新闻中心</div>
            <ul class="nav">
                <li class="act"><a href="javascript:;">新闻</a></li>
                <li><a href="javascript:;">公告</a></li>
                <li><a href="javascript:;">活动</a></li>
            </ul>
            <div id="tags">
                
            </div>
        </div>
    </div>
    <div class="footer">
       <#include "/template/front/site/ts2/common/footer.ftl" />
    </div>
<div style="display: none;"> ads </div></body>
</html>

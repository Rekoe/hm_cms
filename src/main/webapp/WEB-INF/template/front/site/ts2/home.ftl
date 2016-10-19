<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>《塔防三国志》手游_经典新铸，众盼来袭！</title>
   	<#include "/template/front/site/ts2/common/head.ftl" />
    <script type="text/javascript">
		function load(id){
			$("#rk").load("${base}/news/rk/"+id);
			return false;
		}
		$().ready(function() {
			$.ajax({type:"POST",url:"${base}/news/tags",dataType: "json",success: function(data){
	        	var nav = '<ul>';
	            $.each(data,function(i,item){
	            	var id = item.id;
	            	var loadStr = "load(";
	            	    loadStr += "'";
	            	    loadStr += id;
	            	    loadStr += "'";
	            	    loadStr += ")";
	               if(i==1){
	                 nav += '<li class="act"><a href="javascript:void(0);" onclick="return '+loadStr+'">'+item.name+'</a></li>';
	                 load(id)
	               }else if(i < 3){
	                 nav += '<li><a href="javascript:void(0);" onclick="return '+loadStr+'">'+item.name+'</a></li>';
	               }
	            }) 
	            nav +='</ul>';
	            nav +='<a data-pjax href="${base}/news/list" class="ST">更多></a>';
	           $(".news .nav").html(nav); 
	           $(".news .nav ul li").click(function () {
                	$(".news .nav ul li").removeClass("act").eq($(this).index()).addClass("act");
            	});
	         }  
	    	});
		});
</script>
</head>
<body>
    <div class="panel">
        <div class="row">
            <div class="slide">
                <div class="s_c">
                    <img src="${base}/res/cms/site/ts2/images/s1.jpg" />
                    <img src="${base}/res/cms/site/ts2/images/s2.jpg" />
                    <img src="${base}/res/cms/site/ts2/images/s3.jpg" />
                </div>
            </div>
            <div class="news">
                <div class="nav">
                    <ul>
                        <li class="act">新闻</li>
                        <li>公告</li>
                        <li>活动</li>
                    </ul>
                    <a href="class.html" class="ST">更多></a>
                </div>
                <div>
                    <div class="list">
                        <div><a target="_blank" href="https://mp.weixin.qq.com/s?__biz=MjM5OTgzNzkyNA==&mid=2653182711&idx=3&sn=28baf85330638d1e3e4c04175a8e7d5f&scene=1&srcid=0722I5vhCbXyArVKd2syaJTM&key=77421cf58af4a6535d264699f67fd73bfa2c3303f546d9dc97644f6be9c304257fb844e5663e0db156a5fe79102306f2&ascene=1&uin=MTY1MzI1NTQ4MA%3D%3D&devicetype=Windows-QQBrowser&version=61030003&pass_ticket=qTw%2B5fch%2BGWQzd1XLYgtBFAQE%2F9J9li1fZvBAX8PHqPAAlhn%2F9zLXa9wevaYd%2BBv">专注细分领域 上游推新品《塔防三国志手游》</a></div>
                        <ul id="rk">
                            <li><label>[公告]</label><a target="_blank"href="content7.html">塔三手游版安卓版付费删档测试开启公告</a><span>2016-07-27</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="row hero">
            <div class="title ST"><img src="${base}/res/cms/site/ts2/images/icon.png" />英雄展示</div>
            <div class="show">
                <ul>
                    <li class="act">吕布</li>
                    <li>曹操</li>
                    <li>貂蝉</li>
                </ul>
                <div>
                    <div><img src="${base}/res/cms/site/ts2/images/show2.png"/></div>
                    <div><img src="${base}/res/cms/site/ts2/images/show4.png"/></div>
                    <div><img src="${base}/res/cms/site/ts2/images/show5.png"/></div>
                    <div><img src="${base}/res/cms/site/ts2/images/show1.png"/></div>
                    <div><img src="${base}/res/cms/site/ts2/images/show3.png"/></div>
                </div>
            </div>
            <div class="show_js">
                <img class="sw" src="${base}/res/cms/site/ts2/images/lvbu.png" />
                <img class="sw" src="${base}/res/cms/site/ts2/images/caocao.png" />
                <img class="sw" src="${base}/res/cms/site/ts2/images/diaochan.png" />
                <img class="sw" src="${base}/res/cms/site/ts2/images/zhaoyun.png" />
                <img class="sw" src="${base}/res/cms/site/ts2/images/zhangfei.png" />
            </div>
        </div>
        <div class="row video">
            <div class="title ST"><img src="${base}/res/cms/site/ts2/images/icon.png" />游戏视频</div>
            <ul>
                <li><img src="${base}/res/cms/site/ts2/images/l4.jpg" /><div></div><a href="javascript:;" data-video="flvplayer"><img src="${base}/res/cms/site/ts2/images/in.png" /></a></li>
                <li><img src="${base}/res/cms/site/ts2/images/l1.jpg" /><div></div><a href="javascript:;" data-video="flvplayer"><img src="${base}/res/cms/site/ts2/images/in.png" /></a></li>
                <li><img src="${base}/res/cms/site/ts2/images/l2.jpg" /><div></div><a href="javascript:;" data-video="flvplayer"><img src="${base}/res/cms/site/ts2/images/in.png" /></a></li>
                <li><img src="${base}/res/cms/site/ts2/images/l3.jpg" /><div></div><a href="javascript:;" data-video="flvplayer"><img src="${base}/res/cms/site/ts2/images/in.png" /></a></li>
            </ul>
        </div>
        <div class="row mt">
            <div class="title ST"><img src="${base}/res/cms/site/ts2/images/icon.png" />合作媒体</div>
            <div class="box">
                <ul class="list">
                    <li><a target="_blank" href="http://www.shouyou.com/"><img src="${base}/res/cms/site/ts2/images/mt/1.png" /></a></li>
                    <li><a target="_blank" href="http://www.18183.com/"><img src="${base}/res/cms/site/ts2/images/mt/2.png" /></a></li>
                    <li><a target="_blank" href="http://www.tgbus.com/"><img src="${base}/res/cms/site/ts2/images/mt/3.png" /></a></li>
                    <li><a target="_blank" href="http://www.tgbus.com/"><img src="${base}/res/cms/site/ts2/images/mt/4.png" /></a></li>
                    <li><a target="_blank" href="http://www.mofang.com/"><img src="${base}/res/cms/site/ts2/images/mt/5.png" /></a></li>
                    <li><a target="_blank" href="http://www.appgame.com/"><img src="${base}/res/cms/site/ts2/images/mt/6.png" /></a></li>
                    <li><a target="_blank" href="http://www.uuu9.com/"><img src="${base}/res/cms/site/ts2/images/mt/7.png" /></a></li>
                    <li><a target="_blank" href="http://www.youxiduo.com/"><img src="${base}/res/cms/site/ts2/images/mt/8.png" /></a></li>
                    <li><a target="_blank" href="http://www.joyme.com/"><img src="${base}/res/cms/site/ts2/images/mt/9.png" /></a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer">
		<#include "/template/front/site/ts2/common/footer.ftl" />
    </div>
    <div class="fix_r">
        <a href="javascript:;" onClick="alert('iOS下载暂未开启哦！请主公耐心等待。');"><img src="${base}/res/cms/site/ts2/images/ios.png" /></a>
        <a href="http://fblx.dnion.com/APK/tf2_shanggame.apk"><img src="${base}/res/cms/site/ts2/images/android.png" /></a>
        <img src="${base}/res/cms/site/ts2/images/qr.png" />
    </div>
    <script type="text/javascript">
        $(function () {
            if (window.screen.width < 600)
                window.location.href = 'mobile.html';
            $('.slide .s_c').slidesjs({
                navigation: false,
                width: 605,
                height: 405,
                pause: 0,
                play: {
                    auto: true,
                    interval: 4000
                }
            });
            
            $(".show ul li").click(function () {
                $(".show ul li").removeClass("act").eq($(this).index()).addClass("act");
                $(".show div div").css("display", "none").eq($(this).index()).css("display", "block");
                $(".hero .show_js .sw").css("display", "none").eq($(this).index()).css("display", "block");
            });
            $(".video li a").click(function () {
                var url = $(this).attr("data-video");
                $.blockUI({ message: '<div class="vido"><a href="javascript:;" onclick="javascript:$.unblockUI();"><img src="${base}/res/cms/site/ts2/images/btn_close_none_focus.png" /></a><object><param name="quality" value="high"><param name="scale" value="noscale"><param name="wmode" value="opaque"><embed width="640" height="360" flashvars="file=' + url + '&amp;image=&amp;width=640&amp;height=360&autostart=true&repeat=true" allowfullscreen="true" quality="high" src="${base}/res/cms/site/ts2/player/flvplayer.swf" type="application/x-shockwave-flash" quality="high" scale="noscale" wmode="opaque"></object>', css: { width: "640", height: "360", top: "50%", left: "50%", margin: "-180px 0px 0px -320px", border: "5px #ccc solid" }, onOverlayClick: $.unblockUI });
            });
            $('.mt').cxScroll({
                direction: "top",
                step: 2
            });
        });
    </script>
</body>
</html>

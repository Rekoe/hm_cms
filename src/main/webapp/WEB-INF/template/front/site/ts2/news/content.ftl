<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>${obj.title}</title>
    <link type="text/css" rel="stylesheet" href="${base}/res/cms/site/ts2/styles/class.css" />
	<#include "/template/front/site/ts2/common/head.ftl" />
</head>
<body>
    <div class="panel class">
        <div class="fixs">
            <a href="javascript:;" onClick="alert('iOS下载暂未开启哦！请主公耐心等待。');"><img src="${base}/res/cms/site/ts2/images/ios.png" /></a>
            <a href="http://fblx.dnion.com/APK/tf2_shanggame.apk"><img src="${base}/res/cms/site/ts2/images/android.png" /></a>
            <img src="${base}/res/cms/site/ts2/images/qr.png" />
        </div>
        <div class="content">
            <div class="head">
                <h1>新闻中心</h1>
                <div class="addr">您当前的位置：<a data-pjax href="${base}/news/list">首页</a>>新闻中心>${obj.articleCategory.name}</div>
            </div>
            <h2>${obj.title}</h2>
            <div class="shard">
                <span>分享到：</span>
                <!-- JiaThis Button BEGIN -->
                <div class="jiathis_style">
                    <a class="jiathis_button_qzone"></a>
                    <a class="jiathis_button_tsina"></a>
                    <a class="jiathis_button_tqq"></a>
                    <a class="jiathis_button_weixin"></a>
                    <a class="jiathis_button_renren"></a>
                    <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                </div>
                <!-- JiaThis Button END -->
            </div>
            <div class="author">作者： ${obj.author}  来自：    ${obj.createDate?string("yyyy-MM-dd")}</div>
            <div class="des">
                ${obj.content}
            </div>
        </div>
    </div>
    <div class="footer">
 		<#include "/template/front/site/ts2/common/footer.ftl" />
    </div>
<div style="display: none;"> </div>
    <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
</body>
</html>
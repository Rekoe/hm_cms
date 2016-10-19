<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no" />
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <script src="${base}/res/common/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${base}/res/common/js/jquery.form.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
$("#frmLogin").validate({ 
	    submitHandler: function(form) { 
	      UpdateStatus();
          return false;//阻止表单提交
	    } 
	});
	$.ajax({
        url: "${base}/api/v1/bind/webslist.rk",
        type: 'POST',
        dataType: 'JSON',
        timeout: 5000,
        error: function() { alert('Error loading data!'); },
        success: function(msg) {
        	$("[id='b.webSid']").empty();
            $.each(eval(msg), function(i, item) {
            	if(i==0){
            		$("<option value='" + item.id + "' selected='selected'>" + item.name + "</option>").appendTo($("[id='b.webSid']"));
            	} else{
            		$("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("[id='b.webSid']"));
            	}
            });
          $("[id='b.webSid']").selectmenu('refresh', true);
        }
    });
   $.ajax({
        url: "${base}/api/v1/bind/getlist.rk",
        type: 'POST',
        dataType: 'JSON',
        timeout: 5000,
        error: function() { alert('Error loading data!'); },
        success: function(msg) {
        	$("[id='b.mPid']").empty();
            $.each(eval(msg), function(i, item) {
            	if(i==0){
            		$("<option value='" + item.id + "' selected='selected'>" + item.name + "</option>").appendTo($("[id='b.mPid']"));
            	} else{
            		$("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("[id='b.mPid']"));
            	}
            });
          $("[id='b.mPid']").selectmenu('refresh', true);
          loadmSid($("[id='b.mPid']").val());
        }
    });
	 $("[id='b.mPid']").change(function() {
        loadmSid($("[id='b.mPid']").val());
    });
	
	function loadmSid(parentid) {
        $.ajax({
            url: '${base}/api/v1/bind/getlist2/'+ parentid,
            type: 'POST',
            dataType: 'JSON',
            timeout: 5000,
            error: function() { alert('Error loading data!'); },
            success: function(msg) {
                $("[id='b.mSid']").empty();
                $.each(eval(msg), function(i, item) {
                	if(i==10){
                		$("<option value='" + item.id + "' selected='selected'>" + item.name+ "</option>").appendTo($("[id='b.mSid']"));
                	} else{
                		$("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("[id='b.mSid']"));
                	}
                });
                $("[id='b.mSid']").selectmenu( "refresh", true )
            }
        });
    }
    hideMask();
}); 
function showMask(){     
        $("#mask").css("height",$(document).height());     
        $("#mask").css("width",$(document).width());     
        $("#mask").show();     
    }  
    //隐藏遮罩层  
    function hideMask(){     
        $("#mask").hide();     
    } 
function UpdateStatus() {
    var popupDialogId = 'popupDialog';
    $('<div data-role="popup" id="' + popupDialogId + '" data-confirmed="no" data-transition="pop" data-overlay-theme="b" data-theme="b" data-dismissible="false" style="min-width:216px;max-width:500px;"> \
                    \
                    <div role="main" class="ui-content">\
                        <h3 class="ui-title" style="color:#fff; text-align:center;margin-bottom:15px">确认绑定账号吗？</h3>\
                        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionConfirm" data-rel="back" style="background: #1784fd;width: 33%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 12%;font-weight: 100;">确定</a>\
                        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionCancel" data-rel="back" data-transition="flow" style="background: #DBDBDB;width: 33%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 5%;font-weight: 100;color: #333;text-shadow: none;">取消</a>\
                    </div>\
                </div>')
        .appendTo($.mobile.pageContainer);
    var popupDialogObj = $('#' + popupDialogId);
    popupDialogObj.trigger('create');
    popupDialogObj.popup({
        afterclose: function (event, ui) {
            popupDialogObj.find(".optionConfirm").first().off('click');
            var isConfirmed = popupDialogObj.attr('data-confirmed') === 'yes' ? true : false;
            $(event.target).remove();
            if (isConfirmed) {
            	showMask();
               $.ajax({  
		              type: "POST",  
		              url: "${base}/api/v1/bind/submit.rk",  
		              data: $('#frmLogin').serialize(),  
		              success:function(message){ 
		              	if(message.type == "success"){
		              		$.mobile.changePage($('#page-success'),{ role: "dialog" });
		              	}else{
		              		$('#bind_error').html(message.content);
		              		$.mobile.changePage($('#page-error'),{ role: "dialog" });
		              	} 
		              	hideMask();
		              }  
		       }); 
            }
        }
    });
    popupDialogObj.popup('open');
    popupDialogObj.find(".optionConfirm").first().on('click', function () {
        popupDialogObj.attr('data-confirmed', 'yes');
    });
} 

</script>
<style type="text/css">
    input#name {
    width: 100% !important;
}
div.ui-input-text { width: 275px !important }
.item-title {padding: 25px 10px; }
#fixed-fullscreen-content {margin-top: 0; }
.mask {       
position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;     
z-index: 1002; left: 0px;     
opacity:0.5; -moz-opacity:0.5;     
}  
div.main
{
    position: absolute;
    left: 50%;
    top: 80%;
    margin: -100px 0 0 -162px;
    color: White;
    font-family: 微软雅黑;
}
</style>
</head>
<body>
<div id="mask" class="mask"></div>   
<!--登陆页面-->
<div data-role="page" id="pageLogin">
    <div data-role="header">
        <h1 role="heading">塔防三国志账号绑定</h1>
    </div>
    <div data-role="content">
        <form method="post" id="frmLogin" action="${base}/api/v1/bind/submit" class="validate" onsubmit="return false">
            <fieldset data-role="fieldcontain">
               <input name="b.mUid" id="b.mUid" placeholder="塔防三国志手游uid" type="number" class="required">
               <fieldset data-role="controlgroup" data-type="horizontal">
	                <select id='b.mPid' name="b.mPid" class="required" data-native-menu="true">
	               		<option value="0">选择平台</option>
	                </select>
	                <select id='b.mSid' name="b.mSid" class="required" data-native-menu="true">
	                	<option value="0">选择平台</option>
	                </select>
                </fieldset>
            </fieldset>
		   <fieldset data-role="fieldcontain">
			    <input name="b.webUid" id="b.webUid" placeholder="塔防三国志页游uid" type="number" class="required">
			    <fieldset data-role="controlgroup" data-type="horizontal">
				   	<select name="b.webSid" id="b.webSid" data-native-menu="true">
					    <option value="mon">Monday</option>
					    <option value="tue">Tuesday</option>
					    <option value="wed">Wednesday</option>
					</select>
			    </fieldset>
		   </fieldset>
            <div class="ui-btn-inner">  
                <button class="btnLogin" type="submit" data-icon="check" id="submitbtn" data-theme="y" data-inline="true">确定</button> 
            </div>
            <div class="ui-btn-inner">温馨提示：<br/>
				1、请主公朋友们绑定前，注意确认信息是否正确，一经绑定将无法解除绑定<br/>
				2、请主公朋友们输入主公ID时注意主公ID前是否有负号。<br/>
				塔三3D手游(iOS/安卓版)下载请扫以下二维码 <br/>
				<img src="${base}/res/cms/site/ts2/images/apk.png" />
			</div> 
        </form>
    </div>
    <!--div data-role="footer" data-position="fixed" data-theme="c">
        <h4>版权所有 © 上游信息科技(上海)有限公司 </h4>
	</div-->
</div>
<div data-role="page" id="page-success" class="dialog-actionsheet">
	<div data-role="header" data-theme="e">
		<h1>绑定结果</h1>
	</div><!-- /header -->
	<div data-role="content" data-theme="e">
		<p class="ui-btn ui-icon-check ui-btn-icon-left">恭喜,操作成功</p>
		<a href="#home" data-role="button" data-theme="b" data-rel="back">回到首页</a>   
	</div>
</div>
<div data-role="page" id="page-error" class="dialog-actionsheet">
	<div data-role="header" data-theme="e">
		<h1>绑定结果</h1>
	</div><!-- /header -->
	<div data-role="content" data-theme="e">
		<p id="bind_error" class="ui-btn ui-icon-alert ui-btn-icon-left"></p>
		<a href="#home" data-role="button" data-theme="b" data-rel="back">回到首页</a>   
	</div>
</div>
</body>
</html>
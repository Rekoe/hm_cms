<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>账号绑定 - Powered By 上游科技</title>
<meta name="author" content="上游科技 Team" />
<meta name="copyright" content="上游科技" />
<link href="${base}/res/common/css/common.css" rel="stylesheet" type="text/css" />
<link href="http://b2c.demo.shopxx.net/resources/shop/default/css/register.css" rel="stylesheet" type="text/css" />
<script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="${base}/res/common/js/jquery.validate.js" type="text/javascript"></script>
<script src="${base}/res/common/js/jquery.form.min.js" type="text/javascript"></script>
<script src="${base}/res/common/js/pony.js" type="text/javascript"></script>
<script src="${base}/res/cms/js/admin.js?2016090901" type="text/javascript"></script>
<script src="${base}/res/common/js/base.js?201400623" type="text/javascript"></script>
<script type="text/javascript">
$().ready(function() {
$("#frmLogin").validate({ 
	    submitHandler: function(form) { 
	      $.dialog({
				type: "warn",
				content: '确定要做账号绑定？',
				ok: '<@s.m "admin.dialog.ok"/>',
				cancel: '<@s.m "admin.dialog.cancel"/>',
				onOk: function() {
					$.ajax({
						url: "save.rk",
						type: "POST",
						data: $('#jvForm').serialize(),
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								window.location.href = "list"
							}
						}
					});
				}
			}); 
			return false;
	    } 
	});
	$.ajax({
        url: "${base}/api/v1/bind/webslist.rk",
        type: 'POST',
        dataType: 'JSON',
        timeout: 5000,
        error: function() { alert('Error loading data!'); },
        success: function(msg) {
        	$("[id='b.wSid']").empty();
            $.each(eval(msg), function(i, item) {
            	if(i==0){
            		$("<option value='" + item.id + "' selected='selected'>" + item.name + "</option>").appendTo($("[id='b.wSid']"));
            	} else{
            		$("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("[id='b.wSid']"));
            	}
            });
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
            }
        });
    }
});
</script>
</head>
<body>
<div class="header">
	<div class="top">
	</div>
</div>
	<div class="container register">
		<div class="row">
			<div class="span12">
				<div class="wrap">
					<div class="main clearfix">
						<div class="title">
							<strong>塔防三国志账号绑定</strong>USER BIND
						</div>
						<form method="post" id="frmLogin" action="${base}/api/v1/bind/submit" class="validate" onsubmit="return false">
							<table>
								<tr>
									<th><span class="requiredField">*</span>uid(塔防三国3D):</th>
									<td><input type="text" name="b.mUid" id="b.mUid" maxlength="20" class="required"/></td>
								</tr>
								<tr>
									<th>
										<span class="requiredField">*</span>服务器:
									</th>
									<td>
										<select id='b.mPid' name="b.mPid" class="required" >
						               		<option value="0">选择平台</option>
						                </select>
						                <select id='b.mSid' name="b.mSid" class="required">
						                	<option value="0">选择平台</option>
						                </select>
									</td>
								</tr>
								<tr>
									<th><span class="requiredField">*</span>uid(塔防三国页游):</th>
									<td>
										<input type="text" name="b.webUid" id="b.webUid maxlength="20" autocomplete="off" class="required"/>
									</td>
								</tr>
								<tr>
									<th>
										<span class="requiredField">*</span>服务器:
									</th>
									<td>
										<select name="b.wSid" id="b.wSid">
										    <option value="mon">Monday</option>
										    <option value="tue">Tuesday</option>
										    <option value="wed">Wednesday</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										&nbsp;
									</th>
									<td>
										<input type="submit" class="submit" id="submit" value="确定绑定" />
									</td>
								</tr>
								<tr>
								<th>&nbsp;
									</th>
								<td>
								免责声明：<br/>
									1、请主公朋友们绑定前，注意确认信息是否正确，一经绑定将无法解除绑定<br/>
									2、请主公朋友们输入主公ID时注意主公ID前是否有负号。<br/>
								</td></tr>
							</table>
							<div class="login">
								<img src="${base}/res/cms/site/ts2/images/apk.png" />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<div class="footer">
	<div class="bottom">
		<div class="bottomNav">
		</div>
		<div class="info">
			<p>ICP备案编号：沪ICP备14044016号 网络文化经营许可证：沪网文【2013】0687-102号</p>
			<p>版权所有 © 上游信息科技(上海)有限公司 未经许可 严禁复制</p>
		</div>
	</div>
</div>
</body>
</html>

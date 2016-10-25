<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>搜索菜系 - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<#-- 
https://my.oschina.net/zimingforever/blog/63877
  -->
<style>
	body {
		font-family: Arial, Helvetica, sans-serif;
	}
	table {
		font-size: 1em;
	}
	.ui-draggable, .ui-droppable {
		background-position: top;
	}
  .ui-autocomplete-loading {
    background: white url('${base}/res/common/images/ui-anim_basic_16x16.gif') right center no-repeat;
  }
  #city { width: 25em; }
  </style>
<script>
  $(document).ready(function(){    
    $( "#city" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "search.rk",
                    type:"post",
                    dataType: "json",
                    data: {
                        q: request.term
                    },
                    success: function( data ) {
                        //response([{id: data.id, label: data.label, value: data.value}]);
                        response(
                            $.map( data.info, function( item ) {
                            return {
                                label: item.id,
                                value: item.value
                            }
                        }));
                        
                    }
                });
            },
            focus: function( event, ui ) {
                $( "#project" ).val( ui.item.label );
                return false;
            },
            minLength: 2,
            select: function( event, ui ) {
                log( ui.item ? ui.item.label + " ext. " + ui.item.value: "Nothing found");
            },
            open: function() {
                $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
            },
            close: function() {
                $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
            }
    })
    .data("autocomplete" )._renderItem = function( ul, item ) {
          return $("<li></li>" ).data( "item.autocomplete", item ).append( "<a>" + item.label + "&nbsp;ext.&nbsp;" + item.value + "</a>" ).appendTo( ul );
    };
});
  function log( message ) {
    $( "<div/>" ).text( message ).hide().prependTo( "#log" ).fadeIn('slow');
    $("#log div").css({'background':'#555','margin':'1px 4px 1px 2px', 'padding':'5px 0px','color':'#E5E5E5'});
    $( "#log" ).attr( "scrollTop", 0 );
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: 惠民商家 - 搜索</div>
	<div class="clear"></div>
</div>
<div class="body-box">
	<div class="ui-widget">
	  <label for="city">需要搜索的数据：</label>
	  <input id="city"> 
    </div>
  	<div class="ui-widget" style="margin-top:2em; font-family:Arial">
	  结果： <div id="log" style="height: 200px; width: 80%; overflow: auto;" class="ui-widget-content"></div>
  	</div>
<div id="sub">
<@p.form id="jvForm" action="o_save" labelWidth="10" method="post" onsubmit="return false;">
	<@p.text label="商圈" id="res.name" name="res.name" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.text label="地址" id="res.addr" name="res.addr" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.text label="电话" id="res.phone" name="res.phone" required="true" class="required" maxlength="40"/><@p.tr/>
 	<@p.textarea label="附近酒店" name="res.hotel" cols="50" rows="5" class="textbox required" />
	<@p.td colspan="2"><@p.submit code="global.submit" onclick="Cms.addBack('list.rk');"/></@p.td>
</@p.form>
</div>
</div>
</body>
</html>
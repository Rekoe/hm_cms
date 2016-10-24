<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>搜索菜系 - Powered By Rekoe Cms</title>
<#include "/template/admin/head.ftl" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
<style>
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
                log( ui.item ?
                    ui.item.label + " ext. " + ui.item.value:
                    "Nothing found");
            },
            open: function() {
                $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
            },
            close: function() {
                $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
            }
    })
    .data( "autocomplete" )._renderItem = function( ul, item ) {
            return $( "<li></li>" )
                .data( "item.autocomplete", item )
                .append( "<a>" + item.label + "&nbsp;ext.&nbsp;" + item.value + "</a>" )
                .appendTo( ul );
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
	  结果： <div id="log" style="height: 200px; width: 300px; overflow: auto;" class="ui-widget-content"></div>
  	</div>
</div>
</body>
</html>
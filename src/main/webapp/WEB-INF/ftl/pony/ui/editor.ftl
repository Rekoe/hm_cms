<#macro editor
	name value="" height="230"
	fullPage="false" toolbarSet="My"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	maxlength="65535"
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<script type="text/javascript">
    var editor = null;
    KindEditor.ready(function(k){
        editor = k.create('textarea[name="${name}"]',{
            width: '100%',
            height: 370,
            resizeType: 1,
            cssPath : ['${base}/thirdparty/kindeditor4/plugins/code/prettify.css'],
            filterMode: true,
            allowFileManager: true,
            items : ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					'superscript', '|', 'selectall', '-',
					'title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold',
					'italic', 'underline', 'strikethrough', 'removeformat', '|', 'image', 'advtable', 'hr', 'emoticons', 'link', 'unlink', '|', 'about'],
            uploadJson: '${base}/admin/kindeditor/upload.rk',
            fileManagerJson : '${base}/admin/kindeditor/manager.rk',
            afterBlur: function(){this.sync();
            }
        });
    });
</script>
<textarea name="${name}" cols="0" rows="0" style="visibility:hidden">${value}</textarea> 
<#include "control-close.ftl"/><#rt/>
</#macro>
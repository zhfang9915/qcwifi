var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function() {
	var form = layui.form;
	
	//创建配送地址
	form.on('submit(change-password)', function(data) {
		console.log(data.field);
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		data:jQuery.param(data.field),
    		dataType:'json',
	        url: rootURL + "personal/center/password/change",    //提交的页面，方法名
	        success : function(result) {
				if (result['success']) {
					$('#layui-password').hide();
					$('.layui-layer-shade').hide();
			        //重置表单
					$("#changePwdForm")[0].reset();
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});

$(function(){
	$('#changePass').click(function(){
		$('#layui-password').show();
		$('.layui-layer-shade').show();
	})
	$('.layui-layer-close,.layui-btn-reset').click(function(){
		$('#layui-password').hide();
		$('.layui-layer-shade').hide();
	})
});
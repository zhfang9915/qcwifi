var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate'], function() {
	var form = layui.form; //表单

	//提交表单
	form.on('submit(submit-content)', function(data) {
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		dataType:'json',
	        url: rootURL + "push/content/create/step4",    //提交的页面，方法名
	        success : function(result) {
				if (result['success']) {
					location.href=result['data'];
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
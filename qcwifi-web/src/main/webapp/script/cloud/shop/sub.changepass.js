var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function() {
	var form = layui.form;

	form.verify({
		pass: [/(.+){6,18}$/, '密码必须6到18位'],
		
		repass: function(value){
			var repassvalue = $('input[name=password]').val();
			if(value != repassvalue){
				return '两次输入的密码不一致!';
			}
		}
	}); 
	
	form.on('submit(change-pass-sub-account)', function(data) {
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		data:jQuery.param(data.field),
    		dataType:'json',
	        url: rootURL + "shop/sub/pass/" + data.field.id,
	        success : function(result) {
				if (result['success']) {
					location.href = result['data'];
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
		return false;
	});
});
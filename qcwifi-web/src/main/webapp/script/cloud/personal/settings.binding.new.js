var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function(){
  var form = layui.form;
  
  form.on('submit(newBinding)', function(data) {
	var load = layer.load(2);
  	$.ajax({
  		type : 'POST',  
  		data:jQuery.param(data.field),
  		dataType:'json',
	        url: rootURL + "personal/center/newbinding/" + $('input[name=changeType]').val(),
	        success : function(result) {
				if (result['success']) {
					window.location.href=result['data'];
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
	//点击获取短信验证码
	$('.btn-erweim').click(function(){
		var changeType = $('input[name=changeType]').val();
		var binding = $('input[name=binding]').val();
		if (!binding) {
			if (changeType == 'phone') {
				layer.msg('请输入新的手机号', function(){});
			}else {
				layer.msg('请输入新的邮箱', function(){});
			}
			return;
		}
		$.ajax({
			async : false,  
			cache : false, 
			type : 'GET',  
    		dataType:'json',
	        url: rootURL + 'personal/center/newbinding/'+changeType+'/code?b=' + binding,
	        success : function(result) {
	        	if (result['success']) {
		        	addCookie("secondsremained",60,60);//添加cookie记录,有效时间60s  
					settime($('.btn-erweim'));
				}else{
					layer.msg(result['msg'], function(){});
				}
			}
	    });
	});
	
	v = getCookieValue("secondsremained");
	if(v>0){  
		settime($('.btn-erweim'));
	} 
});
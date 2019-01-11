var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function(){
  var form = layui.form;
  
  form.on('submit(changeBinding)', function(data) {
	var load = layer.load(2);
  	$.ajax({
  		type : 'POST',  
  		data:jQuery.param(data.field),
  		dataType:'json',
	        url: rootURL + "personal/center/validation/code",
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
		var byType = $('input[name=byType]').val();
		$.ajax({
			async : false,  
			cache : false, 
			type : 'GET',  
    		dataType:'json',
	        url: rootURL + 'personal/center/code/' + byType,
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
var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function(){
	var form = layui.form;
	
	form.on('submit(submitSwitch)', function(data) {
		console.log(data.field);
		var count = $('.text-less-num').html();
		var min = $('input[name=alarmMin]').val();
		if ($('input[name=switch]').is(':checked') && min > count) {
			layer.confirm('您设置最低提醒数量大于当前剩余量，本次设置将不会提醒', {
			  btn: ['依然设置', '返回修改']
			}, function(){
				setAlarm(data.field);
			}, function(){
			});
		}else {
			setAlarm(data.field);
		}
		return false;
	});
});

function setAlarm(data){
	var load = layer.load(2);
  	$.ajax({
  		type : 'POST',  
  		data:jQuery.param(data),
  		dataType:'json',
        url: rootURL + "shop/sms/alarm",
        success : function(result) {
			if (result['success']) {
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			layer.close(load);
		}
    });
}
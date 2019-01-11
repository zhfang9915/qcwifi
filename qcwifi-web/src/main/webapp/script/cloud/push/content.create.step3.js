var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate'], function() {
	var form = layui.form; //表单
	var laydate = layui.laydate;
	laydate.render({ //日期
		elem: '#pushDate',
		range: true,
        min: 0,
        done: function(value, date, endDate){
        	$('#pushDate-start').val(value.substring(0, 10));
        	$('#pushDate-end').val(value.substring(13, 23));
	    }
	});
	form.on('radio(radio-pushDate)', function(data) {
		(data.value == '0') ? ($('#pushDate-items').hide()) : ($('#pushDate-items').show());
	});
	form.on('radio(radio-city)', function(data) {
		(data.value == '0') ? ($('#city-items').hide()) : ($('#city-items').show());
	});
	form.on('radio(radio-category)', function(data) {
		(data.value == '0') ? ($('#category-items').hide()) : ($('#category-items').show());
	});
	form.on('radio(radio-shop)', function(data) {
		(data.value == '0') ? ($('#shop-items').hide()) : ($('#shop-items').show());
	});
	form.on('radio(radio-terminal)', function(data) {
		(data.value == '0') ? ($('#terminal-items').hide()) : ($('#terminal-items').show());
	});
	
	//提交表单
	form.on('submit(submit-content-set)', function(d) {
		var data = {};
		if ($('input[name=pushDate]:checked').val() !=0) {
			data.start = $('#pushDate-start').val();
			data.end = $('#pushDate-end').val();
			if (data.start == '' || data.end == '') {
				layer.msg("请选择投放日期", function(){});
				return false;
			}
		}
		if ($('input[name=area]:checked').val() !=0) {
			
		}
		if ($('input[name=category]:checked').val() !=0) {
			
		}
		if ($('input[name=shop]:checked').val() !=0) {
	
		}
//		data.channel = 	$('input[name=channel]:checked').val();
		if ($('input[name=terminal]:checked').val() !=0) {
			
		}
		console.log(data);
		submitForm(data);
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});

function submitForm(data) {
	var load = layer.load(2);
	$.ajax({
		type : 'POST',  
		data:jQuery.param(data),
		dataType:'json',
		async : false,
        url: rootURL + "push/content/create/step3",    //提交的页面，方法名
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
}
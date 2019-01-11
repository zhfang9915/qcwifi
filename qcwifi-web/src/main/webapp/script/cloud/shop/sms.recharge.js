var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function(){
	var form = layui.form;
	
	showSmsPackages();
	
	form.on('submit(smsRecharge)', function(data) {
		var load = layer.load(2);
	  	$.ajax({
	  		type : 'POST',  
	  		data:jQuery.param(data.field),
	  		dataType:'json',
		        url: rootURL + "shop/sms/recharge/" + data.field.smsPackageId,
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


function showSmsPackages(){
	var load = layer.load(2);
	$.ajax({
		type : 'GET',  
		dataType:'json',
        url: rootURL + "shop/sms/packages",
        success : function(result) {
			if (result['success']) {
				var option = '';
				var tbody = '';
				var sps = result['data'];
				for (var i = 0; i < sps.length; i++) {
					tbody += '<tr><td>' + sps[i].price + '元</td><td>' + sps[i].count + '条</td></tr>';
					option += '<option value="' + sps[i].id + '">' + sps[i].name + '</option>';
				}
				console.log(option);
				$('#smsPackageDetail').html(tbody);
				$('#smsPackageSelect').html('<option value="">请选择</option>'+option);
			}else{
				$("#smsPackageDetail").html('<tr><td colspan="2">'+result['msg']+'</td></tr>');
			}
			layui.form.render('select');
		},
		complete : function(){
			layer.close(load);
		}
    });
}
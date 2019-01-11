var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'layer'], function() {
	var form = layui.form;
	var layer = layui.layer;
	
	/*编辑商铺备注*/
	$('.remark i').click(function() {
		layer.open({
			type: 1,
			title: '商铺备注',
			area: ['500px', 'auto'],
			content: '<div> <textarea id="shop_remark" name="remark" placeholder="请输入备注内容" class="layui-textarea"></textarea></div>',
			btn: ['确定', '取消'],
			btn1: function(index, layero) {
				if ($('#shop_remark').val() == "") {
					layer.msg("请输入商铺备注", function(){});
					return;
				}
				var load = layer.load(2);
				$.ajax({
		    		type : 'PUT',  
		    		data:JSON.stringify({'remark':$('#shop_remark').val()}),
		    		contentType : 'application/json;charset=utf-8',
			        url: rootURL + "shop/info/"+$("#shop_id").val()+"/remark",
			        success : function(result) {
						if (result['success']) {
							$('#p_shop_remark').html($('#shop_remark').val());
							layer.close(index);
						}else{
							layer.msg(result['msg'], function(){});
						}
					},
					complete : function(){
						layer.close(load);
					}
			    });
			}
		});
	});
	
	/*商家账号设置*/
	$('.setup').click(function() {
		var html = '<form class="layui-form" action="">';
		html += '	<div class="layui-form-item">';
		html += '		<label class="layui-form-label">商家账号</label>';
		html += '		<div class="layui-input-inline">';
		html += '		  <input type="text" name="title" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">';
		html += '		</div>';
		html += '  	</div>';
		html += '	<div class="layui-form-item">';
		html += '		<label class="layui-form-label">密码</label>';
		html += '		<div class="layui-input-inline">';
		html += '		  <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">';
		html += '		</div>';
		html += '  	</div>';
		html += '	<div class="layui-form-item">';
		html += '		<label class="layui-form-label">确认密码</label>';
		html += '		<div class="layui-input-inline">';
		html += '		  <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">';
		html += '		</div>';
		html += '  	</div>';
		html += '</form>';

		layer.open({
			type: 1,
			title: '商家账号设置（商家平台地址：xiaolou.com）',
			area: ['500px', 'auto'],
			content: '<div class="layer-form-box">' + html + '</div>',
			btn: ['确定', '取消'],
			btn1: function(index, layero) {
				//确定按钮的回调
				layer.close(index);
			}
		});
	})
});

function deleteShop(){
	layer.confirm('真的要删除吗？此操作将删除以下信息：<br/>商铺信息<br/>商铺子账户<br/>路由器设备', {
		btn: ['确定','取消']
	}, function(){
		var shopId = $("#shop_id").val();
		var load = layer.load(2);
		$.ajax({
			type : 'DELETE',  
	        url: rootURL + "shop/info/delete/"+shopId,
	        success : function(result) {
	        	if (result['success']) {
					layer.confirm('商铺删除成功', {
						btn : [ '知道了' ]
					}, function() {
						location.href=result['data'];
					});
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
	}, function(){});
}
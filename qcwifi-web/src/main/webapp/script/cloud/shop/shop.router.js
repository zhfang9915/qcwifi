var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'layer'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var element = layui.element;
	var binding = '<div class="binding-device">';
	binding += '					<div class="left-device">';
	binding += '						<img src="'+rootURL+'cloud/images/bind-device.png" alt="">';
	binding += '					</div>';
	binding += '					<div class="right-x">';
	binding += '						<p>请登录路由器获取设备序列号</p>';
	binding += '						<p>登录说明：<a href="#">RippleOS固件使用手册</a></p>';
	binding += '					</div>';
	binding += '					<form class="layui-form" action="">';
	binding += '						<div class="layui-form-item">';
	binding += '							<label class="layui-form-label">设备序列号：</label>';
	binding += '							<div class="layui-input-inline">';
	binding += '								<input type="text" id="router_rand_code" required lay-verify="required" placeholder="请输入设备序列号" autocomplete="off" class="layui-input">';
	binding += '							</div>';
	binding += '						</div>';
	binding += '						';
	binding += '					</form>';
	binding += '				</div>';

	$('#binding').click(function() {
		layer.open({
			type: 1,
			title: false,
			area: ['600px', 'auto'],
			content: binding,
			btn: ['提交', '取消'],
			btn1: function(index, layero) {
				if ($('#router_rand_code').val() == "") {
					layer.msg("请输入设备序列号", function(){});
					return;
				}
				var load = layer.load(2);
				$.ajax({
		    		type : 'POST',  
		    		data:JSON.stringify({'code':$('#router_rand_code').val()}),
		    		contentType : 'application/json;charset=utf-8',
			        url: rootURL + "shop/router/"+$("#shop_id").val(),
			        success : function(result) {
						if (result['success']) {
							window.location.reload();
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
	
	var unbinding = '<div class="unbind-warning">';
	unbinding += '			<div class="left">';
	unbinding += '				<img src="'+rootURL+'cloud/images/warning.png" alt="">';
	unbinding += '			</div>';
	unbinding += '			<div class="right">';
	unbinding += '				<p>确定要解绑设备吗？</p>';
	unbinding += '				<p class="red">解绑后商铺的相关功能无法生效！</p>';
	unbinding += '			</div>';
	unbinding += '		</div>';

	$('#warning').on('click', function() {
		layer.open({
			type: 1,
			title: '解绑设备',
			area: ['500px', 'auto'],
			content: '<div>' + unbinding + '</div>',
			btn: ['确定', '取消'],
			btn1: function(index, layero) {
				var load = layer.load(2);
				$.ajax({
		    		type : 'PUT',  
		    		data:JSON.stringify({'devNo':$('#dev_no').val()}),
		    		contentType : 'application/json;charset=utf-8',
			        url: rootURL + "shop/router/"+$("#shop_id").val(),
			        success : function(result) {
						if (result['success']) {
							window.location.reload();
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
});
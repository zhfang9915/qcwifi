var rootURL = $("script[rootURL]").attr('rootURL');
var system = {
	
		flushDB : function(id){
		layer.confirm('确定刷新DB缓存吗？', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "system/flushDB", function(result) {
				layer.close(load);
				if(result['success']){
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	
}
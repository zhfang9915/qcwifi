var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate', 'table'], function() {
	var form = layui.form; //表单
	var table = layui.table;
	var laydate = layui.laydate;
	
	$('#delete-btn').click(function(){
		var id = $('#contentId').val();
		if (id == "") {
			layer.msg('无法删除，请刷新后重试', function(){});
			return false;
		}
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
	        url: rootURL + "push/content/delete/" + id,
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
	});
	
	$('#edit-btn').click(function(){
		var id = $('#contentId').val();
		if (id == "") {
			layer.msg('无法编辑，请刷新后重试', function(){});
			return false;
		}
		location.href= rootURL + "push/content/update/" + id + "/step1";
	});
});
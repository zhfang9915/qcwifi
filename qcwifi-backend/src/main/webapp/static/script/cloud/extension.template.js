var rootURL = $("script[rootURL]").attr('rootURL');
var extensionTemplate = {
		
	/*表格查询参数*/
	queryParams : function(params) {
		var temp = {
			pageNumber : this.pageNumber,  
            pageSize : this.pageSize,
			name : $("#search-name").val(),
			available : $("#search-available").val(),
			isMore : $("#search-isMore").val()
		};
		return temp;
	},
	
	openCreateExtensionTemplateModal : function(){
		//显示弹出层
		$('#extensionTemplate-create-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	},
	
	createExtensionTemplate : function(){
		var data = {};
		data.name = $("#create-name").val();
		if($.trim(data.name) == ''){
			return layer.tips('模板名称不能为空', '#create-name', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="create-available"]:checked').val();
		data.isMore = $('input:radio[name="create-isMore"]:checked').val();
		data.temp = $("#create-temp").val();
		if($.trim(data.temp) == ''){
			return layer.tips('模板主体不能为空', '#create-temp', {tips: [3, '#000']});
		}
		data.tempFor = $("#create-tempFor").val();
		if($.trim(data.tempFor) == ''){
			return layer.tips('资源主体不能为空', '#create-tempFor', {tips: [3, '#000']});
		}
		data.tempJs = $("#create-tempJs").val();
		
		var load = layer.load(2);
		$.post(rootURL + "extension/template/create", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#extensionTemplate-create-modal').modal("hide");
				$("#createExtensionTemplateForm")[0].reset();
				$('#extensionTemplate-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	},
	
	deleteExtensionTemplate : function(id){
		layer.confirm('确定删除这个推送模版吗？', {
		    btn: ['确定删除','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "extension/template/delete/" + id, function(result) {
				layer.close(load);
				if(result['success']){
					$('#extensionTemplate-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	batchDeleteExtensionTemplate : function(){
		var arr = $('#extensionTemplate-table').bootstrapTable('getSelections');
		if (arr.length>0) {
			layer.confirm('确定删除这'+arr.length+'个推送模版吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var load = layer.load(2);
				$.ajax({
			        type: "post",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        url: rootURL + "extension/template/delete/batch",
			        data: JSON.stringify(arr),
			        success : function(result) {
						if (result['success']) {
							$('#extensionTemplate-table').bootstrapTable("refresh");
							layer.msg(result['data']);
						}else {
							layer.msg(result['msg'], function(){});
						}
					},
					complete : function(){
						layer.close(load);
					}
			    });
			}, function(){});
		}else{
			layer.msg("请选择要删除的数据", function(){});
		}
		return false;
	},
	
	openUpdateExtensionTemplateModal : function(id){
		var load = layer.load(2);
		$.post(rootURL + "extension/template/search/" + id, function(result) {
			layer.close(load);
			var obj = result['data'];
			$("#update-id").val(obj.id);
			$("#update-name").val(obj.name);
			if(obj.available){
				$("#update-available-on").attr("checked","checked");
			}else{
				$("#update-available-off").attr("checked","checked");
			}
			if(obj.isMore){
				$("#update-isMore-on").attr("checked","checked");
			}else{
				$("#update-isMore-off").attr("checked","checked");
			}
			$("#update-temp").val(obj.temp);
			$("#update-tempFor").val(obj.tempFor);
			$("#update-tempJs").val(obj.tempJs);
			//显示弹出层
			$('#extensionTemplate-update-modal').modal({
				show:true,//显示弹出层
	            backdrop:'static',//禁止位置关闭
	            keyboard:false//关闭键盘事件
			});
		}, "json");
	},
	
	updateExtensionTemplate : function(){
		var data = {};
		data.id = $("#update-id").val();
		data.name = $("#update-name").val();
		if($.trim(data.name) == ''){
			return layer.tips('模板名称不能为空', '#update-name', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="update-available"]:checked').val();
		data.isMore = $('input:radio[name="update-isMore"]:checked').val();
		data.temp = $("#update-temp").val();
		if($.trim(data.temp) == ''){
			return layer.tips('模板主体不能为空', '#update-temp', {tips: [3, '#000']});
		}
		data.tempFor = $("#update-tempFor").val();
		if($.trim(data.tempFor) == ''){
			return layer.tips('资源主体不能为空', '#update-tempFor', {tips: [3, '#000']});
		}
		data.tempJs = $("#update-tempJs").val();
		
		var load = layer.load(2);
		$.post(rootURL + "extension/template/update", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#extensionTemplate-update-modal').modal("hide");
				$('#extensionTemplate-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	},
	
	previewByPc : function(id){
		var load = layer.load(2);
		$.post(rootURL + "extension/template/search/" + id, function(result) {
			layer.close(load);
			var obj = result['data'];
			var index = layer.open({
				type : 2,
				title : obj.name + '预览',
				content : rootURL + 'extension/template/preview/pc/' + obj.id,
				maxmin : true,
				anim:3
			});
			layer.full(index);
		}, "json");
	},
	
	previewByPhone : function(id){
		var load = layer.load(2);
		$.post(rootURL + "extension/template/search/" + id, function(result) {
			layer.close(load);
			var obj = result['data'];
			var index = layer.open({
				type : 2,
				title : obj.name + '预览',
				content : rootURL + 'extension/template/preview/phone/' + obj.id,
				maxmin : true,
				anim:3
			});
			layer.full(index);
		}, "json");
	}
	
}
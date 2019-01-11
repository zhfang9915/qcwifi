var rootURL = $("script[rootURL]").attr('rootURL');
var plugin = {
		
	/*表格查询参数*/
	queryParams : function(params) {
		var temp = {
			pageNumber : this.pageNumber,  
            pageSize : this.pageSize,
			version : $("#search-version").val(),
			crosstool : $("#search-crosstool").val(),
			available : $("#search-available").val()
		};
		return temp;
	},
	
//	updatePlugin : function(){
//		var data = {};
//		data.id=$("#edit-id").val();
//		data.description = $("#edit-description").val();
//		if($.trim(data.description) == ''){
//			return layer.tips('描述不能为空', '#edit-description', {tips: [3, '#000']});
//		}
//		data.version = $("#edit-version").val();
//		if($.trim(data.version) == ''){
//			return layer.tips('版本不能为空', '#edit-version', {tips: [3, '#000']});
//		}
//		data.crosstool = $("#edit-crosstool").val();
//		if($.trim(data.crosstool) == ''){
//			return layer.tips('交叉编译版本不能为空', '#edit-crosstool', {tips: [3, '#000']});
//		}
//		data.available = $('input:radio[name="edit-available"]:checked').val();
//		
//		var load = layer.load(2);
//		$.post(rootURL + "plugin/update", data, function(result) {
//			layer.close(load);
//			if(result['success']){
//				$('#plugin-edit-modal').modal("hide");
//				$('#plugin-table').bootstrapTable("refresh");
//				layer.msg(result['data']);
//			}else{
//				layer.msg(result['msg'], function(){});
//			}
//		}, "json");
//	},
//	
//	/**
//	 * 编辑
//	 */
//	editPlugin : function(id){
//		$('#plugin-edit-form')[0].reset();
//		var load = layer.load(2);
//		$.post(rootURL + "plugin/search/" + id, function(result) {
//			layer.close(load);
//			if(result['success']){
//				var p = result['data'];
//				$("#edit-id").val(id);
//				$("#edit-version").val(p.version);
//				$("#edit-description").val(p.description);
//				$("#edit-crosstool").val(p.crosstool);
//				if(p.available){
//					$("#edit-available-true").attr("checked","checked");
//					$("#edit-available-false").removeAttr("checked");
//				}else{
//					$("#edit-available-false").attr("checked","checked");
//					$("#edit-available-true").removeAttr("checked");
//				}
//				//显示弹出层
//				$('#plugin-edit-modal').modal({
//					show:true,//显示弹出层
//		            backdrop:'static',//禁止位置关闭
//		            keyboard:false//关闭键盘事件
//				});
//			}else{
//				layer.msg(result['msg'], function(){});
//			}
//		}, "json");
//	},
	
	/**
	 * 删除
	 */
	deletePlugin : function(id){
		layer.confirm('确定删除这个插件吗？', {
		    btn: ['确定删除','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "plugin/delete/" + id, function(result) {
				layer.close(load);
				if(result['success']){
					$('#plugin-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	batchDeletePlugin : function(){
		var arr = $('#plugin-table').bootstrapTable('getSelections');
		if (arr.length>0) {
			layer.confirm('确定删除这'+arr.length+'个插件吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var load = layer.load(2);
				$.ajax({
			        type: "post",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        url: rootURL + "plugin/delete/batch",
			        data: JSON.stringify(arr),
			        success : function(result) {
						if (result['success']) {
							$('#plugin-table').bootstrapTable("refresh");
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
	
	createPlugin : function() {
		//显示弹出层
		$('#plugin-create-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	},
	
	changeFile : function(){
		$("#uploadBtn").html("创建插件");
        $("#progressBar").width("0%");
        var file = $("#pluginFile").prop('files');
        if (file.length != 0) {
            $("#uploadBtn").attr('disabled', false);
        }
	},
	
	submitPlugin : function(){
		// 进度条归零
        $("#progressBar").width("0%");
        // 上传按钮禁用
        $("#uploadBtn").attr('disabled', true);
        // 进度条显示
        $("#progressBar").parent().show();
        $("#progressBar").parent().addClass("active");
        // 上传文件
        plugin.upladFile();
	},
	

	upladFile : function() {
		var pfile = $("#pluginFile").get(0).files[0]; // js 获取文件对象
		if(!pfile){
			return layer.tips('请选择需要上传的插件', '#pluginFile', {tips: [3, '#000']});
		}
		if(Math.ceil(pfile.fileSize / (1024*1024)) > 10){
			return layer.msg("上传的文件大小超出10M", function(){});
		}
		var xhr = new XMLHttpRequest();
		xhr.open("POST", rootURL + "plugin/create", true);
		xhr.upload.addEventListener("progress", plugin.progressFunction, false);
		xhr.addEventListener("load", plugin.uploadComplete, false);
		xhr.addEventListener("error", plugin.uploadFailed, false);
		xhr.send(new FormData($("#createPluginForm")[0]));
	},

	progressFunction : function(evt) {
		var progressBar = $("#progressBar");
		if (evt.lengthComputable) {
			if(Math.round(evt.loaded / evt.total * 100) == 100) {
				var completePercent = "99%";
			}else {
				var completePercent = Math.round(evt.loaded / evt.total * 100)+ "%";
			}
			progressBar.width(completePercent);
			$("#progressBar").html(completePercent);
		}
	},

	uploadComplete : function(evt) {
		$("#progressBar").width("100%");
		var result = JSON.parse(evt.target.responseText);
		if(result['success']){
			$("#uploadBtn").attr('disabled', false);
			$("#uploadBtn").html("创建插件");
			$("#progressBar").parent().removeClass("active");
			$("#progressBar").parent().hide();
			$('#plugin-create-modal').modal("hide");
			$("#createPluginForm")[0].reset();
			$('#plugin-table').bootstrapTable("refresh");
			layer.msg(result['data']);
		}else{
			layer.msg(result['msg'], function(){});
		}
	},

	uploadFailed : function(evt) {
		console.log(evt);
		console.log(evt.target.responseText);
		layer.msg("提交失败，请确认插件文件大小是否超出限制！", function(){});
		$("#uploadBtn").attr('disabled', false);
		$("#uploadBtn").html("创建插件");
		$("#progressBar").parent().removeClass("active");
		$("#progressBar").parent().hide();
	},
	
	detail : function(obj){
		$("#detail-name").html(obj.name);
		$("#detail-description").html(obj.description);
		$("#detail-version").html(obj.version);
		$("#detail-crosstool").html(obj.crosstool);
		$("#detail-respath").html(obj.resPath);
		var available = "禁用";
		if(obj.available){
			available = "启用";
		}
		$("#detail-available").html(available);
		$("#detail-md5").html(obj.md5);
		$("#detail-createby").html(obj.createBy);
		$("#detail-createtime").html(obj.createTime);
		$("#detail-remark").html(obj.remark);
		$('#plugin-detail-modal').modal({show:true});
	}
	
}
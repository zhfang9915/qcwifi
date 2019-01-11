var rootURL = $("script[rootURL]").attr('rootURL');
var firmware = {
		/*表格查询参数*/
		queryParams : function(params) {
			var version = document.getElementById("search-version").value;
			var crosstool = document.getElementById("search-crosstool").value;
			var description = document.getElementById("search-description").value;
			var available = document.getElementById("search-available").value;
			
			var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				version: version,
				crosstool: crosstool,
				description: description,
				available: available,
				pageNumber : this.pageNumber,  
                pageSize : this.pageSize
			};
			console.log(temp);
			return temp;
		},
	
		//删除
		deleteFirmware : function(id){
			layer.confirm('确定删除这个固件吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var load = layer.load(2);
				$.post(rootURL + "firmware/delete/" + id, function(result) {
					layer.close(load);
					if(result['success']){
						$('#firmware-table').bootstrapTable("refresh");
						layer.msg(result['data']);
					}else{
						layer.msg(result['msg'], function(){});
					}
				}, "json");
			}, function(){});
		},
 
		uploadFile : function() {
			var file = $("#firmwareFile").get(0).files[0]; // js 获取文件对象
			if(!file){
				return layer.tips('请选择需要上传的固件', '#firmwareFile', {tips: [3, '#000']});
			}
			if(Math.ceil(file.fileSize / (1024*1024)) > 10){
				return layer.msg("上传的文件大小超出10M", function(){});
			}
			var xhr = new XMLHttpRequest();
			xhr.open("POST", rootURL + "firmware/create", true);
			xhr.upload.addEventListener("progress", firmware.progressFunction, false);
			xhr.addEventListener("load", firmware.uploadComplete, false);
			xhr.addEventListener("error", firmware.uploadFailed, false);
			xhr.send(new FormData($("#createFirmwareForm")[0]));
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
				$("#uploadBtn").html("创建固件");
				$("#progressBar").parent().removeClass("active");
				$("#progressBar").parent().hide();
				$('#firmware-create-modal').modal("hide");
				$("#createFirmwareForm")[0].reset();
				$('#firmware-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		
		uploadFailed : function(evt) {
			console.log(evt);
			console.log(evt.target.responseText);
			layer.msg("提交失败，请确认固件文件大小是否超出限制！", function(){});
			$("#uploadBtn").attr('disabled', false);
			$("#uploadBtn").html("创建固件");
			$("#progressBar").parent().removeClass("active");
			$("#progressBar").parent().hide();
		},
	
	downloadFirmware : function(url) {
		window.open(url);
	},
}

$(function() {
		$('#firmware-table').bootstrapTable({
			url : rootURL + "firmware/table", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : firmware.queryParams,// 传递参数（*）
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			minimumCountColumns : 2, // 最少允许的列数
			toolbar : "#toolbar",
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			showExport : true,
			columns : 
			[ {
                checkbox: true,
                align: 'center',
                valign: 'middle'
			}, {
				field : 'id',
				title : '固件ID'
			}, {
				field : 'name',
				title : '文件名称'
			}, {
				field : 'version',
				title : '版本'
			}, {
				field : 'crosstool',
				title : 'GCC版本'
			}, {
				field : 'md5',
				title : 'md5'
			}, {
				field : 'description',
				title : '固件描述'
			}, {
				field : 'createBy',
				title : '创建者'
			}, {
				field : 'available',
				align: 'center',
				title : '状态',
				formatter : function(value, row, index){
					if (value) {
						return '<span class="glyphicon glyphicon-ok text-success" aria-hidden="true"></span>';
					} else {
						return '<span class="glyphicon glyphicon-remove text-default" aria-hidden="true"></span>';
					}
				}
			}, {
				title : '操作',
				align: 'center',
				formatter : function(value, row, index){
					return [
						'<button type="button" class="btn btn-link btn-xs" onClick="firmware.downloadFirmware(\''+row.downloadUrl+'\')">下载源固件</button>',
						'<button type="button" class="btn btn-link btn-xs" onClick="firmware.downloadFirmware(\''+row.bakUrl+'\')">下载含头固件</button>',
			            '<button type="button" class="btn btn-link btn-xs" onClick="firmware.deleteFirmware(\''+row.id+'\')">删除</button>'
			        ].join('');
				}
			} ]
		});
		
		//点击搜索
		$("#firmware-search").click(function(){
			$('#firmware-table').bootstrapTable("refresh");
		});


		$("#firmware-delete").click(function(){
			var arr = $('#firmware-table').bootstrapTable('getSelections');
			if (arr.length > 0) {
				layer.confirm('确定删除这' + arr.length + '个固件吗？', {
				    btn: ['确定删除','取消'], //按钮
				    shade: false //不显示遮罩
				}, function() {
					$.ajax({
				        type: "post",     //提交方式
				        contentType: "application/json; charset=utf-8",   //内容类型
				        dataType: "json",     //类型
				        url: rootURL + "firmware/delete/batch",    //提交的页面，方法名
				        data: JSON.stringify(arr),    //参数为固件ID
				        success : function(result) {
							if (result['success']) {
								$('#firmware-table').bootstrapTable("refresh");
								layer.msg(result['data']);
							} else {
								layer.msg(result['msg'], function(){});
							}
						}
				    });
				}, function(){});
			}
			else {
				layer.msg("请选择要删除的数据", function(){});
			}
		});
		
		$("#uploadBtn").attr('disabled', true);//创建插件按钮
		
		//点击创建固件页面
		$("#firmware-create").click(function(){
			//显示弹出层
			$('#firmware-create-modal').modal({
				show:true,//显示弹出层
	            backdrop:'static',//禁止位置关闭
	            keyboard:false//关闭键盘事件
			});
		});
		
		//点击创建
		$("#uploadBtn").click(function() {
	        // 进度条归零
	        $("#progressBar").width("0%");
	        // 上传按钮禁用
	        $(this).attr('disabled', true);
	        // 进度条显示
	        $("#progressBar").parent().show();
	        $("#progressBar").parent().addClass("active");
	        // 上传文件
	        firmware.uploadFile();
	    });
		
	    // 文件修改时
	    $("#firmwareFile").change(function() {
	        $("#uploadBtn").html("创建插件");
	        $("#progressBar").width("0%");
	        var file = $(this).prop('files');
	        //if (file.length != 0) {
	            $("#uploadBtn").attr('disabled', false);
	        //}
	    });
	    
});